package ar.com.thinco.fandog;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity implements Login.OnFragmentInteractionListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// TODO: 27/12/17 Agregar Screen de Logo Thinco
//        Aca comienzo mi codigo
        mAuth = FirebaseAuth.getInstance();
        btnLogOut = findViewById(R.id.btnLogOut);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        inicio(currentUser);
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (currentUser ==null)
                    inicio(currentUser);
            }
        });
        btnLogOut.setOnClickListener(this);
    }

    private void inicio(FirebaseUser currentUser) {
        if (currentUser == null) {
            //No hay usuario llamamos al fragment del login
            Login fragmentLogin = new Login();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedorFr,fragmentLogin).commit();
            btnLogOut.setEnabled(false);
        }else {
            btnLogOut.setEnabled(true);
        }
    }

    @Override
    public void onFragmentInteraction() {
        borrarFragment();
    }

    private void borrarFragment() {
        for(Fragment fragment:getSupportFragmentManager().getFragments()){
            if(fragment!=null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        btnLogOut.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                inicio(mAuth.getCurrentUser());
                break;
            default:
                break;
        }
    }
}
