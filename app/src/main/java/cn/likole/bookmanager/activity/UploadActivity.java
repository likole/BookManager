package cn.likole.bookmanager.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.likole.bookmanager.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.likole.bookmanager.Constant.basic_url;

/**
 * Created by likole on 3/31/18.
 */

public class UploadActivity extends TakePhotoActivity {


    ImageView imageView;
    Button btn_upload;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageView = findViewById(R.id.imageView);
        btn_upload = findViewById(R.id.button);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    upImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        //file
        File file = new File(Environment.getExternalStorageDirectory(),
                "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        //take photo
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        getTakePhoto().onEnableCompress(compressConfig, true);
        getTakePhoto().onPickFromCapture(imageUri);
    }

    @Override
    public void takeSuccess(TResult result) {
        path = result.getImage().getOriginalPath();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(path))));
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(UploadActivity.this, "Error:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {

    }

    private void upImage() {
        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(path);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpg"), file))
                .addFormDataPart("bookId", getIntent().getIntExtra("bookId", 0) + "");

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(basic_url + "file/image")
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadActivity.this, "上传成功～", Toast.LENGTH_LONG);
                        finish();
                    }
                });
            }
        });

    }

}
