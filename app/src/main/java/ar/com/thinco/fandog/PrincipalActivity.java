package ar.com.thinco.fandog;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InicialFragment.OnFragmentInteractionListener, CajaFragment.OnFragmentInteractionListener, CargarStocksFragment.OnFragmentInteractionListener{

    private FirebaseAuth firebaseAuth;
    private TextView nombre;
    private TextView correo;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private SharedPreferences sharedPreferences;
    private Turno miTuno;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                View view = getCurrentFocus();
                view.clearFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Cargo la actividad principal
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorPrincipal,new InicialFragment())
                .commit();

        View navHeader = navigationView.getHeaderView(0);
        nombre = navHeader.findViewById(R.id.nav_header_userName);
        correo = navHeader.findViewById(R.id.nav_header_correo);
        cargarUI();
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportActionBar().setTitle("Cargar Inicial");
    }

    private void cargarUI() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        correo.setText(user.getEmail());
        if (user.getDisplayName() == null||user.getDisplayName().equals("")){
//        if (user.getDisplayName().equals("Frank")||user.getDisplayName().equals("Thinco")){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            View v = getLayoutInflater().inflate(R.layout.insertar_nombre,null);
            dialog.setView(v);
            dialog.setCancelable(true);
            Button b = v.findViewById(R.id.insertarNombre);
            final EditText et = v.findViewById(R.id.etInsertNombre);
            final EditText apellido = v.findViewById(R.id.etInsertApellido);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a,b;
                    a=et.getText().toString().trim();
                    b=apellido.getText().toString().trim();
                    if (!a.isEmpty()||!b.isEmpty()){
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(a+" "+b).build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            nombre.setText(user.getDisplayName());
                                    }
                                });
                        Toast.makeText(PrincipalActivity.this, "Guardado", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PrincipalActivity.this, "No puede estar vacio el campo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.create().show();
        }else{
            nombre.setText(user.getDisplayName());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (sharedPreferences.getBoolean("inicialCargado",false)){
                new AlertDialog.Builder(this).setTitle("Antes de irte")
                        .setMessage("¿Deseas guardar los datos no enviados?")
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPreferences.edit()
                                        .putBoolean("inicialEncontrado",true)
                                        .putBoolean("inicialCargado",true)
                                        .putBoolean("SalchichasCargado",true)
                                        .putBoolean("PanesCargado",true)
                                        .putBoolean("GaseosasCargado",true)
                                        .putBoolean("CajaCargado",true)
                                        .apply();
                                finish();
                            }
                        })
                        .setNegativeButton("Descartar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPreferences.edit()
                                        .putBoolean("inicialEncontrado",false)
                                        .putBoolean("inicialCargado",false)
                                        .putBoolean("SalchichasCargado",false)
                                        .putBoolean("PanesCargado",false)
                                        .putBoolean("GaseosasCargado",false)
                                        .putBoolean("CajaCargado",false)
                                        .apply();
                                dialogInterface.dismiss();
                                finish();
                            }
                        })
                        .create().show();
            }else
                super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        if (!sharedPreferences.getBoolean("inicialEncontrado",false)){
            sharedPreferences.edit().clear().apply();
        }
        super.onStop();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id){
            case R.id.nav_stockInicial:
                fragment = new InicialFragment();
                navigationView.getMenu().getItem(0).setChecked(true);
                getSupportActionBar().setTitle("Cargar Inicial");
                break;
            case R.id.nav_carga_stock:
                fragment = new CargarStocksFragment();
                navigationView.getMenu().getItem(1).setChecked(true);
                getSupportActionBar().setTitle("Stock por Art");
                break;
            case R.id.nav_carga_caja:
                fragment = new CajaFragment();
                getSupportActionBar().setTitle("Cargar Caja");
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case R.id.nav_salir:
                salir();
                break;
            default:
                break;

        }
        if (fragment != null){
            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void salir(){
        firebaseAuth.signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void inicialAvanzar() {
        // TODO: 14/1/2018 agregar bloqueo para que no puedan cambiar de fragment hasta que no este cargado el inical
        Fragment fragment = new CargarStocksFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
        getSupportActionBar().setTitle("Stock por Art");
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void viewPagerAvanzar() {
        Fragment fragment = new CajaFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
        getSupportActionBar().setTitle("Cargar Caja");
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void cajaAvanzar() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.titleGuardando)
                .setMessage(traerCampos())
                .setCancelable(false)
                .setPositiveButton(R.string.enviarVenta, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        enviarTurno();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }

    private void enviarTurno() {
        //Primero enviamos a la bd

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String format = s.format(new Date());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fandog");
        myRef.child(format).setValue(miTuno);

        //luego seteamos las variables de iniciales en false.

        sharedPreferences.edit().clear().apply();

        //enviamos de nuevo a la primera pantalla.
        InicialFragment fragment = new InicialFragment();
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
        getSupportActionBar().setTitle("Cargar Inicial");
    }

    private String traerCampos() {

        String vendedor,local,precioSal,precioGas; //Stock Inicial
        String salIni,salCom,salRot,salSc,salScp,salSal,salFin,salVta; //Salchichas
        String panIni,panCom,panRot,panSc,panScp,panSal,panFin,panVta; //Pan
        String gasIni,gasCom,gasRot,gasSc,gasScp,gasSal,gasFin,gasVta; //Gaseosas
        String efeIni,ventasTotales,dAlivios,hAlivios,tAlivios,efeFin,difCaja; //CajaFragment

        vendedor = nombre.getText().toString();
        local = sharedPreferences.getString("local","");
        precioSal = sharedPreferences.getString("precioSalchichas","");
        precioGas = sharedPreferences.getString("precioGaseosas","");
        salIni = sharedPreferences.getString("SalchichasInicial","");
        salCom = sharedPreferences.getString("SalchichasCompras","");
        salRot = sharedPreferences.getString("SalchichasRotos","");
        salSc = sharedPreferences.getString("SalchichasSinCargo","");
        salScp = sharedPreferences.getString("SalchichasSinCargoPersonal","");
        salSal = sharedPreferences.getString("SalchichasSalidas","");
        salFin = sharedPreferences.getString("SalchichasFinal","");
        salVta = sharedPreferences.getString("SalchichasVentas","");
        panIni = sharedPreferences.getString("PanesInicial","");
        panCom = sharedPreferences.getString("PanesCompras","");
        panRot = sharedPreferences.getString("PanesRotos","");
        panSc = sharedPreferences.getString("PanesSinCargo","");
        panScp = sharedPreferences.getString("PanesSinCargoPersonal","");
        panSal = sharedPreferences.getString("PanesSalidas","");
        panFin = sharedPreferences.getString("PanesFinal","");
        panVta = sharedPreferences.getString("PanesVentas","");
        gasIni = sharedPreferences.getString("GaseosasInicial","");
        gasCom = sharedPreferences.getString("GaseosasCompras","");
        gasRot = sharedPreferences.getString("GaseosasRotos","");
        gasSc = sharedPreferences.getString("GaseosasSinCargo","");
        gasScp = sharedPreferences.getString("GaseosasSinCargoPersonal","");
        gasSal = sharedPreferences.getString("GaseosasSalidas","");
        gasFin = sharedPreferences.getString("GaseosasFinal","");
        gasVta = sharedPreferences.getString("GaseosasVentas","");
        efeIni = sharedPreferences.getString("efectivoInicial","");
        ventasTotales = sharedPreferences.getString("cajaVentas","");
        dAlivios = sharedPreferences.getString("cajaDesdeAlivios","");
        hAlivios = sharedPreferences.getString("cajaHastaAlivios","");
        tAlivios = sharedPreferences.getString("cajaTotalAlivios","");
        efeFin = sharedPreferences.getString("cajaEfectivoFinal","");
        difCaja = sharedPreferences.getString("cajaDiferencia","");

        miTuno = new Turno(
                vendedor,local,precioSal,precioGas,salIni,salCom,salRot
                ,salSc,salScp,salSal,salFin,salVta,panIni,panCom,panRot
                ,panSc,panScp,panSal,panFin,panVta,gasIni,gasCom,gasRot
                ,gasSc,gasScp,gasSal,gasFin,gasVta,efeIni,ventasTotales
                ,dAlivios,hAlivios,tAlivios,efeFin,difCaja
        );
        
        String resultado="";
        resultado+= "Vendedor: "+vendedor+"\n"+
                "Local: "+local+"\n" +
                "Precio de Salchichas: "+ precioSal+"\n" +
                "Precio de GV: "+precioGas+"\n" +
                "\n" +
                "Salchichas inicial: "+salIni+"\n" +
                "Salchichas compras: "+salCom+"\n" +
                "Salchichas rotas: "+salRot+"\n" +
                "Salchichas sin cargo: "+salSc+"\n" +
                "Salchichas s/c pers: "+salScp+"\n" +
                "Salchichas salidas: "+salSal+"\n" +
                "Salchichas final: "+salFin+"\n" +
                "Salchichas vendidos: "+salVta+"\n" +
                "\n" +
                "Panes inicial: "+panIni+"\n" +
                "Panes compras: "+panCom+"\n" +
                "Panes rotos: "+panRot+"\n"+
                "Panes sin cargo: "+panSc+"\n"+
                "Panes s/c pers: "+panScp+"\n"+
                "Panes salidas: "+panSal+"\n"+
                "Panes final: "+panFin+"\n"+
                "Panes vendidos: "+panVta+"\n"+
                "\n"+
                "Gaseosas inicial: "+gasIni+"\n" +
                "Gaseosas compras: "+gasCom+"\n" +
                "Gaseosas rotos: "+gasRot+"\n"+
                "Gaseosas sin cargo: "+gasSc+"\n"+
                "Gaseosas s/c pers: "+gasScp+"\n"+
                "Gaseosas salidas: "+gasSal+"\n"+
                "Gaseosas final: "+gasFin+"\n"+
                "Gaseosas vendidos: "+gasVta+"\n"+
                "\n"+
                "Efectivo inicial en caja: "+efeIni+"\n"+
                "Total de $ por ventas: "+ventasTotales+"\n"+
                "Primer alivio usado: "+dAlivios+"\n"+
                "Ultimo alivio usado: "+hAlivios+"\n"+
                "Total de $ en alivios: "+tAlivios+"\n"+
                "Efectivo final en caja: "+efeFin+"\n"+
                "Diferencia de $: "+difCaja+"\n";

        return resultado;
    }
}
