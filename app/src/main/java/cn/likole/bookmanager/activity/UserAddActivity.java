package cn.likole.bookmanager.activity;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class UserAddActivity extends BaseActivity {
    Toolbar mToolbar;
    private EditText et_username;
    private EditText et_name;
    private EditText et_password;
    private CheckBox cb_admin;
    private Button btn;
    private int admin = 0;

    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar2);
        et_username = $(R.id.user_add_username);
        et_name = $(R.id.user_add_name);
        et_password = $(R.id.user_add_password);
        cb_admin = $(R.id.user_add_admin);
        btn = $(R.id.btn_user_add);
        cb_admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                admin = b ? 1 : 0;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (username.length() <= 0 || password.length() < 6) {
                    Toast.makeText(UserAddActivity.this, "用户名或密码过短", Toast.LENGTH_SHORT).show();
                    return;
                }

                //tring username<br />String password<br />String name<br />int power
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("username", username);
                formBody.add("password", password);
                formBody.add("name", et_name.getText().toString());
                formBody.add("power", String.valueOf(admin));
                Request request = new Request.Builder()
                        .url(basic_url + "user/add")
                        .post(formBody.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(UserAddActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Toast.makeText(UserAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void setUpData() {
        mToolbar.setTitle("添加用户");
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_user_add;
    }
}
