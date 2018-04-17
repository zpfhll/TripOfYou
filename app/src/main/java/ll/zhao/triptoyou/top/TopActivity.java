package ll.zhao.triptoyou.top;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.TripModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;

/**
 * Created by Administrator on 2018/3/25.
 */
public class TopActivity extends FragmentActivity implements View.OnClickListener {

    private List<TripCardFragment> fragmentList;
    private TripAdpter tripAdpter;
    private ViewPager tripPage;
    private ImageView rootBackground;

    private LinearLayout menuBtn;
    private View menuBackground;
    private ImageView addTrip;
    private ImageView persons;
    private ImageView history;

    private int heigth ;
    private int width;
    private int menuBackgroundHeigth;

    private SpringSystem springSystem;
    private Spring showMenuSpring;
    private Spring closeMenuSpring;

    private boolean menuIsShowing;

    //是否应该隐藏菜单按钮
    private boolean isShouldHidenMenuBtn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_layout);

        heigth = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        menuIsShowing = false;

        tripPage = findViewById(R.id.viewPager);
        rootBackground = findViewById(R.id.root_background);
        menuBackground = findViewById(R.id.menu_background);
        menuBackground.setOnClickListener(this);
        menuBtn = findViewById(R.id.menu_button);
        menuBtn.setOnClickListener(this);
        persons = findViewById(R.id.persons);
        addTrip = findViewById(R.id.add_trip);
        history = findViewById(R.id.history);

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

        springSystem = SpringSystem.create();
        showMenuSpring = springSystem.createSpring();
        showMenuSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                double currentValue = spring.getCurrentValue();
                Log.i("--->",currentValue+"");
                ViewGroup.LayoutParams params = menuBackground.getLayoutParams();
                params.height = (int)(heigth * currentValue + menuBackgroundHeigth);
                menuBackground.setLayoutParams(params);
                menuBackground.setAlpha((float) (0.5 + 0.4 * currentValue));
                menuBtn.setRotation((float) (90 * currentValue));

                persons.setTranslationX((float) ((width/6 - persons.getWidth()/2) * currentValue));
                persons.setTranslationY((float)((heigth - 200) / 2 * currentValue));

                addTrip.setTranslationX((float) ((width/2 - addTrip.getWidth()/2) * currentValue));
                addTrip.setTranslationY((float)((heigth - 200) / 2 * currentValue));

                history.setTranslationX((float) ((width / 3 * 2 + width/6 - history.getWidth()/2) * currentValue));
                history.setTranslationY((float)((heigth - 200) / 2 * currentValue));

                addTrip.setAlpha((float) (1 * currentValue));
                persons.setAlpha((float) (1 * currentValue));
                history.setAlpha((float) (1 * currentValue));
                if(currentValue == 0 && isShouldHidenMenuBtn){
                    isShouldHidenMenuBtn = false;
                    Log.i("--->","end");
                    addTrip.setVisibility(View.GONE);
                    persons.setVisibility(View.GONE);
                    history.setVisibility(View.GONE);
                }
            }
            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {
                if(spring.getEndValue() == 0){
                    isShouldHidenMenuBtn = true;
                }
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_button:
                if(!menuIsShowing){
                    menuIsShowing = true;
                    menuBackgroundHeigth = menuBackground.getHeight();
                    addTrip.setVisibility(View.VISIBLE);
                    persons.setVisibility(View.VISIBLE);
                    history.setVisibility(View.VISIBLE);
                    showMenuSpring.setCurrentValue(0);
                    showMenuSpring.setEndValue(1);
                }else{
                    menuIsShowing = false;
                    showMenuSpring.setCurrentValue(1);
                    showMenuSpring.setEndValue(0);
                }
                break;
            case R.id.menu_background:
                if(menuIsShowing) {
                    menuIsShowing = false;
                    showMenuSpring.setCurrentValue(1);
                    showMenuSpring.setEndValue(0);
                }
                break;
            default:
                break;
        }
    }
}
