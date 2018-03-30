package cn.likole.bookmanager.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.adapter.BorrowAdapter;
import cn.likole.bookmanager.bean.BorrowBean;
import cn.likole.bookmanager.util.SnackBarUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;

/**
 * Created by likole on 3/30/18.
 */

public class BorrowFragment extends BaseFragment {

    private ListView mListView;
    private BorrowAdapter mAdapter;
    private List<BorrowBean> mDatas;
    private boolean isGetData = false;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mAdapter.update(mDatas);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_borrow_list;
    }

    @Override
    protected void setUpView() {
        mListView = $(R.id.borrow_list);
        mDatas = new ArrayList<>();
        mAdapter = new BorrowAdapter(getActivity(), mDatas);
//        mAdapter.setMode(Attributes.Mode.Single);
        mListView.setAdapter(mAdapter);
    }


    @Override
    protected void setUpData() {
//        update();
    }

    private void update() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(basic_url + "borrow/borrowList")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SnackBarUtils.makeLong(mListView, "网络错误，请检查网络后重试").danger();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    mDatas = new Gson().fromJson(response.body().string(), new TypeToken<List<BorrowBean>>() {
                    }.getType());
//                    Log.e("info",mDatas.toString());
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter && !isGetData) {
            isGetData = true;
            update();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            update();
            isGetData = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

}
