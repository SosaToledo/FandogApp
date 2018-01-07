package ar.com.thinco.fandog;


import android.content.Context;
import android.content.Intent;
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

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InicialFragment.OnFragmentInteractionListener, FirebaseAuth.AuthStateListener
, CajaFragment.OnFragmentInteractionListener, CargarStocksFragment.OnFragmentInteractionListener, ArticuloFragment.OnFragmentInteractionListener{

    private FirebaseAuth firebaseAuth;
    final private String TAG = "PrincipalActivity";
    private TextView nombre;
    private TextView correo;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(this);

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                break;
            case R.id.nav_carga_stock:
                fragment = new CargarStocksFragment();
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case R.id.nav_carga_caja:
                fragment = new CajaFragment();
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case R.id.nav_salir:
                firebaseAuth.signOut();
                navigationView.getMenu().getItem(3).setChecked(true);
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.i(TAG, "onAuthStateChanged: Cambio el estado del user");
        if (firebaseAuth.getCurrentUser()!= null)
            Log.i(TAG, "currentUser: "+firebaseAuth.getCurrentUser().toString());
        if (firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void inicialAvanzar() {
        Fragment fragment = new CargarStocksFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void viewPagerAvanzar() {
        Log.i(TAG, "viewPagerAvanzar: Entro desde principal");
        Fragment fragment = new CajaFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.contenedorPrincipal, fragment)
                .commit();
        navigationView.getMenu().getItem(2).setChecked(true);
    }
}
