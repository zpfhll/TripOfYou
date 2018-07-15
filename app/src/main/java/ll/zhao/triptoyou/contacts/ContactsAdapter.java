package ll.zhao.triptoyou.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.database.Person;

/**
 * Created by Administrator on 2018/5/3.
 */

public class ContactsAdapter extends BaseAdapter{

    private List<Person> datas;
    private Context context;

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Person getItem(int position) {
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
        holder.contactIcon.setImageBitmap(datas.get(position).getBitmapIcon());
        return convertView;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView contactName;
        TextView contactTel;
        ImageView contactIcon;

        ViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contacts_name);
            contactTel = itemView.findViewById(R.id.contacts_tel);
            contactIcon = itemView.findViewById(R.id.contacts_icon);
        }

    }

    ContactsAdapter(List<Person> datas, Context context){
        this.datas = datas;
        this.context = context;
    }



}
