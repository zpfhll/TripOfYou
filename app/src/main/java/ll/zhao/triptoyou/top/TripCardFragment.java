package ll.zhao.triptoyou.top;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.TripEnum;
import ll.zhao.tripdatalibrary.model.FootPrintModel;
import ll.zhao.tripdatalibrary.model.TripModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.custom.HLLButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripCardFragment extends Fragment implements View.OnClickListener {


    private TripModel tripModel;

    private boolean isTrip = true;

    //图片
    private ImageView cardImage1;
    //开始日的区域
    private LinearLayout  startTimeLayout;
    //开始日
    private TextView startTime;
    //结束按钮
    private HLLButton stopButton;
    //新规按钮
    private HLLButton addButton;
    //结算按钮
    private HLLButton moneyButton;
    //消除按钮
    private HLLButton deleteButton;
    //同行者
    private HLLButton contactsButton;
    //明细
    private HLLButton detailButton;
    //旅行名
    private TextView tripName;
    //旅行地点
    private TextView tripLocation;
    //足迹
    private RecyclerView footPrints;

    private TextView noDataInfo;

    private LinearLayoutManager mLayoutManager;

    public TripCardFragment() {
    }

    public static TripCardFragment init(TripModel tripModel){
        TripCardFragment fragment = new TripCardFragment();
        fragment.setTripModel(tripModel);
        return fragment;
    }

    public static TripCardFragment initAdd(Context context){
        TripCardFragment fragment = new TripCardFragment();
        TripModel tripModel =new TripModel();
        tripModel.setImage1(BitmapFactory.decodeResource(context.getResources(),R.mipmap.defualt));
        fragment.setTripModel(tripModel);
        fragment.setTrip(false);
        return fragment;
    }

    public void setTripModel(TripModel tripModel) {
        this.tripModel = tripModel;
    }
    public TripModel getTripModel() {
        return tripModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_card, container, false);
        cardImage1 = view.findViewById(R.id.card_image1);
        startTimeLayout = view.findViewById(R.id.start_time_layout);
        startTime = view.findViewById(R.id.trip_start_date);
        stopButton = view.findViewById(R.id.stop_trip);
        addButton = view.findViewById(R.id.new_trip);
        moneyButton = view.findViewById(R.id.money_trip);
        deleteButton = view.findViewById(R.id.delete_card);
        contactsButton = view.findViewById(R.id.contacts);
        detailButton = view.findViewById(R.id.detail);
        tripName = view.findViewById(R.id.trip_name);
        tripLocation = view.findViewById(R.id.trip_location);
        footPrints = view.findViewById(R.id.foot_prints);
        noDataInfo = view.findViewById(R.id.nodata_info);

        cardImage1.setClipToOutline(true);
        if(!isTrip){
            startTimeLayout.setVisibility(View.GONE);
            stopButton.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
            moneyButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            contactsButton.setVisibility(View.GONE);
            detailButton.setVisibility(View.GONE);
            tripName.setVisibility(View.GONE);
            tripLocation.setVisibility(View.GONE);
            footPrints.setVisibility(View.GONE);
            noDataInfo.setVisibility(View.VISIBLE);
        }else{
            noDataInfo.setVisibility(View.GONE);
            startTimeLayout.setVisibility(View.VISIBLE);
            switch (tripModel.getState()){
                case TripEnum.TRIP_STATE_DOING:
                    stopButton.setVisibility(View.VISIBLE);
                    addButton.setVisibility(View.GONE);
                    moneyButton.setVisibility(View.GONE);
                    break;
                case TripEnum.TRIP_STATE_NOTDO:
                    stopButton.setVisibility(View.GONE);
                    addButton.setVisibility(View.GONE);
                    moneyButton.setVisibility(View.VISIBLE);
                    break;
                case TripEnum.TRIP_STATE_DONE:
                    stopButton.setVisibility(View.GONE);
                    addButton.setVisibility(View.GONE);
                    moneyButton.setVisibility(View.GONE);
                    break;
            }
            deleteButton.setVisibility(View.VISIBLE);
            contactsButton.setVisibility(View.VISIBLE);
            detailButton.setVisibility(View.VISIBLE);
            tripName.setVisibility(View.VISIBLE);
            tripLocation.setVisibility(View.VISIBLE);
            footPrints.setVisibility(View.VISIBLE);
            if(tripModel.getImage1() != null) {
                cardImage1.setImageBitmap(tripModel.getImage1());
            }
            startTime.setText(tripModel.getStart_time());
            tripName.setText(tripModel.getTrip_name());
            tripLocation.setText(tripModel.getLocation());

            stopButton.setOnClickListener(this);
            addButton.setOnClickListener(this);
            moneyButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            contactsButton.setOnClickListener(this);
            detailButton.setOnClickListener(this);


            mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            // 设置布局管理器
            footPrints.setLayoutManager(mLayoutManager);
            // 设置adapter
            List<FootPrintModel> data = new ArrayList<FootPrintModel>();
            FootPrintModel startFootPrintModel = new FootPrintModel();
            startFootPrintModel.setFootPrintTime(getString(R.string.footprint_start));
            startFootPrintModel.setFootPrintType("start");
            data.add(startFootPrintModel);

            //TODO dummydata
            for (int i = 0; i < 3; i++) {
                FootPrintModel footPrintModel = new FootPrintModel();
                footPrintModel.setFootPrintTime("2018/05/15 03:04");
                footPrintModel.setFootPrintType("normal");
            }

            if(tripModel.getState() == TripEnum.TRIP_STATE_DOING){
                FootPrintModel endFootPrintModel = new FootPrintModel();
                endFootPrintModel.setFootPrintTime(getString(R.string.footprint_now));
                endFootPrintModel.setFootPrintType("do");
                data.add(startFootPrintModel);
            }else{
                FootPrintModel endFootPrintModel = new FootPrintModel();
                endFootPrintModel.setFootPrintTime(getString(R.string.footprint_done));
                endFootPrintModel.setFootPrintType("end");
                data.add(startFootPrintModel);
            }

            TripFootprintAdapter tripFootprintAdapter = new TripFootprintAdapter(data);
            footPrints.setAdapter(tripFootprintAdapter);
            // 设置Item添加和移除的动画
            footPrints.setItemAnimator(new DefaultItemAnimator());

        }


        return view;
    }

    public void setTrip(boolean trip) {
        isTrip = trip;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stop_trip:
                break;
            case R.id.new_trip:
                break;
            case R.id.money_trip:
                break;
            case R.id.delete_card:
                break;
            case R.id.contacts:
                break;
            case R.id.detail:
                break;
        }
    }
}
