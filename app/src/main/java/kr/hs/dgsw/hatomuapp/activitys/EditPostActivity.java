package kr.hs.dgsw.hatomuapp.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import static kr.hs.dgsw.hatomuapp.activitys.MainActivity.TAG;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.adapters.SelectedImageAdapter;
import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.beans.UserBean;
import kr.hs.dgsw.hatomuapp.databinding.ActivityEditPostBinding;
import kr.hs.dgsw.hatomuapp.network.HatomuService;
import kr.hs.dgsw.hatomuapp.network.ServiceControl;
import kr.hs.dgsw.hatomuapp.setting.HatomuTheme;
import kr.hs.dgsw.hatomuapp.utils.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEditPostBinding binding;
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL = 1;
    public static final int PHOTO_PICKER_REQ = 2;

    private HatomuService service;

    private String token;

    private SelectedImageAdapter imageAdapter;
    private UserBean loginUser;

    private List<Uri> uriList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HatomuTheme.applyTheme(this);

        loginUser = UserBean.getInstance();
        token = loginUser.getToken();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_post);
        RequestPermission();

        service = ServiceControl.getHatomuService();

        uriList = new ArrayList<>();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerView adapter
        imageAdapter = new SelectedImageAdapter(this, uriList);

        SnapHelper snapHelper = new LinearSnapHelper();
        binding.listSelectItem.setAdapter(this.imageAdapter);
        binding.listSelectItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //뚝뚝 끊기는 RecyclerView
        snapHelper.attachToRecyclerView(binding.listSelectItem);

        binding.buttonAddImage.setOnClickListener(this);
    }

    private void RequestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_EXTERNAL);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.submit_menu:
                //작성 클릭
                submitPost();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EditPostActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                alert.setMessage("글 작성시 권한이 필요합니다");
                alert.show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_addImage) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            //Intent i = new Intent(Intent.ACTION_PICK);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.setType("image/*");
            startActivityForResult(i, PHOTO_PICKER_REQ);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_PICKER_REQ:
                    if (data.getData() != null) {
                        Uri uri = data.getData();
                        this.imageAdapter.addItem(uri);
                    } else {
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                Uri uri = item.getUri();
                                this.imageAdapter.addItem(uri);
                            }
                        }
                    }
            }
        }
    }

    private void submitPost() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
        List<MultipartBody.Part> postImages = new ArrayList<>();
        Intent result = new Intent();
        Observable.fromIterable(uriList)
                .subscribeOn(Schedulers.newThread())
                .map(uri -> new File(FileUtil.getPath(this, uri)))
                .subscribe(file -> {
                    RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
                    postImages.add(MultipartBody.Part.createFormData("postImage", file.getName(), fbody));
                    String content = binding.editContent.getText().toString();
                    RequestBody cbody = RequestBody.create(MediaType.parse("text/plain"), content);
                    service.uploadPost(postImages, cbody, token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(r -> {
                                result.putExtra("message", r.getMessage());
                                setResult(RESULT_OK, result);
                                Log.d(TAG, "OnNext ");
                                finish();
                            }, t -> {
                                result.putExtra("message", t.getMessage());
                                setResult(RESULT_CANCELED, result);
                                Log.d(TAG, "OnError ");
                                Log.d(TAG, t.getMessage());
                                finish();
                            });
                });
    }
}
