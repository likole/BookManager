package cn.likole.bookmanager.fragment;

import android.widget.TextView;

import cn.likole.bookmanager.R;

/**
 * Created by likole on 3/25/18.
 */

public class StringFragment extends BaseFragment {
    private String mText;
    private TextView mTvText;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_string;
    }

    @Override
    protected void setUpView() {
        mText = getArguments().getString("text");
        mTvText = (TextView) getContentView().findViewById(R.id.tv_text);
        if (!"".equals(mText))
            mTvText.setText(mText);
        else
            mTvText.setText("暂无信息");
    }
}
