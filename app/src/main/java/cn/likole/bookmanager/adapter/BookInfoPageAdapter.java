package cn.likole.bookmanager.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.likole.bookmanager.bean.BookBean;
import cn.likole.bookmanager.fragment.StringFragment;
import cn.likole.bookmanager.util.ViewUtils;


/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:21:51
 */
public class BookInfoPageAdapter extends FragmentPagerAdapter {

    private final List<String> mTitleList;
    private final Context mContext;
    private BookBean mBookBean;

    public BookInfoPageAdapter(Context context, BookBean bookBean, FragmentManager fm) {
        super(fm);
        mContext = context;
        mBookBean = bookBean;
        mTitleList = new ArrayList<>();
        mTitleList.add("作者信息");
        mTitleList.add("书籍简介");
        mTitleList.add("修改");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(StringFragment.class, false);
        Bundle bundle = new Bundle();
        if (getPageTitle(position).equals("作者信息")) {
            bundle.putString("text", mBookBean.getAuthorIntro());
        } else if (getPageTitle(position).equals("修改")) {
            bundle.putString("text", "修改界面");
        } else if (getPageTitle(position).equals("书籍简介")) {
            bundle.putString("text", mBookBean.getBookIntro());
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
