package cn.likole.bookmanager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.adapter.UserAdapter;
import cn.likole.bookmanager.bean.UserBean;
import cn.likole.bookmanager.util.SnackBarUtils;

public class UserFragment extends BaseFragment {

    private ListView mListView;

    @Override
    protected void setUpView() {
        mListView = $(R.id.user_list);
    }

    @Override
    protected void setUpData() {
        /**
         * The following comment is the sample usage of ArraySwipeAdapter.
         */
        ArrayList<UserBean> list = new ArrayList<>();
        UserBean userbean = new UserBean();
        userbean.setUserId(1000);
        userbean.setUserUsername("测试用户");
        userbean.setUserName("正在加载中，请稍候");
        list.add(userbean);

        mListView.setAdapter(new UserAdapter(getActivity(), list));

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
}
