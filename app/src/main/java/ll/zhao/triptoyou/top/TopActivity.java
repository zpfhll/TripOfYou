package ll.zhao.triptoyou.top;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.BaseDB;
import ll.zhao.tripdatalibrary.PersonSqlDao;
import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.tripdatalibrary.model.TripModel;
import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.contacts.ContactsActivity;
import ll.zhao.triptoyou.custom.HLLButton;
import ll.zhao.triptoyou.map.MapActivity;
import ll.zhao.triptoyou.model.UserInfoViewModel;
import ll.zhao.triptoyou.userinfo.UserInfoActivity;

/**
 * Created by Administrator on 2018/3/25.
 */
public class TopActivity extends BaseActivity{

    private static final int MODIFY_REQUEST_CODE = 1;

    private List<TripCardFragment> fragmentList;
    private TripAdpter tripAdpter;
    private ViewPager tripPage;
    private ImageView rootBackground;

    private LinearLayout menuBtn;
    private View menuBackground;
    private HLLButton addTrip;
    private HLLButton persons;
    private HLLButton history;
    private HLLButton mapBtn;

    private FrameLayout menuUserInfoView;
    private FrameLayout menuUserInfoModifyView;

    private int heigth ;
    private int width;
    private int menuBackgroundHeigth;

    private SpringSystem springSystem;
    private Spring showMenuSpring;

    private boolean menuIsShowing;

    //是否应该隐藏菜单按钮
    private boolean isShouldHidenMenuBtn = false;
    //是否应该展开菜单按钮
    private boolean isShouldShowMenuBtn = false;
    //按钮是否按下
    private boolean isClicked = false;

