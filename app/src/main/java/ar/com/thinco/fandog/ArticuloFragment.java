package ar.com.thinco.fandog;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class ArticuloFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private EditText ventas,stockInicial,compras,rotos,sinCargo,sinCargoPersonal,salidas,stockFinal;
    private String sInicial,cpras,rtos,sCargo,sCargoPers,sdas,sFinal,title;
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
        ventas = view.findViewById(R.id.fgCargaVentas);

        Button btnGuardar = view.findViewById(R.id.fgCargaGuardar);
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
    public void onStart() {
        super.onStart();
        stockInicial.setText(sharedPreferences.getString(title+"Inicial","0") );
//            sharedPreferences.edit().putBoolean(title+"Cargado",false).apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean(title+"Cargado",false)) {
            poblarFormulario();
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
            int vta = comprobarErrores();
            ventas.setText(String.valueOf(vta));

            //Aca guardo todos los valores de los campos.
            sharedPreferences.edit()
                    .putString(title+"Inicial",sInicial)
                    .putString(title+"Final",sFinal)
                    .putString(title+"Rotos",rtos)
                    .putString(title+"Compras",cpras)
                    .putString(title+"SinCargo",sCargo)
                    .putString(title+"SinCargoPersonal",sCargoPers)
                    .putString(title+"Salidas",sdas)
                    .putString(title+"Ventas",ventas.getText().toString())
                    .putBoolean(title+"Cargado",true) //aca cargo la variable para comprobar luego y devolverlos de memoria
                    .apply();
            mParentListener.onChildFragmentInteraction();
        }
    }

    private void poblarFormulario() {
        stockInicial.setText(sharedPreferences.getString(title+"Inicial","" ) );
        stockFinal.setText(sharedPreferences.getString(title+"Final","" ) );
        rotos.setText(sharedPreferences.getString(title+"Rotos","") ) ;
        compras.setText(sharedPreferences.getString(title+"Compras","" ) );
        sinCargo.setText(sharedPreferences.getString(title+"SinCargo","")) ;
        sinCargoPersonal.setText(sharedPreferences.getString(title+"SinCargoPersonal","") );
        salidas.setText(sharedPreferences.getString(title+"Salidas","") ) ;
        ventas.setText(sharedPreferences.getString(title+"Ventas","") ) ;
    }

    private int comprobarErrores() {
        int si,sf,r,c,sc,scp,sal,vta;
        si= Integer.valueOf(sInicial);
        sf = Integer.valueOf(sFinal);
        r = Integer.valueOf(rtos);
        c = Integer.valueOf(cpras);
        sc = Integer.valueOf(sCargo);
        scp = Integer.valueOf(sCargoPers);
        sal = Integer.valueOf(sdas);
        vta = si -sf+c-r-sc-scp-sal;
        return vta;
//        si = sf - c + r + sc +scp + sal + vta;
//        c = sf - si + r + sc + scp + sal + vta;
//        r = -sf + si + c - sc - scp - sal - vta;
//        sc = -sf + si + c - r - scp - sal - vta;
//        scp = -sf + si + c - r - sc - sal -vta;
//        sal = -sf + si + c -r - sc -scp - vta;
//        sf = si + c - r -sc - scp - sal - vta;
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
