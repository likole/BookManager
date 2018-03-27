package cn.likole.bookmanager.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import cn.likole.bookmanager.R;
import cn.likole.bookmanager.activity.BookDetailActivity;
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

public class BookEditFragment extends BaseFragment {

    //int bookId<br />String bookIsbn<br />int bookNumber<br /> int bookBorrow<br />String bookImage<br /> String bookTitle<br />String bookIntro<br />String bookAuthor<br />String authorIntro

    private EditText et_book_isbn;
    private EditText et_book_number;
    private EditText et_book_title;
    private EditText et_book_intro;
    private EditText et_book_auther;
    private EditText et_book_autherInfo;
    private Button btn_edit_book;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_book_edit;
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

        et_book_isbn.setText(getArguments().getString("bookIsbn"));
        et_book_number.setText(String.valueOf(getArguments().getInt("bookNumber")));
        et_book_title.setText(getArguments().getString("bookTitle"));
        et_book_intro.setText(getArguments().getString("bookIntro"));
        et_book_auther.setText(getArguments().getString("bookAuthor"));
        et_book_autherInfo.setText(getArguments().getString("authorIntro"));

        btn_edit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            //int bookId<br />String bookIsbn<br />int bookNumber<br /> int bookBorrow<br />String bookImage<br /> String bookTitle<br />String bookIntro<br />String bookAuthor<br />String authorIntro
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("bookId", String.valueOf(getArguments().getInt("bookId")));
                formBody.add("bookIsbn", et_book_isbn.getText().toString());
                formBody.add("bookNumber", et_book_number.getText().toString());
                formBody.add("bookBorrow", String.valueOf(getArguments().getInt("bookBorrow")));
                formBody.add("bookTitle", et_book_title.getText().toString());
                formBody.add("bookIntro", et_book_intro.getText().toString());
                formBody.add("bookAuthor", et_book_auther.getText().toString());
                formBody.add("authorIntro", et_book_autherInfo.getText().toString());
                Request request = new Request.Builder()
                        .url(basic_url + "book/edit")
                        .post(formBody.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                        intent.putExtra("bookId", getArguments().getInt("bookId"));
                        getActivity().startActivity(intent);
                    }
                });
            }
        });

    }
}
