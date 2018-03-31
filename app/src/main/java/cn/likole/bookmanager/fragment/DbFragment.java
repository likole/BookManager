package cn.likole.bookmanager.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import cn.likole.bookmanager.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;

/**
 * Created by likole on 3/31/18.
 */

public class DbFragment extends BaseFragment {

    Button btn_in;
    Button btn_out;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_db;
    }

    @Override
    protected void setUpView() {
        btn_in = $(R.id.button_in);
        btn_out = $(R.id.button_out);
        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(basic_url + "data/in")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                Toast.makeText(getActivity(), "数据导入成功", Toast.LENGTH_LONG).show();
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(basic_url + "data/out")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                Toast.makeText(getActivity(), "数据导出成功", Toast.LENGTH_LONG).show();
            }
        });
    }
}
