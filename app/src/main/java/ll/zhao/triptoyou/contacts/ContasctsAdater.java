package ll.zhao.triptoyou.contacts;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import java.util.List;

import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.custom.HLLButton;

/**
 * Created by Administrator on 2018/5/3.
 */

public class ContasctsAdater extends RecyclerView.Adapter<ContasctsAdater.ViewHolder> {

    private List<String> datas;
    public static boolean isShowingDelete;
    public static ViewHolder showHolder;

    public static boolean isEditing;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView contactName;
        public TextView contactTel;
        public HLLButton delete;
        public ImageView contactIcon;
        public ConstraintLayout contactItem;
        public SpringSystem springSystem;
        public Spring showSpring;

        public Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete_contact_btn); // to ItemButton
            contactItem = itemView.findViewById(R.id.contacts_item);
            contactName = itemView.findViewById(R.id.contacts_name);
            contactTel = itemView.findViewById(R.id.contacts_tel);
            contactIcon = itemView.findViewById(R.id.contacts_icon);
            springSystem = SpringSystem.create();
            showSpring = springSystem.createSpring();
            showSpring.addListener(new SpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    double currentValue = spring.getCurrentValue();
                    Log.i("--->",currentValue+"");
                    float distance = (float)( Utils.dp2pxS(context,80) * currentValue);
                    contactItem.setTranslationX(-distance);
                }
                @Override
                public void onSpringAtRest(Spring spring) {

                }

                @Override
                public void onSpringActivate(Spring spring) {

                }

                @Override
                public void onSpringEndStateChange(Spring spring) {
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isShowingDelete){
                        showHolder = (ViewHolder) v.getTag();
                        showHolder.showSpring.setEndValue(1);
                        isShowingDelete = true;
                    }else{
                        isShowingDelete = false;
                        showHolder.showSpring.setCurrentValue(1);
                        showHolder.showSpring.setEndValue(0);
                    }
                }
            });
            contactItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isShowingDelete){
                        isShowingDelete = false;
                        showHolder.showSpring.setCurrentValue(1);
                        showHolder.showSpring.setEndValue(0);
                    }
                }
            });

        }

    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ContasctsAdater(List<String> datas){
        this.datas = datas;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.context = parent.getContext();
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.contactName.setText(datas.get(position));
        holder.delete.setTag(holder);
        if(isShowingDelete){
            isShowingDelete = false;
            showHolder.showSpring.setCurrentValue(1);
            showHolder.showSpring.setEndValue(0);
        }
        if(!isEditing){
            holder.delete.setVisibility(View.GONE);
        }else{
            holder.delete.setVisibility(View.VISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return datas.size();
    }
//
//
//    @Override
//    public int getCount() {
//        return datas.size();
//    }
//
//    @Override
//    public String getItem(int position) {
//        return datas.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder holder;
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.contacts_item, null);
//            holder = new ViewHolder();
//                /*得到各个控件的对象*/
//            holder.delete = convertView.findViewById(R.id.delete_contact_btn); // to ItemButton
//            holder.contactItem = convertView.findViewById(R.id.contacts_item);
//            holder.contactName = convertView.findViewById(R.id.contacts_name);
//            holder.contactTel = convertView.findViewById(R.id.contacts_tel);
//            holder.contactIcon = convertView.findViewById(R.id.contacts_icon);
//
//            holder.springSystem = SpringSystem.create();
//            holder.showMenuSpring = holder.springSystem.createSpring();
//            holder.showMenuSpring.addListener(new SpringListener() {
//                @Override
//                public void onSpringUpdate(Spring spring) {
//                    double currentValue = spring.getCurrentValue();
//                    Log.i("--->",currentValue+"");
//                    float distance = (float)( Utils.dp2pxS(context,80) * currentValue);
//                    holder.contactItem.setTranslationX(-distance);
//                }
//                @Override
//                public void onSpringAtRest(Spring spring) {
//
//                }
//
//                @Override
//                public void onSpringActivate(Spring spring) {
//
//                }
//
//                @Override
//                public void onSpringEndStateChange(Spring spring) {
//                }
//            });
//
//            convertView.setTag(holder); //绑定ViewHolder对象
//        }
//        else {
//            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
//        }
//        holder.contactName.setText(datas.get(position));
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!isShowingDelete){
//                    holder.showMenuSpring.setEndValue(1);
//                    isShowingDelete = true;
//                }else{
//                    isShowingDelete = false;
//                    holder.showMenuSpring.setCurrentValue(1);
//                    holder.showMenuSpring.setEndValue(0);
//                }
//            }
//        });
//
//        return convertView;
//    }
//
//    /*存放控件 的ViewHolder*/

}
