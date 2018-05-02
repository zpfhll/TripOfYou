package ll.zhao.triptoyou.contacts;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
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

public class ContasctsAdater extends BaseAdapter {

    private List<String> datas;
    private LayoutInflater mInflater;
    private Context context;

    public static boolean isShowingDelete = false;


    public ContasctsAdater(Context context,List<String> datas){
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contacts_item, null);
            holder = new ViewHolder();
                /*得到各个控件的对象*/
            holder.delete = convertView.findViewById(R.id.delete_contact_btn); // to ItemButton
            holder.contactItem = convertView.findViewById(R.id.contacts_item);
            holder.contactName = convertView.findViewById(R.id.contacts_name);
            holder.contactTel = convertView.findViewById(R.id.contacts_tel);
            holder.contactIcon = convertView.findViewById(R.id.contacts_icon);

            holder.springSystem = SpringSystem.create();
            holder.showMenuSpring = holder.springSystem.createSpring();
            holder.showMenuSpring.addListener(new SpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    double currentValue = spring.getCurrentValue();
                    Log.i("--->",currentValue+"");
                    float distance = (float)( Utils.dp2pxS(context,80) * currentValue);
                    holder.contactItem.setTranslationX(-distance);
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

            convertView.setTag(holder); //绑定ViewHolder对象
        }
        else {
            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
        }
        holder.contactName.setText(datas.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowingDelete){
                    holder.showMenuSpring.setEndValue(1);
                    isShowingDelete = true;
                }else{
                    isShowingDelete = false;
                    holder.showMenuSpring.setCurrentValue(1);
                    holder.showMenuSpring.setEndValue(0);
                }
            }
        });

        return convertView;
    }

    /*存放控件 的ViewHolder*/
    public final class ViewHolder {
        public TextView contactName;
        public TextView contactTel;
        public HLLButton delete;
        public ImageView contactIcon;
        public ConstraintLayout contactItem;

        private SpringSystem springSystem;
        private Spring showMenuSpring;
    }
}
