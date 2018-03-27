package cn.likole.bookmanager.activity;


import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.adapter.BookInfoPageAdapter;
import cn.likole.bookmanager.bean.BookBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;


public class BookDetailActivity extends BaseActivity {

    private String mUrl;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mIvBook;

    private BookBean mBookBean;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvRating;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void setUpView() {
        //设置Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mCollapsingToolbarLayout = $(R.id.collapsing_toolbar_layout);
        mIvBook = $(R.id.iv_book_image);
        mTvTitle = $(R.id.tv_title);
        mTvMsg = $(R.id.tv_msg);
        mTvRating = $(R.id.tv_rating);
        mViewPager = $(R.id.viewpager);
        mTabLayout = $(R.id.sliding_tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("作者信息"));
        mTabLayout.addTab(mTabLayout.newTab().setText("书籍简介"));
        mTabLayout.addTab(mTabLayout.newTab().setText("修改"));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#6d4c41"));
//        dynamicAddSkinEnableView(mTabLayout, "tabIndicatorColor", R.color.colorAccent);
//        dynamicAddSkinEnableView(mCollapsingToolbarLayout, "contentScrimColor", R.color.colorPrimary);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void init() {
//        mUrl = getIntent().getStringExtra("bookId");
        mUrl = basic_url + "book/getBook?bookId=" + (getIntent().getIntExtra("bookId", 0));
    }

    @Override
    protected void setUpData() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BookBean result = new Gson().fromJson(response.body().string(), new TypeToken<BookBean>() {
                }.getType());
                mBookBean = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCollapsingToolbarLayout.setTitle(mBookBean.getBookTitle());
                        mTvTitle.setText(mBookBean.getBookTitle());
                        mTvMsg.setText(mBookBean.getBookAuthor() + "/" + mBookBean.getBookIsbn() + "/" + mBookBean.getBookId());
                        mTvRating.setText("总数：" + mBookBean.getBookNumber() + " 剩余：" + (mBookBean.getBookNumber() - mBookBean.getBookBorrow()));

//                ImageRequest imageRequest = new ImageRequest.Builder().imgView(mIvBook).url(result.getImages().getLarge()).create();
//                ImageLoader.getProvider().loadImage(imageRequest);

                        BookInfoPageAdapter adapter = new BookInfoPageAdapter(BookDetailActivity.this, mBookBean, getSupportFragmentManager());
                        mViewPager.setAdapter(adapter);
                        mTabLayout.setupWithViewPager(mViewPager);
                    }
                });
            }


        });
    }
}
