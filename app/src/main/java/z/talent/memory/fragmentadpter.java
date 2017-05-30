package z.talent.memory;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */

public class fragmentadpter extends FragmentPagerAdapter {
ArrayList<Fragment> fragmentitems=new ArrayList<Fragment>();
    public fragmentadpter(FragmentManager fragmentManager,ArrayList<Fragment> fragmentitems){
    super(fragmentManager);
    this.fragmentitems=fragmentitems;
}

    @Override
    public Fragment getItem(int position) {
        return fragmentitems.get(position);
    }

    @Override
    public int getCount() {
        return fragmentitems.size();
    }

}
