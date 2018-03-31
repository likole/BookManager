package cn.likole.bookmanager.fragment;

import android.os.Handler;
import android.os.Message;
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
 * Created by likole on 3/27/18.
 */

public class BookAddFragment extends BaseFragment {
    //int bookId<br />String bookIsbn<br />int bookNumber<br /> int bookBorrow<br />String bookImage<br /> String bookTitle<br />String bookIntro<br />String bookAuthor<br />String authorIntro

    private EditText et_book_isbn;
    private EditText et_book_number;
    private EditText et_book_title;
    private EditText et_book_intro;
    private EditText et_book_auther;
    private EditText et_book_autherInfo;
    private Button btn_edit_book;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "网络错误,请检查网络", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_LONG).show();
                    resetView();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_book_edit;
    }


    private void resetView() {
        et_book_isbn.setText("");
        et_book_number.setText("");
        et_book_title.setText("");
        et_book_intro.setText("");
        et_book_auther.setText("");
        et_book_autherInfo.setText("");
    }

    @Override
    protected void setUpView() {
        et_book_isbn = (EditText) getContentView().findViewById(R.id.et_book_isbn);
        et_book_number = (EditText) getContentView().findViewById(R.id.et_book_number);
        et_book_title = (EditText) getContentView().findViewById(R.id.et_book_title);
        et_book_intro = (EditText) getContentView().findViewById(R.id.et_book_intro);
        et_book_auther = (EditText) getContentView().findViewById(R.id.et_book_auther);
        et_book_autherInfo = (EditText) getContentView().findViewById(R.id.et_book_autherInfo);
        btn_edit_book = (Button) getContentView().findViewById(R.id.btn_edit_book);


        btn_edit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            //int bookId<br />String bookIsbn<br />int bookNumber<br /> int bookBorrow<br />String bookImage<br /> String bookTitle<br />String bookIntro<br />String bookAuthor<br />String authorIntro
            public void onClick(View view) {

                String isbn = et_book_isbn.getText().toString();
                String num = et_book_number.getText().toString();
                String title = et_book_title.getText().toString();

                if (isbn.length() <= 0 || num.length() <= 0 || title.length() <= 0) {
                    Toast.makeText(getActivity(), "信息不足", Toast.LENGTH_LONG).show();
                    return;
                }

                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("bookIsbn", isbn);
                formBody.add("bookNumber", num);
                formBody.add("bookBorrow", "0");
                formBody.add("bookTitle", title);
                formBody.add("bookIntro", et_book_intro.getText().toString());
                formBody.add("bookAuthor", et_book_auther.getText().toString());
                formBody.add("authorIntro", et_book_autherInfo.getText().toString());
                Request request = new Request.Builder()
                        .url(basic_url + "book/add")
                        .post(formBody.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message msg = new Message();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                });
            }
        });

    }
}
