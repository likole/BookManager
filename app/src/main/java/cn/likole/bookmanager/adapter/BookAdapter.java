package cn.likole.bookmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.activity.BookDetailActivity;
import cn.likole.bookmanager.bean.BookBean;

import static cn.likole.bookmanager.Constant.basic_url;

/**
 * Created by _SOLID
 * Date:2016/4/5
 * Time:11:34
 */
public class BookAdapter extends RVBaseAdapter<BookBean> {

    public BookAdapter(Context context, List<BookBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_book;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        intent.putExtra("bookId", mBeans.get(position - 1).getBookId());
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(MyCommonViewHolder holder, BookBean bean, int position) {
        holder.setText(R.id.tv_title, bean.getBookTitle());
        holder.setText(R.id.tv_num, "数量：" + (bean.getBookNumber() - bean.getBookBorrow()) + "/" + bean.getBookNumber());
        holder.setText(R.id.tv_author, "作者:" + bean.getBookAuthor() + "");
        holder.setText(R.id.tv_isbn, "ISBN:" + bean.getBookIsbn());
        holder.setText(R.id.tv_bookid, "图书编号:" + bean.getBookId());
        try {
            Log.e("image", bean.getBookImage());
            holder.setImageFromInternet(R.id.iv_image, basic_url + "images/" + bean.getBookImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
