package us.bojie.learnokhttp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FileDownloadActivity extends AppCompatActivity {

    public static final String URL = "https://download.apkpure.com/b/apk/Y29tLm5ldGVhc2UuY2xvdWRtdXNpY185Ml9jYjU5NjRiYg?_fn=572R5piT5LqR6Z%2Bz5LmQX3Y0LjAuMV9hcGtwdXJlLmNvbS5hcGs%3D&k=ece099e5481712abe7f09f0033aa192458f660b2&as=641fe1939afcb92cf94bccee72cfc30358f3be2a&_p=Y29tLm5ldGVhc2UuY2xvdWRtdXNpYw%3D%3D&c=1%7CMUSIC_AND_AUDIO";
    public static final String FILENAME = "net_music.apk";
    private static final String TAG = FileDownloadActivity.class.getSimpleName();

    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        ButterKnife.bind(this);

        requestPermission();
        initOkhttp();
    }

    private void initOkhttp() {
        mOkHttpClient = new OkHttpClient();
    }

    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;

    public void requestPermission() {
        // if current activity already grand the permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Pls give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                // request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: " + "permission failed");
                }
            }
        }
    }

    @OnClick(R.id.btn_download)
    public void onViewClicked() {

        downloadAPK();
    }

    private void downloadAPK() {

        Request request = new Request.Builder()
                .url(URL)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                writeFile(response);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int progress = msg.arg1;
//                Log.d(TAG, "handleMessage: " + progress);
                mProgressBar.setProgress(progress);
            }
        }
    };


    private void writeFile(Response response) {
        InputStream is = null;
        FileOutputStream fos = null;

        is = response.body().byteStream();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d(TAG, "writeFile: " + path);
        
        File file = new File(path, FILENAME);

        try {
            fos = new FileOutputStream(file);

            byte[] bytes = new byte[1024];
            int len;

            long totalSize = response.body().contentLength();
            long sum = 0;
            while ((len = is.read(bytes)) != -1) {

                fos.write(bytes);

                // Update progress bar using handler
                sum += len;
                int progress = (int) ((sum * 1.0f / totalSize) * 100);
//                Log.d(TAG, "writeFile: " + progress);
                Message msg = mHandler.obtainMessage(1);
                msg.arg1 = progress;

                mHandler.sendMessage(msg);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

