package ll.zhao.triptoyou.top;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ll.zhao.tripdatalibrary.model.TripModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripCardFragment extends Fragment {


    private TripModel tripModel;

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
        return view;
    }


}
