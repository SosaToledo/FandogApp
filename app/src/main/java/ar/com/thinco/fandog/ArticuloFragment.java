package ar.com.thinco.fandog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticuloFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticuloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticuloFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private EditText stockInicial,compras,rotos,sinCargo,sinCargoPersonal,salidas,stockFinal;
    private String sInicial,cpras,rtos,sCargo,sCargoPers,sdas,sFinal,title;
    private Button btnGuardar;
    private OnChildFragmentInteractionListener mParentListener;
    private SharedPreferences sharedPreferences;

    public ArticuloFragment( ) {
    }

    public static ArticuloFragment newInstance(int sectionNumber) {
        ArticuloFragment fragment = new ArticuloFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articulo, container, false);

        stockInicial = view.findViewById(R.id.fgCargaStockI);
        compras = view.findViewById(R.id.fgCargaCompras);
        rotos = view.findViewById(R.id.fgCargaRotos);
        sinCargo = view.findViewById(R.id.fgCargaSinCargo);
        sinCargoPersonal = view.findViewById(R.id.fgCargaSCPersonal);
        salidas = view.findViewById(R.id.fgCargaSalidas);
        stockFinal = view.findViewById(R.id.fgCargaStockF);

        btnGuardar = view.findViewById(R.id.fgCargaGuardar);
        btnGuardar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ( (CargarStocksFragment) getParentFragment()).startTrackingFragment(this);
    }

    @Override
    public void onDestroy() {
        ( (CargarStocksFragment) getParentFragment()).stopTrackingFragment(this);
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stockInicial = view.findViewById(R.id.fgCargaStockI);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean(title+"Cargado",false)) {
            stockInicial.setText(sharedPreferences.getString("inicial"+title,"0"));
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

        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnChildFragmentInteractionListener) {
            mParentListener = (OnChildFragmentInteractionListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement OnChildFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        sInicial = stockInicial.getText().toString();
        cpras = compras.getText().toString();
        rtos = rotos.getText().toString();
        sCargo = sinCargo.getText().toString();
        sCargoPers = sinCargoPersonal.getText().toString();
        sdas = salidas.getText().toString();
        sFinal = stockFinal.getText().toString();

        if (sInicial.isEmpty()||cpras.isEmpty()||rtos.isEmpty()||sCargo.isEmpty()
                ||sCargoPers.isEmpty()||sdas.isEmpty()||sFinal.isEmpty()){
            Toast.makeText(getActivity(), "No pueden haber campos vacios.", Toast.LENGTH_SHORT).show();
        }else {
            if (comprobarErrores())
            // TODO: 7/1/2018 Comprobar errores al cargar
              mParentListener.onChildFragmentInteraction();
        }
    }

    private boolean comprobarErrores() {
        int si,sf,r,c,sc,scp,sal;
        sInicial=stockInicial.getText().toString();
        sFinal=stockFinal.getText().toString();
        rtos=rotos.getText().toString();
        cpras = compras.getText().toString();
        sCargo = sinCargo.getText().toString();
        sCargoPers = sinCargoPersonal.getText().toString();
        sdas = salidas.getText().toString();

        si = Integer.parseInt(sInicial);
        sf = Integer.parseInt(sFinal);
        r = Integer.parseInt(rtos);
        c = Integer.parseInt(cpras);
        sc = Integer.parseInt(sCargo);
        scp = Integer.parseInt(sCargoPers);
        sal = Integer.parseInt(sdas);

        //Comienzan las comprobaciones.
        int totalSuma, totalResta, vta;
        totalSuma = c + si;
        totalResta = r + sc + scp + sal + sf;
        vta = totalSuma - totalResta;
        int hola = 0;
        Log.i("comprobarErrores",String.valueOf(c));
        if (si < sf)
            hola = -si+r+sc+scp+sal+vta+sf;
            Log.i("comprobacion",String.valueOf(hola));
            if (c != -si+r+sc+scp+sal+vta+sf)
                new AlertDialog.Builder(getActivity()).setTitle("Problema con el stock")
                    .setMessage("Puede que hayas equivocado en las compras, volve a contar, deberian ser como: "+ (-si+r+sc+scp+sal+vta+sf) )
                    .show();
            return false;
//        sharedPreferences.edit().putBoolean(title+"Cargado",true).commit();
    }

    public void setTitle(String s) {
        title = s;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public interface OnChildFragmentInteractionListener{
        void onChildFragmentInteraction();
    }
}
