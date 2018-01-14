package ar.com.thinco.fandog;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CajaFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private EditText efectivoInicial,ventas,desdeAlivios,hastaAlivios,totalAlivios,efectivoFinal,diferencia;
    private SharedPreferences sharedPreferences;
    private String efeInicial, vtas, dAlivios,hAlivios,tAlivios,efeFinal,dif;

    public CajaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_caja, container, false);

        efectivoInicial = v.findViewById(R.id.Caja_efectivoInicial);
        ventas = v.findViewById(R.id.Caja_ventas);
        desdeAlivios = v.findViewById(R.id.desdeAlivios);
        hastaAlivios = v.findViewById(R.id.hastaAlivios);
        totalAlivios = v.findViewById(R.id.totalAlivios);
        efectivoFinal = v.findViewById(R.id.Caja_efectivoFinal);
        diferencia = v.findViewById(R.id.Caja_diferencia);

        Button button = v.findViewById(R.id.btnGuardarEfectivo);
        button.setOnClickListener(this);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        return v;
    }

    @Override
    public void onClick(View view) {
        efeInicial = efectivoInicial.getText().toString();
        vtas = ventas.getText().toString();
        dAlivios = desdeAlivios.getText().toString();
        hAlivios = hastaAlivios.getText().toString();
        tAlivios = totalAlivios.getText().toString();
        efeFinal = efectivoFinal.getText().toString();
        dif = diferencia.getText().toString();
        
        if (efeInicial.isEmpty()||vtas.isEmpty()||dAlivios.isEmpty()||hAlivios.isEmpty()||tAlivios.isEmpty()
                ||efeFinal.isEmpty()||dif.isEmpty()){
            Toast.makeText(getActivity(), "No pueden haber campos vacios", Toast.LENGTH_SHORT).show();
        }else{
            guardarCampos();
            mListener.cajaAvanzar();
        }
    }

    private void guardarCampos() {
        sharedPreferences.edit()
                .putString("efectivoInicial",efeInicial)
                .putString("cajaVentas",vtas)
                .putString("cajaDesdeAlivios",dAlivios)
                .putString("cajaHastaAlivios",hAlivios)
                .putString("cajaTotalAlivios",tAlivios)
                .putString("cajaEfectivoFinal",efeFinal)
                .putString("cajaDiferencia",dif)

                .putBoolean("CajaCargado",true)
                .apply();
    }

    private void poblarCampos() {
        efectivoInicial.setText(sharedPreferences.getString("efectivoInicial","0"));
        ventas.setText(sharedPreferences.getString("cajaVentas","0"));
        desdeAlivios.setText(sharedPreferences.getString("cajaDesdeAlivios","0"));
        hastaAlivios.setText(sharedPreferences.getString("cajaHastaAlivios","0"));
        totalAlivios.setText(sharedPreferences.getString("cajaTotalAlivios","0"));
        efectivoFinal.setText(sharedPreferences.getString("cajaEfectivoFinal","0"));
        diferencia.setText(sharedPreferences.getString("cajaDiferencia","0"));
    }

    @Override
    public void onResume() {
        super.onResume();
        efectivoInicial.setText(sharedPreferences.getString("efectivoInicial","0"));
        if (sharedPreferences.getBoolean("CajaCargado",false)){
            poblarCampos();
        }
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


    public interface OnFragmentInteractionListener {
        void cajaAvanzar();
    }
}
