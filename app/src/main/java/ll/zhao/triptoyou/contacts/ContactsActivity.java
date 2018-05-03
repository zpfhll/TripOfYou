package ll.zhao.triptoyou.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.HLLButton;

public class ContactsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ContasctsAdater adapter;

    private HLLButton delete;
    private HLLButton done ;
    private ItemTouchHelper mIth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.sort_listview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            datas.add("data"+i);
        }
        datas.add("323423");
        datas.add("asdfasdf");
        datas.add("asdfasdfewllwll");
        adapter = new ContasctsAdater(datas);
        recyclerView.setAdapter(adapter);

         delete = findViewById(R.id.delete_button);
         done = findViewById(R.id.done_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIth.attachToRecyclerView(recyclerView);
                ContasctsAdater.isEditing = true;
                done.setVisibility(View.VISIBLE);
                delete.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIth.attachToRecyclerView(null);
                ContasctsAdater.isEditing = false;
                done.setVisibility(View.GONE);
                delete.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                if( ContasctsAdater.isShowingDelete){
                    ContasctsAdater.isShowingDelete = false;
                    ContasctsAdater.showHolder.showSpring.setCurrentValue(1);
                    ContasctsAdater.showHolder.showSpring.setEndValue(0);
                }
            }
        });

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
//                        final int fromPos = viewHolder.getAdapterPosition();
//                        datas.remove(fromPos);
//                        adapter.notifyItemRemoved(fromPos);
                    }

                });



    }
}
