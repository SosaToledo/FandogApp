package ar.com.thinco.fandog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class miPagerAdapter extends FragmentPagerAdapter {
    private static final CharSequence FRAG0 = "Salchichas";
    private static final CharSequence FRAG1 = "Panes";
    private static final CharSequence FRAG2 = "Gaseosa de Vidrio";


    private static final int COUNT = 3;

    public miPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ArticuloFragment a = new ArticuloFragment();
        switch (position){
            case 0:a.setTitle("Salchichas");break;
            case 1:a.setTitle("Panes");break;
            case 2:a.setTitle("Gaseosas");break;
            default:break;
        }
        return a;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return FRAG0;
            case 1:
                return FRAG1;
            case 2:
                return FRAG2;
            default:
                return null;
        }
    }
}