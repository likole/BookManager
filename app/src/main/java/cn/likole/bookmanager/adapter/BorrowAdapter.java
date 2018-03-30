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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.bean.BorrowBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;

/**
 * Created by likole on 3/30/18.
 */

public class BorrowAdapter extends BaseSwipeAdapter {
    private Context mContext;
    private List<BorrowBean> mDatas;
    private int pos;

    public BorrowAdapter(Context context, List<BorrowBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    public void update(List<BorrowBean> mDatas) {
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
    public void fillValues(int position, View convertView) {

        BorrowBean borrow = mDatas.get(position);
        Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
            tv.setText(borrow.getBook().getBookTitle() + "(id:" + borrow.getBook().getBookId() + ")");
            TextView tv2 = (TextView) convertView.findViewById(R.id.tv_bookid);
            tv2.setText("ISBN:" + borrow.getBook().getBookIsbn());
            TextView tv3 = (TextView) convertView.findViewById(R.id.tv_username);
            tv3.setText("借阅人账号：" + borrow.getUserInfo().getUserUsername());
            TextView tv4 = (TextView) convertView.findViewById(R.id.tv_borrower);
            tv4.setText("借阅人：" + borrow.getUserInfo().getUserName());
            TextView tv5 = (TextView) convertView.findViewById(R.id.tv_time);
            tv5.setText("借阅时间：" + format.format(new Date(borrow.getBorrowInfo().getBorrowTime())));
        } catch (Exception e) {

        }


        final SwipeLayout sl = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        final TextView delete = (TextView) convertView.findViewById(R.id.delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int pos = (Integer) delete.getTag();
                BorrowBean obj = mDatas.get(pos);

                //发送请求
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("borrowId", mDatas.get(pos).getBorrowInfo().getBorrowId() + "");
                Request request = new Request.Builder()
                        .url(basic_url + "borrow/update")
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


                //移除
                Log.e("onClick", "........pos ...." + pos + " obj = " + obj);
                mDatas.remove(obj);
                notifyDataSetChanged();
                sl.close();
            }
        });

    }

    @Override
    public View generateView(int position, ViewGroup arg1) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_borrow_list_item, null);
        pos = position;
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(R.id.borrow_swipe);

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

        return v;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.borrow_swipe;
    }
}
