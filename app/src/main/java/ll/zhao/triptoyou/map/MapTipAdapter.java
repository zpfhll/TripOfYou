package ll.zhao.triptoyou.map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.help.Tip;

import java.util.List;

import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.contacts.ContactsAdapter;


public class MapTipAdapter extends BaseAdapter{


    private List<Tip> datas;
    private Context context;

    MapTipAdapter(List<Tip> datas, Context context){
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Tip getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.tips_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tipLocation.setText(datas.get(position).getDistrict() + " " +datas.get(position).getAddress());
        holder.tipName.setText(datas.get(position).getName());
        return convertView;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tipName;
        TextView tipLocation;

        ViewHolder(View itemView) {
            super(itemView);
            tipLocation = itemView.findViewById(R.id.tip_location);
            tipName = itemView.findViewById(R.id.tip_name);
        }

    }
}
