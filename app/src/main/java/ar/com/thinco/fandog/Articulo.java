package ar.com.thinco.fandog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Articulo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Articulo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Articulo extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private EditText stockInicial;
    private String si,r,s,c,sc,scp,sf;

    public Articulo() {
    }

    public static Articulo newInstance(int sectionNumber) {
        Articulo fragment = new Articulo();
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


//        compras = view.findViewById(R.id.fgCargaCompras);
//        rotos = view.findViewById(R.id.fgCargaRotos);
//        sinCargo = view.findViewById(R.id.fgCargaSinCargo);
//        sinCargoPers = view.findViewById(R.id.fgCargaSCPersonal);
//        salidas = view.findViewById(R.id.fgCargaSalidas);
//        stockFinal = view.findViewById(R.id.fgCargaStockF);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stockInicial = view.findViewById(R.id.fgCargaStockI);
    }

//        c = compras.getText().toString().trim();
//        r =rotos.getText().toString().trim();
//        sc =sinCargo.getText().toString().trim();
//        scp = sinCargoPers.getText().toString().trim();
//        s = salidas.getText().toString().trim();
//        sf = stockFinal.getText().toString().trim();
//
//        if (si.isEmpty() || c.isEmpty() || r.isEmpty() || sc.isEmpty() || scp.isEmpty() ||
//                s.isEmpty() || sf.isEmpty() )
//            return "No pueden haber campos vacios";
//        else
//            return "Todo Ok";

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
}
