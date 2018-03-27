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

import java.io.IOException;
import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.bean.UserBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;

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

    public void update(List<UserBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
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
    public void fillValues(final int position, View convertView) {
        Log.e("fillValues", "position = " + position);
        TextView tv = (TextView) convertView.findViewById(R.id.user_username);
        //tv.setText((position + 1) + ".");
        tv.setText("账号：" + mDatas.get(position).getUserUsername() + "(用户编号：" + mDatas.get(position).getUserId() + ")");
        TextView tv2 = (TextView) convertView.findViewById(R.id.user_name);
        tv2.setText("用户名：" + mDatas.get(position).getUserName());
        TextView tv3 = (TextView) convertView.findViewById(R.id.user_password);
        tv3.setText("密码：" + mDatas.get(position).getUserPassword());

        final SwipeLayout sl = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        final TextView delete = (TextView) convertView.findViewById(R.id.delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //发送请求
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("userId", mDatas.get(position).getUserId() + "");
                Request request = new Request.Builder()
                        .url(basic_url + "user/delete")
                        .post(formBody.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });

                //删除元素
                int pos = (Integer) delete.getTag();
                UserBean obj = mDatas.get(pos);

                Log.e("onClick", "........pos ...." + pos + " obj = " + obj);
                mDatas.remove(obj);

                //通知更改
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