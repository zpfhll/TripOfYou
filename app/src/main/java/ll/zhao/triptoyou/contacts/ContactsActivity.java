package ll.zhao.triptoyou.contacts;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.SortTableListView;

public class ContactsActivity extends BaseActivity {
    private SortTableListView sortTableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        sortTableListView = findViewById(R.id.sort_listview);
        List<String> datas = new ArrayList<>();
        datas.add("323423");
        datas.add("asdfasdf");
        datas.add("asdfasdfewllwll");
        ContasctsAdater adater = new ContasctsAdater(this,datas);
        sortTableListView.setAdapter(adater);
    }
}
