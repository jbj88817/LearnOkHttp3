package us.bojie.learnokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import us.bojie.learnokhttp.model.BaseResult;
import us.bojie.learnokhttp.okhttp.BaseCallBack;
import us.bojie.learnokhttp.okhttp.SimpleHttpClient;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mOkHttpClient = new OkHttpClient();

//        SampleHttpClient.newBuilder().url("https://bojie.us").get().build().enqueue(new BaseCallBack<User>() {
//            @Override
//            public void onSuccess(User user) {
//
//            }
//
//            @Override
//            public void onError(int code) {
//
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//
//            }
//        });

//        String url = Config.API.BASE_URL + "login";

//        SimpleHttpClient.newBuilder()
//                .url(url)
//                .post()
//                .addParam("username", "bojie")
//                .addParam("password", "123456")
//                .build()
//                .enqueue(new BaseCallBack<BaseResult>() {
//                    @Override
//                    public void onSuccess(BaseResult baseResult) {
//                        if (baseResult.getSuccess() == 1) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(int code) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//                });
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String userName = mEtUsername.getText().toString().trim();
        String passWord = mEtPassword.getText().toString().trim();

        loginWithForm(userName, passWord);

//        loginWithJSON(userName, passWord);
    }

    private void loginWithJSON(String userName, String passWord) {

        String url = Config.API.BASE_URL + "login/json";

        SimpleHttpClient.newBuilder()
                .addParam("username", userName)
                .addParam("password", passWord)
                .json()
                .url(url)
                .build().enqueue(new BaseCallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult baseResult) {
                Toast.makeText(LoginActivity.this, baseResult.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject.put("username", userName);
//            jsonObject.put("password", passWord);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String jsonParams = jsonObject.toString();
//        Log.d(TAG, "loginWithJSON: " + jsonParams);
//
//        RequestBody body = RequestBody
//                .create(MediaType.parse("application/json;charset=utf-8"), jsonParams);
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String json = response.body().string();
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(json);
//
//                        String message = jsonObject.optString("message");
//                        final int success = jsonObject.optInt("success");
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (success == 1) {
//                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }


    private void loginWithForm(String userName, String passWord) {

        String url = Config.API.BASE_URL + "login";

        SimpleHttpClient.newBuilder()
                .addParam("username", userName)
                .addParam("password", passWord)
                .post()
                .url(url)
                .build().enqueue(new BaseCallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult baseResult) {
                Toast.makeText(LoginActivity.this, baseResult.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });


//        RequestBody body = new FormBody.Builder()
//                .add("username", userName)
//                .add("password", passWord)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String json = response.body().string();
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(json);
//
//                        String message = jsonObject.optString("message");
//                        final int success = jsonObject.optInt("success");
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (success == 1) {
//                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }
}
