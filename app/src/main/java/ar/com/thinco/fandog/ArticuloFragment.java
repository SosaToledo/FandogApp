package ar.com.thinco.fandog;

import android.content.Context;
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
    private String sInicial,cpras,rtos,sCargo,sCargoPers,sdas,sFinal;
    private Button btnGuardar;
    private OnChildFragmentInteractionListener mParentListener;

    public ArticuloFragment() {
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
            // TODO: 7/1/2018 Comprobar errores al cargar
            mParentListener.onChildFragmentInteraction();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public interface OnChildFragmentInteractionListener{
        void onChildFragmentInteraction();
    }
}
