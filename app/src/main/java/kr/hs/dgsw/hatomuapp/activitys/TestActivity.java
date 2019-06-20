package kr.hs.dgsw.hatomuapp.activitys;

import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kr.hs.dgsw.hatomuapp.activitys.MainActivity.TAG;

import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.databinding.ActivityTestBinding;
import kr.hs.dgsw.hatomuapp.network.HatomuService;
import kr.hs.dgsw.hatomuapp.utils.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {
//
//    public static final int PHOTO_PICKER_REQ = 1;
//    private ActivityTestBinding binding;
//    private HatomuService service;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
//
//        //서버와 통신할 Retrofit 설정
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(HatomuService.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        service = retrofit.create(HatomuService.class);
//
//        binding.buttonImageSelect.setOnClickListener(v -> {
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            //Intent i = new Intent(Intent.ACTION_PICK);
//            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            i.setType("image/*");
//            startActivityForResult(i, PHOTO_PICKER_REQ);
//        });
//
//        binding.buttonGotoLogin.setOnClickListener(v -> {
//            Intent i = new Intent(this, LoginActivity.class);
//            startActivity(i);
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PHOTO_PICKER_REQ:
//                    if (data.getData() != null) {
//                        Uri uri = data.getData();
//                        List<MultipartBody.Part> postImages = new ArrayList<>();
//                        //content:// 뭐시기 uri를 절대경로로 바꿈
//                        File file = new File(FileUtil.getPath(this, uri));
//                        //imageType의 RequestBody 생성
//                        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
//                        //MultipartBody.Part.createFormDate 제일 중요
//                        postImages.add(MultipartBody.Part.createFormData("postImage", file.getName(), fbody));
//                        String content = "";
//                        content = binding.edittextContent.getText().toString();
//                        RequestBody cbody = RequestBody.create(MediaType.parse("text/plain"), content);
//                        service.uploadPost(postImages, cbody, "192027813").enqueue(new Callback<ResponseBean<PostBean>>() {
//                            @Override
//                            public void onResponse(Call<ResponseBean<PostBean>> call, Response<ResponseBean<PostBean>> response) {
//                                Log.d(TAG, response.body().toString());
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseBean<PostBean>> call, Throwable t) {
//                                Log.d(TAG, t.getMessage());
//                            }
//                        });
//                        Log.d(TAG, uri.toString());
//                    } else {
//                        ClipData clipData = data.getClipData();
//                        if (clipData != null) {
//                            List<MultipartBody.Part> postImages = new ArrayList<>();
//                            for (int i = 0; i < clipData.getItemCount(); i++) {
//                                ClipData.Item item = clipData.getItemAt(i);
//                                Uri uri = item.getUri();
//                                //content:// 뭐시기 uri를 절대경로로 바꿈
//                                File file = new File(FileUtil.getPath(this, uri));
//                                //imageType의 RequestBody 생성
//                                RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
//                                //MultipartBody.Part.createFormDate 제일 중요
//                                postImages.add(MultipartBody.Part.createFormData("postImage", file.getName(), fbody));
//                            }
//                            String content = "";
//                            content = binding.edittextContent.getText().toString();
//                            RequestBody cbody = RequestBody.create(MediaType.parse("text/plain"), content);
//                            service.uploadPost(postImages, cbody, "192027813").enqueue(new Callback<ResponseBean<PostBean>>() {
//                                @Override
//                                public void onResponse(Call<ResponseBean<PostBean>> call, Response<ResponseBean<PostBean>> response) {
//                                    Log.d(TAG, response.body().toString());
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBean<PostBean>> call, Throwable t) {
//                                    Log.d(TAG, t.getMessage());
//                                }
//                            });
//                        }
//                    }
//            }
//        }
//    }
}
