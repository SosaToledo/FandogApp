package ar.com.thinco.fandog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class CargarStocksFragment extends Fragment implements ArticuloFragment.OnFragmentInteractionListener, ArticuloFragment.OnChildFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;
    private List<Fragment> fragmentList;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private boolean PAG_UNO = false;
    private boolean PAG_DOS = false;
    private boolean PAG_TRES = false;

    public CargarStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Lista", (Serializable) fragmentList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null)
            fragmentList= (List<Fragment>) savedInstanceState.getSerializable("Lista");
        else
            fragmentList = new ArrayList<Fragment>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cargar_stocks, container, false);

        pagerAdapter = new miPagerAdapter(this.getChildFragmentManager());
        viewPager = v.findViewById(R.id.viewPagerCargarStocks);
        viewPager.setAdapter(pagerAdapter);
        return v;
    }

    public void startTrackingFragment(Fragment f) {
        fragmentList.add(f);
    }

    public void stopTrackingFragment(Fragment f) {
        fragmentList.remove(f);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.viewPagerAvanzar();
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onChildFragmentInteraction() {

        int i = viewPager.getCurrentItem()+1;
        int o = pagerAdapter.getCount();
        if (i < o){
            viewPager.setCurrentItem(i);
            switch (i){
                case 1:
                    PAG_UNO=true;
                    break;
                case 2:
                    PAG_DOS=true;
                    break;
                case 3:
                    PAG_TRES=true;
                    break;
                default:break;
            }
        }
        else if (i==o){
            if (!PAG_UNO){
                Toast.makeText(getActivity(), "Todos los articulos deben estar cargados", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
            }else if (!PAG_DOS){
                Toast.makeText(getActivity(), "Todos los articulos deben estar cargados", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
            }else {
                // TODO: 7/1/2018 en lugar de ir al metodo este, hacer otro que verifique hijo por hijo que esta todo bien o cambiar la estrutura entera
                mListener.viewPagerAvanzar();
                PAG_UNO=PAG_DOS=PAG_TRES=false;
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void viewPagerAvanzar();
    }
}
