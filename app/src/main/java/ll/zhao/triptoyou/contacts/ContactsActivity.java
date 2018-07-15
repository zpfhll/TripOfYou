package ll.zhao.triptoyou.contacts;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.HLLButton;
import ll.zhao.triptoyou.database.DataManager;
import ll.zhao.triptoyou.database.Person;

public class ContactsActivity extends BaseActivity{
    private ListView listView;
    private ContactsAdapter adapter;

    private HLLButton addBtn;
    private HLLButton backBtn;
    private TextView noDataInfo;

    private  List<Person> datas;
    private FrameLayout contactDetailFragmentLayout;
    private View contactDetailBackground;
    private ContactDetailFragment contactDetailFragment;

    private Runnable refreshRunnable;

    public ContactsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = findViewById(R.id.sort_listview);
        contactDetailFragmentLayout = findViewById(R.id.contact_detail_fragment);
        contactDetailBackground = findViewById(R.id.contact_detail_background);
        contactDetailFragmentLayout.setVisibility(View.GONE);
        contactDetailBackground.setVisibility(View.GONE);
        noDataInfo = findViewById(R.id.nodata_info);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        addBtn = findViewById(R.id.add_contact_button);
        addBtn.setOnClickListener(this);
        datas = new ArrayList<>();

        if(datas.size() < 1){
            noDataInfo.setVisibility(View.VISIBLE);
        }else{
            noDataInfo.setVisibility(View.GONE);
        }

        adapter = new ContactsAdapter(datas,this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(contactDetailFragment == null){
                    contactDetailFragment = ContactDetailFragment.newInstance(datas.get(position),position);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contact_detail_fragment,contactDetailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else{
                    contactDetailFragment.refreshView(datas.get(position),position);
                }

                contactDetailFragmentLayout.setVisibility(View.VISIBLE);
                contactDetailBackground.setVisibility(View.VISIBLE);
            }
        });

        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                datas.clear();
                datas.addAll(DataManager.getAllFriend());
                if(datas.size() < 1){
                    noDataInfo.setVisibility(View.VISIBLE);
                }else{
                    noDataInfo.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        new Handler(getMainLooper()).post(refreshRunnable);
    }

    @Override
    public void onClick(View v) {
        if(clickIsDone) {
            super.baseOnClick(v);
            clickIsDone = false;
            switch (v.getId()) {
                case R.id.back_btn:
                    closeActivity();
                    break;
                case R.id.add_contact_button:
                    if(contactDetailFragment == null){
                        contactDetailFragment = ContactDetailFragment.newInstance(null,0);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.contact_detail_fragment,contactDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else{
                        contactDetailFragment.refreshView(null,0);
                    }
                    contactDetailFragmentLayout.setVisibility(View.VISIBLE);
                    contactDetailBackground.setVisibility(View.VISIBLE);
                    break;
            }
        }
        clickIsDone = true;
    }

    public void deleteContact(int position) {
        HLLog.showLog("ContactsActivity","deleteContact",""+position);
        DataManager.deletePerson(datas.get(position));
        datas.remove(position);
        adapter.notifyDataSetChanged();
        if (datas.size() < 1) {
            noDataInfo.setVisibility(View.VISIBLE);
        } else {
            noDataInfo.setVisibility(View.GONE);
        }
    }

    public void refreshContacts(){
        new Handler(getMainLooper()).post(refreshRunnable);
    }

    @Override
    public void onBackPressed() {
        contactDetailFragmentLayout.setVisibility(View.GONE);
        contactDetailBackground.setVisibility(View.GONE);
    }



}
