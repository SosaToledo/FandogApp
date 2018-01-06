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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Articulo.OnFragmentInteractionListener, InicialFragment.OnFragmentInteractionListener, FirebaseAuth.AuthStateListener
, CajaFragment.OnFragmentInteractionListener{

    private FirebaseAuth firebaseAuth;
    final private String TAG = "Principal";
    private TextView nombre;
    private TextView correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
    }

    private void cargarUI() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        correo.setText(user.getEmail());
        if (user.getDisplayName() == null||user.getDisplayName().equals("")){
//        if (user.getDisplayName().equals("Nombre Usuario")){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            View v = getLayoutInflater().inflate(R.layout.insertar_nombre,null);
            dialog.setView(v);
            dialog.setCancelable(true);
            Button b = v.findViewById(R.id.insertarNombre);
            final EditText et = v.findViewById(R.id.etInsertNombre);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!et.getText().toString().trim().isEmpty()){
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(et.getText().toString().trim()).build();
                        user.updateProfile(profileUpdates);
                        nombre.setText(user.getDisplayName());

                    }else {
                        Toast.makeText(Principal.this, "No puede estar vacio el campo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.create().show();
        }else{
            nombre.setText(user.getDisplayName());
        }
        Log.i(TAG, "cargarUI: user name= "+ user.getDisplayName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                break;
            case R.id.nav_carga_stock:
                fragment = new Articulo();
                break;
            case R.id.nav_carga_caja:
                fragment = new CajaFragment();
                break;
            case R.id.nav_salir:
                firebaseAuth.signOut();
                break;
            default:
                break;

        }
        if (fragment != null){
            getSupportFragmentManager()
                .beginTransaction()
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
}
