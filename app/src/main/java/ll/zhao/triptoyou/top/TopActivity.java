package ll.zhao.triptoyou.top;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.TripModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;

/**
 * Created by Administrator on 2018/3/25.
 */
public class TopActivity extends FragmentActivity {

    private List<TripCardFragment> fragmentList;
    private TripAdpter tripAdpter;
    private ViewPager tripPage;
    private ImageView rootBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_layout);

        tripPage = findViewById(R.id.viewPager);
        rootBackground = findViewById(R.id.root_background);

        TripModel tripModel1 = new TripModel();
        tripModel1.setImage1(BitmapFactory.decodeResource(getResources(),R.mipmap.i1));
        TripModel tripModel2 = new TripModel();
        tripModel2.setImage1(BitmapFactory.decodeResource(getResources(),R.mipmap.i2));
        TripModel tripModel3 = new TripModel();
        tripModel3.setImage1(BitmapFactory.decodeResource(getResources(),R.mipmap.i3));
        TripModel tripModel4 = new TripModel();
        tripModel4.setImage1(BitmapFactory.decodeResource(getResources(),R.mipmap.i4));

        fragmentList = new ArrayList<>();
        fragmentList.add(TripCardFragment.init(tripModel1));
        fragmentList.add(TripCardFragment.init(tripModel2));
        fragmentList.add(TripCardFragment.init(tripModel3));
        fragmentList.add(TripCardFragment.init(tripModel4));


        tripAdpter = new TripAdpter(getSupportFragmentManager(),fragmentList);
        tripPage.setOffscreenPageLimit(3);
        tripPage.setPageMargin(-Utils.dp2px(this,40));
        tripPage.setAdapter(tripAdpter);

        rootBackground.setImageBitmap(Utils.toBlur(fragmentList.get(tripPage.getCurrentItem()).getTripModel().getImage1(),10));
        tripPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rootBackground.setImageBitmap(Utils.toBlur(fragmentList.get(position).getTripModel().getImage1(),10));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
