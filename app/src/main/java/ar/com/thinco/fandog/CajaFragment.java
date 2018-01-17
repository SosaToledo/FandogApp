package ar.com.thinco.fandog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static android.content.ContentValues.TAG;


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
        
        if (efeInicial.isEmpty()||dAlivios.isEmpty()||hAlivios.isEmpty()||tAlivios.isEmpty()
                ||efeFinal.isEmpty() ){
            Toast.makeText(getActivity(), "No pueden haber campos vacios", Toast.LENGTH_SHORT).show();
        }else if (compruebaConexion(getActivity(),view)&&comprobarAnteriores(view)) {
            if (comprobarCampos()){
                guardarCampos();
                mListener.cajaAvanzar();
            }
        }
    }

    private boolean comprobarCampos() {
        int d,h,s,p,g,ps,pg,ventasTotales,efei,ta,efef,dif = 0;
        boolean alivios, vtas = false;
        String msj = "";
        Log.d(TAG, "comprobarCampos: Entro:"+msj);

        s = Integer.valueOf(sharedPreferences.getString("SalchichasVentas","0") );
        p = Integer.valueOf(sharedPreferences.getString("PanesVentas","0") );
        g = Integer.valueOf(sharedPreferences.getString("GaseosasVentas","0") );

        ps = Integer.valueOf(sharedPreferences.getString("precioSalchichas","0"));
        pg = Integer.valueOf(sharedPreferences.getString("precioGaseosas","0"));

        ventasTotales= (s*ps)+(p*pg)+g;
        ventas.setText(String.valueOf(ventasTotales));

        d=Integer.valueOf(desdeAlivios.getText().toString());
        h=Integer.valueOf(hastaAlivios.getText().toString());

        efei = Integer.valueOf(efectivoInicial.getText().toString());
        ta = Integer.valueOf(totalAlivios.getText().toString());
        efef = Integer.valueOf(efectivoFinal.getText().toString());
        dif = (efei-ta+efef) - ventasTotales;
        if ( (dif<0)||(dif>0) ){
            msj+="Tenes una diferencia en la caja \n \n";
            vtas=false;
        }else if (dif == 0 ){
            vtas= true;
        }
        diferencia.setText(String.valueOf(dif));

        if (d==h)
            alivios =true;
        else if (d>h){
            msj+="-No podes tener el numero del primer alivio mayor al ultimo \n \n";
            alivios =false;
        }
        else
            alivios =true;
        if (!msj.isEmpty()){
            Log.d(TAG, "comprobarCampos: Mensaje esta vacio");
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.diferenciasEnLaCaja)
                    .setMessage(msj)
                    .setPositiveButton("Revisar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        Log.d(TAG, "comprobarCampos: alivios="+alivios+" vtas="+vtas);
        if (alivios&&vtas)
            return true;
        else
            return false;
    }

    private boolean comprobarAnteriores(View v) {
        boolean inicial,stockA,stockB,stockC,resul;
        inicial = sharedPreferences.getBoolean("inicialCargado",false);
        stockA = sharedPreferences.getBoolean("SalchichasCargado",false);
        stockB = sharedPreferences.getBoolean("PanesCargado",false);
        stockC = sharedPreferences.getBoolean("GaseosasCargado",false);
        if (inicial&&stockA&&stockB&&stockC){
            resul=true;
        }else
            resul=false;
        if (!resul){
            Snackbar.make(v,R.string.todosLosCamposDebenEstarCargados,Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
        }
        return resul;
    }

    public static boolean compruebaConexion(Context context, View v) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                String comando = "ping -c 1 google.com";
                try {
                    connected = (Runtime.getRuntime().exec (comando).waitFor() == 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    connected = false;
                } catch (IOException e) {
                    e.printStackTrace();
                    connected = false;
                }
            }
        }
        if (!connected){
            Snackbar.make(v,R.string.necesitasConexion,Snackbar.LENGTH_SHORT)
                    .setAction("Bien", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
        }
        return connected;
    }

    private void guardarCampos() {
        sharedPreferences.edit()
                .putString("efectivoInicial",efeInicial)
                .putString("cajaVentas",ventas.getText().toString())
                .putString("cajaDesdeAlivios",dAlivios)
                .putString("cajaHastaAlivios",hAlivios)
                .putString("cajaTotalAlivios",tAlivios)
                .putString("cajaEfectivoFinal",efeFinal)
                .putString("cajaDiferencia",diferencia.getText().toString())

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
