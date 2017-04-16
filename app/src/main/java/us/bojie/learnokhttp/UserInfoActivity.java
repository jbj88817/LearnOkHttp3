package us.bojie.learnokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoActivity extends AppCompatActivity {


    private static final String TAG = UserInfoActivity.class.getSimpleName();
    @BindView(R.id.btn_get)
    Button mBtnGet;
    @BindView(R.id.imgview)
    ImageView mImgview;
    @BindView(R.id.tv_username)
    TextView mTvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get)
    public void onViewClicked() {
        getUserInfo();
    }

    private void getUserInfo() {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .get()
                .url("http://192.168.1.14:5000/user/info?id=1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    showUser(result);
                }
            }
        });
    }

    private void showUser(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String id = jsonObject.optString("id");
                    String username = jsonObject.optString("username");
                    String headUrl = jsonObject.optString("head_url");

                    mTvUsername.setText(username);
                    Picasso.with(UserInfoActivity.this)
                            .load(headUrl)
                            .resize(200, 200)
                            .centerCrop()
                            .into(mImgview);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