    private PersonSqlDao personSqlDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_layout);

        heigth = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        menuIsShowing = false;
        deviceBackIsEnable = false;

        tripPage = findViewById(R.id.viewPager);
        rootBackground = findViewById(R.id.root_background);
        menuBackground = findViewById(R.id.menu_background);
        menuBackground.setOnClickListener(this);
        menuBtn = findViewById(R.id.menu_button);
        menuBtn.setOnClickListener(this);
        persons = findViewById(R.id.persons);
        persons.setOnClickListener(this);
        addTrip = findViewById(R.id.add_trip);
        history = findViewById(R.id.history);
        mapBtn = findViewById(R.id.map_button);
        mapBtn.setOnClickListener(this);
        menuUserInfoModifyView = findViewById(R.id.user_info_modify);
        menuUserInfoView = findViewById(R.id.menu_user_info);
        menuUserInfoView.setOnClickListener(this);
        menuUserInfoView.setVisibility(View.GONE);

        personSqlDao = new PersonSqlDao(this);

        getPersonInfo();

        menuBackgroundHeigth = Utils.dp2pxS(this,50);

        TripModel tripModel1 = new TripModel();
        tripModel1.setImage1(BitmapFactory.decodeResource(getResources(),R.mipmap.i1));

        fragmentList = new ArrayList<>();
        fragmentList.add(TripCardFragment.initAdd(this));

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
                persons.setTranslationY((float)(heigth / 2 * currentValue));

                addTrip.setTranslationX((float) ((width/2 - addTrip.getWidth()/2) * currentValue));
                addTrip.setTranslationY((float)(heigth  / 2 * currentValue));

                history.setTranslationX((float) ((width / 3 * 2 + width/6 - history.getWidth()/2) * currentValue));
                history.setTranslationY((float)(heigth / 2 * currentValue));

                addTrip.setAlpha((float) (1 * currentValue));
                persons.setAlpha((float) (1 * currentValue));
                history.setAlpha((float) (1 * currentValue));

                menuUserInfoView.setAlpha((float) (1 * currentValue));

                if(currentValue == 0 && isShouldHidenMenuBtn){
                    isShouldHidenMenuBtn = false;
                    Log.i("--->","end");
                    addTrip.setVisibility(View.GONE);
                    persons.setVisibility(View.GONE);
                    history.setVisibility(View.GONE);
                    isClicked = false;
                    menuUserInfoView.setVisibility(View.GONE);
                }else if(currentValue == 1 && isShouldShowMenuBtn) {
                    isClicked = false;
                }
            }
            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {
                if(spring.getEndValue() == 0){
                    isShouldHidenMenuBtn = true;
                }else if(spring.getEndValue() == 1){
                    isShouldShowMenuBtn = true;
                    menuUserInfoView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });

    }


    private void getPersonInfo(){
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                PersonModel personModel = personSqlDao.getSelfData();
                UserInfoFragment userInfoFragment = UserInfoFragment.newInstance(personModel);
                addFragement(userInfoFragment,R.id.menu_user_info);
            }
        });
    }


    @Override
    public void baseOnClick(View v) {
        if(clickIsDone){
            super.baseOnClick(v);
            clickIsDone = false;
            switch (v.getId()){
                case R.id.menu_button:
                    if(!isClicked) {
                        isClicked = true;
                        if (!menuIsShowing) {
                            menuIsShowing = true;
//                        menuBackgroundHeigth = menuBackground.getHeight();
                            addTrip.setVisibility(View.VISIBLE);
                            persons.setVisibility(View.VISIBLE);
                            history.setVisibility(View.VISIBLE);
                            showMenuSpring.setCurrentValue(0);
                            showMenuSpring.setEndValue(1);
                        } else {
                            menuIsShowing = false;
                            showMenuSpring.setCurrentValue(1);
                            showMenuSpring.setEndValue(0);
                        }
                    }
                    break;
                case R.id.menu_background:
                    if(menuIsShowing) {
                        menuIsShowing = false;
                        showMenuSpring.setCurrentValue(1);
                        showMenuSpring.setEndValue(0);
                    }
                    break;
                case R.id.map_button:
                    Intent intent = new Intent(TopActivity.this, MapActivity.class);
                    startActivity(intent);
                    if(menuIsShowing) {
                        menuIsShowing = false;
                        showMenuSpring.setCurrentValue(1);
                        showMenuSpring.setEndValue(0);
                    }
                    break;
                case R.id.menu_user_info:
                    if(menuIsShowing) {
                        menuIsShowing = false;
                        showMenuSpring.setCurrentValue(1);
                        showMenuSpring.setEndValue(0);
                    }
                    PersonModel personModel = personSqlDao.getSelfData();
//                    UserInfoModifyFragment userInfoFragment = UserInfoModifyFragment.newInstance(personModel);
//                    addFragement(userInfoFragment,R.id.user_info_modify);
//                    menuUserInfoModifyView.setVisibility(View.VISIBLE);
                    Intent modifyIntent = new Intent(TopActivity.this, UserInfoActivity.class);
                    modifyIntent.putExtra("userInfo",personModel);
                    startActivityForResult(modifyIntent,MODIFY_REQUEST_CODE);
                    break;
                case R.id.persons:
                    Intent intent1 = new Intent(TopActivity.this, ContactsActivity.class);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
            clickIsDone = true;
        }
    }

    /**
     * 添加fragment
     * @param fragment
     * @param resId
     */
    private void addFragement(Fragment fragment,int resId){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(resId,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PersonModel personModel = personSqlDao.getSelfData();
        ViewModelProviders.of(this).get(UserInfoViewModel.class).getUserInfo().setValue(personModel);
    }

//    public void hiddenUserInfoModify(boolean isRefresh){
//        if(menuUserInfoModifyView.getVisibility() == View.VISIBLE){
//            menuUserInfoModifyView.setVisibility(View.GONE);
//            getSupportFragmentManager().popBackStack();
//            if(isRefresh){
//                PersonModel personModel = personSqlDao.getSelfData();
//                ViewModelProviders.of(this).get(UserInfoViewModel.class).getUserInfo().setValue(personModel);
//            }
//        }
//    }

}
