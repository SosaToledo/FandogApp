package ar.com.thinco.fandog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class InicialFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private TextView nombreVendedor;
    private EditText iniSalchichas, iniPanes, iniGaseosas,efectivoEnCaja,precioSalschichas,precioGaseosas;
    private Spinner spinnerTurno,spinnerLocal;
    private String sLocal,sTurno,iniS,iniP,iniG,efe,precioS,precioGV,nVendedor;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public InicialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicial, container, false);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        Aca seteamos los spinners y los rellenamos
        spinnerTurno = view.findViewById(R.id.spinnerTurno);
        spinnerLocal = view.findViewById(R.id.spinnerLocal);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTurno = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.spinner_turnos,
                android.R.layout.simple_spinner_item);
        adapterTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTurno.setAdapter(adapterTurno);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.spinner_locales,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLocal.setAdapter(adapter);

//      busco el resto de los elementos y los linkeo
        nombreVendedor = view.findViewById(R.id.nombreVendedor);
        nVendedor =FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        nombreVendedor.setText("Vendedor: "+nVendedor);
        iniSalchichas = view.findViewById(R.id.iniSalchichas);
        iniPanes = view.findViewById(R.id.iniPanes);
        iniGaseosas = view.findViewById(R.id.iniGaseosas);
        efectivoEnCaja = view.findViewById(R.id.efectivo_en_caja);

        precioSalschichas = view.findViewById(R.id.precioSalchichas);
        precioGaseosas = view.findViewById(R.id.precioGaseosas);

        Button btnGuardarIni = view.findViewById(R.id.btnGuardarIni);
        btnGuardarIni.setOnClickListener(this);


        return view;
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
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("inicialEncontrado",false)){
            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
            builder.setTitle("Bienvenido")
                    .setMessage("Se encontraron elementos guardados")
                    .setPositiveButton("Cargar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            poblarFormulario();
                            sharedPreferences.edit()
                                    .putBoolean("inicialEncontrado",false)
                                    .apply();
                        }
                    })
                    .setNegativeButton("Descartar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            sharedPreferences.edit()
                                    .putBoolean("inicialEncontrado",false)
                                    .putBoolean("inicialCargado",false)
                                    .putBoolean("SalchichasCargado",false)
                                    .putBoolean("PanesCargado",false)
                                    .putBoolean("GaseosasCargado",false)
                                    .putBoolean("CajaCargado",false )
                                    .apply();
                        }
                    })
                    .setCancelable(false);
            builder.create().show();
        }
        if (sharedPreferences.getBoolean("inicialCargado",false)
                &&!sharedPreferences.getBoolean("inicialEncontrado",false)) {
            poblarFormulario();
        }
    }

    @Override
    public void onClick(View view) {
        comprobarLosCampos();
    }

    private void comprobarLosCampos() {

        iniS = iniSalchichas.getText().toString().trim();
        iniP = iniPanes.getText().toString().trim();
        iniG = iniGaseosas.getText().toString().trim();
        sLocal = spinnerLocal.getSelectedItem().toString();
        sTurno = spinnerTurno.getSelectedItem().toString();
        efe = efectivoEnCaja.getText().toString();
        precioS = precioSalschichas.getText().toString();
        precioGV = precioGaseosas.getText().toString();

        // TODO: 6/1/2018 Falta bloquear el avance hasta que este todo cargado

        if (iniS.isEmpty()||iniP.isEmpty()||iniG.isEmpty()||spinnerLocal.getSelectedItemPosition()==0||
                spinnerTurno.getSelectedItemPosition()==0||efe.isEmpty()||
                precioS.isEmpty()||precioGV.isEmpty()){
            Toast.makeText(getActivity(), "No pueden haber campos vacios.", Toast.LENGTH_SHORT).show();
        }else {
            guardarDatos();
            mListener.inicialAvanzar();
        }
    }

    private void guardarDatos() {

        editor.putString("nombreVendedor",nVendedor);

        editor.putString("SalchichasInicial",iniS);
        editor.putString("PanesInicial",iniP);
        editor.putString("GaseosasInicial",iniG);

        editor.putString("local",sLocal);
        editor.putInt("local-posicion",spinnerLocal.getSelectedItemPosition());
        editor.putString("turno",sTurno);
        editor.putInt("turno-posicion",spinnerTurno.getSelectedItemPosition());

        editor.putString("efectivoInicial",efe);
        editor.putString("precioSalchichas",precioS);
        editor.putString("precioGaseosas",precioGV);

        editor.putBoolean("inicialCargado",true);
        editor.apply();
    }

    private void poblarFormulario() {
        nombreVendedor.setText("Vendedor: "+sharedPreferences.getString("nombreVendedor","user"));

        spinnerTurno.setSelection(sharedPreferences.getInt("turno-posicion",0));
        spinnerLocal.setSelection(sharedPreferences.getInt("local-posicion",0));

        iniSalchichas.setText(sharedPreferences.getString("SalchichasInicial","0"));
        iniPanes.setText(sharedPreferences.getString("PanesInicial","0"));
        iniGaseosas.setText(sharedPreferences.getString("GaseosasInicial","0"));
        efectivoEnCaja.setText(sharedPreferences.getString("efectivoInicial","0"));
        precioSalschichas.setText(sharedPreferences.getString("precioSalchichas","0"));
        precioGaseosas.setText(sharedPreferences.getString("precioGaseosas","0"));
    }

    public interface OnFragmentInteractionListener {
        void inicialAvanzar();
    }
}
