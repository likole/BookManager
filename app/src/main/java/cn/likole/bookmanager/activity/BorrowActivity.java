package cn.likole.bookmanager.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import cn.likole.bookmanager.R;
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

public class BorrowActivity extends BaseActivity {

    Toolbar mToolbar;
    Button btn_borrow;
    EditText et_user;
    EditText et_book;


    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar2);
        mToolbar.setTitle("借阅书籍");

        et_user = $(R.id.et_borrow_userId);
        et_book = $(R.id.et_borrow_bookId);
        btn_borrow = $(R.id.btn_borrow);
        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = et_user.getText().toString();
                String bookId = et_book.getText().toString();

                if (userId.length() < 4 || bookId.length() < 4) {
                    Toast.makeText(BorrowActivity.this, "信息不全", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("borrowUserId", userId);
                formBody.add("borrowBookId", bookId);
                Request request = new Request.Builder()
                        .url(basic_url + "borrow/add")
                        .post(formBody.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(BorrowActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Toast.makeText(BorrowActivity.this, "借阅成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.acticity_borrow;
    }
}
