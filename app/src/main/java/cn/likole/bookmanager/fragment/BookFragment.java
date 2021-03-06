package cn.likole.bookmanager.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.adapter.BookAdapter;
import cn.likole.bookmanager.bean.BookBean;
import cn.likole.bookmanager.util.SnackBarUtils;
import ren.solid.library.http.HttpClientManager;
import ren.solid.library.http.callback.adapter.JsonHttpCallBack;

import static cn.likole.bookmanager.Constant.basic_url;

public class BookFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "BookFragmentTAG";
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;


    private XRecyclerView mRecyclerView;
    private BookAdapter mBookAdapter;
    private FloatingActionButton mFABSearch;
    private EditText mETInput;
    private AlertDialog mInputDialog;

    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 20;
    private int mCurrentPageIndex = 1;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 5:
                    loadComplete();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_book;
    }

    @Override
    protected void setUpView() {
        mFABSearch = $(R.id.fab_search);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = $(R.id.recyclerview);
        mBookAdapter = new BookAdapter(getMContext(), new ArrayList<BookBean>());
        mRecyclerView.setAdapter(mBookAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mFABSearch.setOnClickListener(this);
        initInputDialog();
    }

    @Override
    protected void setUpData() {
//        String[] keyWords = {"Android", "文艺青年", "科技", ".NET", "创业之路"};
//        Random random = new Random();
//        int n = random.nextInt(keyWords.length);
        mCurrentKeyWord = "";
        //switchAction(ACTION_REFRESH);
        mRecyclerView.setRefreshing(true);

    }

    private void getData() {
//        String reqUrl = "https://api.douban.com/v2/book/search" + "?q=" + mCurrentKeyWord + "&start=" + (mCurrentPageIndex - 1) * mPageSize +
//                "&count=" + mPageSize;

        if (mCurrentKeyWord.length() == 0)
        HttpClientManager.getData(basic_url + "book/entireList?q=" + mCurrentKeyWord + "&offset=" + (mCurrentPageIndex - 1) * mPageSize + "&num=" + mPageSize, new JsonHttpCallBack<List<BookBean>>() {
            @Override
            public Type getType() {
                return new TypeToken<List<BookBean>>() {
                }.getType();
            }

            @Override
            public void onSuccess(List<BookBean> result) {
                mBookAdapter.addAll(result);
                loadComplete();
            }

            @Override
            public JsonHttpCallBack.DataType getDataType() {
                return DataType.OBJECT;
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError:" + e);
                SnackBarUtils.makeLong(mRecyclerView, "网络错误").danger();
                loadComplete();
            }
        });
        else {
            HttpClientManager.postData(basic_url + "book/partList?bookTitle=" + mCurrentKeyWord + "&offset=" + (mCurrentPageIndex - 1) * mPageSize + "&num=" + mPageSize, new JsonHttpCallBack<List<BookBean>>() {
                @Override
                public Type getType() {
                    return new TypeToken<List<BookBean>>() {
                    }.getType();
                }

                @Override
                public void onSuccess(List<BookBean> result) {
                    mBookAdapter.addAll(result);
                    loadComplete();
                }

                @Override
                public JsonHttpCallBack.DataType getDataType() {
                    return DataType.OBJECT;
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "onError:" + e);
                    SnackBarUtils.makeLong(mRecyclerView, "网络错误").danger();
                    loadComplete();
                }
            });
        }
    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                mBookAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_search:
                mInputDialog.show();
                break;
            default:
                break;
        }
    }

    /***
     * 初始化输入对框框
     */
    private void initInputDialog() {
        mETInput = new EditText(getMContext());
        mETInput.setTextColor(Color.parseColor("#292929"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getMContext());
        builder.setTitle("请输入关键字");


        builder.setView(mETInput);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mCurrentKeyWord = mETInput.getText().toString();
                if ("".equals(mCurrentKeyWord)) {//如果用户输入的关键字为空，我们就按照最开始的数据加载方式加载
                    setUpData();
                } else {
                    switchAction(ACTION_REFRESH);
                }
                mETInput.setText("");

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mETInput.setText("");
            }
        });
        mInputDialog = builder.create();

    }

}
