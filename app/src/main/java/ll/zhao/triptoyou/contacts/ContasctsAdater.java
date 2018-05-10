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

import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.custom.HLLButton;

/**
 * Created by Administrator on 2018/5/3.
 */

public class ContasctsAdater extends BaseAdapter{

    private List<PersonModel> datas;
    public static boolean isShowingDelete;
    public static ViewHolder showHolder;

    private boolean isEditing;

    private Context context;

    private ContactsListener listener;

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public PersonModel getItem(int position) {
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
                    .inflate(R.layout.contacts_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.contactName.setText(datas.get(position).getName());
        holder.contactTel.setText(datas.get(position).getTel());
        holder.contactIcon.setImageBitmap(datas.get(position).getIcon());
        return convertView;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView contactName;
        public TextView contactTel;
        public ImageView contactIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contacts_name);
            contactTel = itemView.findViewById(R.id.contacts_tel);
            contactIcon = itemView.findViewById(R.id.contacts_icon);
        }

    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ContasctsAdater(List<PersonModel> datas,Context context){
        this.datas = datas;
        this.context = context;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public void setListener(ContactsListener listener) {
        this.listener = listener;
    }

}
