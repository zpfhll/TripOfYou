package ll.zhao.triptoyou.contacts;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.HLLButton;

public class ContactsActivity extends BaseActivity implements ContactsListener{
    private ListView listView;
    private ContasctsAdater adapter;

    private HLLButton addBtn;
    private HLLButton backBtn;
    private TextView nodataInfo;

    private  List<PersonModel> datas;
    private FrameLayout contactDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = findViewById(R.id.sort_listview);
        contactDetailFragment = findViewById(R.id.contact_detail_fragment);
        nodataInfo = findViewById(R.id.nodata_info);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        addBtn = findViewById(R.id.add_contact_button);
        addBtn.setOnClickListener(this);
        datas = new ArrayList<>();

        for (int i = 0; i <10; i++) {
            PersonModel model = new PersonModel();
            model.setName("user"+i);
            model.setTel("182297756"+i);
            model.setIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.home));
            datas.add(model);
        }

        if(datas.size() < 1){
            nodataInfo.setVisibility(View.VISIBLE);
        }else{
            nodataInfo.setVisibility(View.GONE);
        }

        adapter = new ContasctsAdater(datas,this);
        listView.setAdapter(adapter);
        adapter.setListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactDetailFragment userInfoFragment = ContactDetailFragment.newInstance(datas.get(position));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contact_detail_fragment,userInfoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                contactDetailFragment.setVisibility(View.VISIBLE);
            }
        });

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
                    break;
            }
        }
        clickIsDone = true;
    }

    @Override
    public void deleteContact(int position) {
        HLLog.showLog("ContactsActivity","deleteContact",""+position);
        datas.remove(position);
        adapter.notifyDataSetChanged();
        if(datas.size() < 1){
            nodataInfo.setVisibility(View.VISIBLE);
        }else{
            nodataInfo.setVisibility(View.GONE);
        }
    }
}
