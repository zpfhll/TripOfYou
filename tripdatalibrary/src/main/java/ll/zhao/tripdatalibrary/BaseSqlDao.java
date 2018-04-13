package ll.zhao.tripdatalibrary;

import java.util.List;
import java.util.Map;

import ll.zhao.tripdatalibrary.model.BaseModel;

/**
 * Created by Administrator on 2018/3/21.
 */

public interface BaseSqlDao {

    boolean insert(List<BaseModel> list);
    boolean insert(BaseModel model);
    List<BaseModel> getAllData();
    List<BaseModel> getData(String[] condition);

}
