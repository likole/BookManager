package cn.likole.bookmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.bean.UserBean;

/**
 * Created by likole on 3/23/18.
 */

public class UserAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private List<UserBean> mDatas;
    //private TextView mDelete;
    //private SwipeLayout swipeLayout;
    private int pos;

    public UserAdapter(Context context, List<UserBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void fillValues(int position, View convertView) {
        Log.e("fillValues", "position = " + position);
        TextView tv = (TextView) convertView.findViewById(R.id.user_username);
        //tv.setText((position + 1) + ".");
        tv.setText(mDatas.get(position).getUserUsername() + "(" + mDatas.get(position).getUserId() + ")");
        TextView tv2 = (TextView) convertView.findViewById(R.id.user_name);
        tv.setText(mDatas.get(position).getUserName());

        final SwipeLayout sl = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        final TextView delete = (TextView) convertView.findViewById(R.id.delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int pos = (Integer) delete.getTag();
                UserBean obj = mDatas.get(pos);

                Log.e("onClick", "........pos ...." + pos + " obj = " + obj);
                mDatas.remove(obj);
                notifyDataSetChanged();
                sl.close();
            }
        });

    }

    @Override
    public View generateView(int position, ViewGroup arg1) {
        Log.e("generateView", "position = " + position);
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_user_list_item, null);
        pos = position;
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(R.id.user_swipe);

        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {//当隐藏的删除menu被打开的时候的回调函数
            }
        });

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout,
                                      boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();

            }
        });
//      v.findViewById(R.id.delete).setOnClickListener(
//              new View.OnClickListener() {
//                  @Override
//                  public void onClick(View view) {
//                      Toast.makeText(mContext, "click delete position = "+pos,Toast.LENGTH_SHORT).show();
//                      swipeLayout.close();
//                  }
//              });

        return v;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.user_swipe;
    }

}