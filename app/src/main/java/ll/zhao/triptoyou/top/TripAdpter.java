package ll.zhao.triptoyou.top;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TripAdpter extends FragmentPagerAdapter {

    private List<TripCardFragment> fragments;

    public TripAdpter(FragmentManager fm,List<TripCardFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
