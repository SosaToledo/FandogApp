package ar.com.thinco.fandog;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, FirebaseAuth.AuthStateListener {

    private FirebaseAuth mAuth;
    private FragmentManager fragmentManager;
    private FirebaseUser currentUser;
    final private String TAG = "Current User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// TODO: 27/12/17 Agregar Screen de Logo Thinco
//        Aca comienzo mi codigo
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        fragmentManager = getSupportFragmentManager();


        inicio();
        mAuth.addAuthStateListener(this);
    }

    private void inicio() {
        currentUser = mAuth.getCurrentUser();
        List<Fragment> lista = fragmentManager.getFragments();
        if (currentUser == null ) {
            if ( lista.isEmpty() ){
                //No hay usuario llamamos al fragment del login
                LoginFragment fragmentLoginFragment = new LoginFragment();
                fragmentManager.beginTransaction().add(R.id.contenedorFr, fragmentLoginFragment).commitAllowingStateLoss();
            }
        }else {
        }
    }

    @Override
    public void onFragmentInteraction() {
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            inicio();
            Log.i(TAG, "onAuthStateChanged: No hay usuario");            
        }
        else {
            Log.i(TAG, "onAuthStateChanged: Si hay usuario");
            Intent intent = new Intent(getApplication(), PrincipalActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
