package ar.com.thinco.fandog;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Fragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;

    //Mis variables
    private FirebaseAuth firebaseAuth;
    private EditText etUser,etPass,etCorreo;
    private Button btnLogin, btnPerdiMiPass;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance() {
        Login fragment = new Login();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Buscamos los dos EditText y los dos botones
        etUser = view.findViewById(R.id.etUsuario);
        etPass = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLoginIniciarSesion);
        btnPerdiMiPass = view.findViewById(R.id.btnLoginPerdiMiClave);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnLogin.setOnClickListener(this);
        btnPerdiMiPass.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginIniciarSesion:
                iniciarSesion();
                break;
            case R.id.btnLoginPerdiMiClave:
                resetear();
                break;
            default:
                break;
        }
    }

    private void resetear() {
        AlertDialog.Builder constructor = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.reset_password,null);
        constructor.setView(v);

        etCorreo = v.findViewById(R.id.etDialogCorreo);
        Button btnReenviar = v.findViewById(R.id.btnDialogPerdmiMiPass);

        btnReenviar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s =etCorreo.getText().toString().trim();
                        if (s.isEmpty() || !s.contains("@")){
                            Toast.makeText(getActivity(), "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
                        }else{
                            firebaseAuth.sendPasswordResetEmail(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Te enviamos un correo electronico.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(), "Hubo un problema, prueba nuevamente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
        );
        constructor.create();
        constructor.show();
        Toast.makeText(getActivity(), "Entro", Toast.LENGTH_SHORT).show();
    }

    private void iniciarSesion() {
        String user,pass;
        user = etUser.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        
        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(getActivity(), "No pueden haber campos vacios", Toast.LENGTH_SHORT).show();
        }else if (user.length()<4 || pass.length()<4 ){
            Toast.makeText(getActivity(), "Error, datos demasiado cortos", Toast.LENGTH_SHORT).show();
        }else {

            firebaseAuth.signInWithEmailAndPassword(user,pass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Logeado correctamente.", Toast.LENGTH_SHORT).show();
                                mListener.onFragmentInteraction();
                            } else {
                                Toast.makeText(getActivity(), "Contaseña o usuario incorrecto.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

}
