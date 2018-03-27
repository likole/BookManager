package cn.likole.bookmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.activity.UserAddActivity;
import cn.likole.bookmanager.adapter.UserAdapter;
import cn.likole.bookmanager.bean.UserBean;
import cn.likole.bookmanager.util.SnackBarUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;

public class UserFragment extends BaseFragment {

    private ListView mListView;
    private Button btn;
    ArrayList<UserBean> list;
    UserAdapter userAdapter;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    userAdapter.update(list);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void setUpView() {
        mListView = $(R.id.user_list);
        btn = $(R.id.btn_user_addActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserAddActivity.class));
            }
        });
    }

    @Override
    protected void setUpData() {
        /**
         * The following comment is the sample usage of ArraySwipeAdapter.
         */
//        UserBean userbean = new UserBean();
//        userbean.setUserId(1000);
//        userbean.setUserUsername("测试用户");
//        userbean.setUserName("正在加载中，请稍候");
//        list.add(userbean);

        list = new ArrayList<>();

        userAdapter = new UserAdapter(getActivity(), list);
        mListView.setAdapter(userAdapter);
        update();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout) (mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SnackBarUtils.makeShort(mListView, "滑动删除用户，暂不支持修改，可先删除再添加").success();
//                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_user_list;
    }

    public void update() {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        Request request = new Request.Builder()
                .url(basic_url + "user/userList")
                .post(formBody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SnackBarUtils.makeLong(mListView, "网络错误，请检查网络后重试").danger();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    list = new Gson().fromJson(response.body().string(), new TypeToken<List<UserBean>>() {
                    }.getType());
//                    Log.e("info",list.toString());
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    //应该不会发生
                }

            }
        });
    }

}
