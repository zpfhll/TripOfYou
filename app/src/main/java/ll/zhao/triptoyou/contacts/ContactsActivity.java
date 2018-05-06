package ll.zhao.triptoyou.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.HLLButton;

public class ContactsActivity extends BaseActivity implements ContactsListener{
    private RecyclerView recyclerView;
    private ContasctsAdater adapter;

    private HLLButton deleteBtn;
    private HLLButton doneBtn ;
    private HLLButton addBtn;
    private HLLButton backBtn;
    private TextView nodataInfo;

    private ItemTouchHelper mIth;

    private  List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.sort_listview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        nodataInfo = findViewById(R.id.nodata_info);

        deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(this);
        doneBtn = findViewById(R.id.done_button);
        doneBtn.setOnClickListener(this);
        doneBtn.setVisibility(View.GONE);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        addBtn = findViewById(R.id.add_contact_button);
        addBtn.setOnClickListener(this);


        datas = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            datas.add("data"+i);
        }


        if(datas.size() < 1){
            nodataInfo.setVisibility(View.VISIBLE);
            deleteBtn.setEnabled(false);
        }else{
            nodataInfo.setVisibility(View.GONE);
            deleteBtn.setEnabled(true);
        }

        adapter = new ContasctsAdater(datas);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        mIth  = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, ItemTouchHelper.UP) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        if( ContasctsAdater.isShowingDelete){
                            ContasctsAdater.isShowingDelete = false;
                            ContasctsAdater.showHolder.showSpring.setCurrentValue(1);
                            ContasctsAdater.showHolder.showSpring.setEndValue(0);
                        }
                        adapter.notifyItemMoved(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    }

                });
    }



    @Override
    public void onClick(View v) {
        if(clickIsDone) {
            super.baseOnClick(v);
            clickIsDone = false;
            switch (v.getId()) {
                case R.id.delete_button:
                    mIth.attachToRecyclerView(recyclerView);
                    adapter.setEditing(true);
                    doneBtn.setVisibility(View.VISIBLE);
                    deleteBtn.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.done_button:
                    mIth.attachToRecyclerView(null);
                    adapter.setEditing(false);
                    doneBtn.setVisibility(View.GONE);
                    deleteBtn.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    if( ContasctsAdater.isShowingDelete){
                        ContasctsAdater.isShowingDelete = false;
                        ContasctsAdater.showHolder.showSpring.setCurrentValue(1);
                        ContasctsAdater.showHolder.showSpring.setEndValue(0);
                    }
                    break;
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
//        if( ContasctsAdater.isShowingDelete){
//            ContasctsAdater.isShowingDelete = false;
//            ContasctsAdater.showHolder.showSpring.setCurrentValue(1);
//            ContasctsAdater.showHolder.showSpring.setEndValue(0);
//        }
        ContasctsAdater.showHolder = null;
        HLLog.showLog("ContactsActivity","deleteContact",""+position);
        adapter.notifyItemRemoved(position);
        datas.remove(position);
        adapter.notifyDataSetChanged();
        if(datas.size() < 1){
            nodataInfo.setVisibility(View.VISIBLE);
        }else{
            nodataInfo.setVisibility(View.GONE);
        }
    }
}
