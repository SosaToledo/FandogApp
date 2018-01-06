package ar.com.thinco.fandog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InicialFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private TextView nombreVendedor;
    private EditText iniSalchichas, iniPanes, iniGaseosas,efectivoEnCaja;
    private Spinner spinnerTurno,spinnerLocal;
    private Button btnGuardarIni;

    public InicialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicial, container, false);

//        Aca seteamos los spinners y los rellenamos
        spinnerTurno = view.findViewById(R.id.spinnerTurno);
        spinnerLocal = (Spinner) view.findViewById(R.id.spinnerLocal);
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
        iniSalchichas = view.findViewById(R.id.iniSalchichas);
        iniPanes = view.findViewById(R.id.iniPanes);
        iniGaseosas = view.findViewById(R.id.iniGaseosas);
        btnGuardarIni = view.findViewById(R.id.btnGuardarIni);
        btnGuardarIni.setOnClickListener(this);
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void onClick(View view) {
        // TODO: 6/1/2018 Falta comprobar los campos y despues hacer que no avance hasta que este todo cargado
        int id = view.getId();
        if (id == R.id.btnGuardarIni){
            Toast.makeText(getActivity(), "entro bien", Toast.LENGTH_SHORT).show();
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
}
