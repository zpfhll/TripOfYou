package ll.zhao.triptoyou.top;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.FootPrintModel;
import ll.zhao.triptoyou.R;

/**
 * Created by Administrator on 2018/4/14.
 */

public class TripFootprintAdapter extends RecyclerView.Adapter<TripFootprintAdapter.ViewHolder> {

    private List<FootPrintModel> mData;

    public TripFootprintAdapter(List<FootPrintModel> data) {
        this.mData = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_print, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FootPrintModel model = mData.get(position);

        if(model.getFootPrintType().equals("do")){
            holder.timeLine.setImageResource(R.mipmap.time_line_add);
            holder.footContent.setVisibility(View.GONE);
            holder.footImage.setImageResource(R.mipmap.add_card);
            holder.footImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 足迹添加
                    Log.i("add","add footprint");
                }
            });

        }else if(model.getFootPrintType().equals("end")){
            holder.timeLine.setImageResource(R.mipmap.time_line_add);
            holder.footContent.setVisibility(View.GONE);
            holder.footImage.setVisibility(View.GONE);
        }else {
            if (position == 0) {//第一个
                holder.timeLine.setImageResource(R.mipmap.time_line_first);
                holder.footContent.setVisibility(View.GONE);
                holder.footImage.setVisibility(View.GONE);
            } else {
                // 绑定数据
                if(model.getIcon() != null){
                    holder.footImage.setImageBitmap(model.getIcon());
                }
                holder.footContent.setText(model.getFootPrintLocation());
            }
        }
        holder.time.setText(model.getFootPrintTime());
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView timeLine;
        ImageView footImage;
        TextView footContent;
        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.footprint_time);
            timeLine = itemView.findViewById(R.id.footprint_time_line);
            footImage = itemView.findViewById(R.id.footprint_image);
            footContent = itemView.findViewById(R.id.footprint_content);
        }
    }

}
