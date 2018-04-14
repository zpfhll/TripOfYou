package ll.zhao.triptoyou.top;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.TripModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripCardFragment extends Fragment {


    private TripModel tripModel;
    private LinearLayoutManager mLayoutManager;

    public TripCardFragment() {
    }



    public static TripCardFragment init(TripModel tripModel){
        TripCardFragment fragment = new TripCardFragment();
        fragment.setTripModel(tripModel);
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
        ImageView imageView = view.findViewById(R.id.card_image1);
        imageView.setClipToOutline(true);
        imageView.setImageBitmap(tripModel.getImage1());
        RecyclerView footView = view.findViewById(R.id.foot_prints);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        // 设置布局管理器
        footView.setLayoutManager(mLayoutManager);
        // 设置adapter
        List<String> data = new ArrayList<String>();
        data.add("2017/10/10 12:30");
        data.add("2017/10/11 09:30");
        data.add("2017/10/12 12:30");
        data.add("2017/10/13 12:30");
        TripFootprintAdapter tripFootprintAdapter = new TripFootprintAdapter(data);
        footView.setAdapter(tripFootprintAdapter);
        // 设置Item添加和移除的动画
        footView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }


}
