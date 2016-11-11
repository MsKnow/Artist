package com.example.know.artist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.know.artist.base.ToolbarActivity;
import com.example.know.model.User;
import com.example.know.retrofit.ServiceFactory;
import com.example.know.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/3/6.
 */
public class UploadActivity extends ToolbarActivity implements View.OnClickListener{

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PICK_PHOTO = 3;

    private Uri imageUri;

    @Bind(R.id.im_upload)ImageView imageViewTU;

    @Bind(R.id.bt_img_take)Button buttonImgTake;
    @Bind(R.id.bt_img_pick)Button buttonImgPick;
    @Bind(R.id.bt_img_upload)Button buttonImgUp;


    File outputImage;

    int userId = 233;
    int selfId ;

    @Override
    protected int getContentId() {
        return R.layout.activity_upload;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        selfId = intent.getIntExtra("selfId",-1);/////////
        userId = intent.getIntExtra("userId",-1);

        buttonImgTake.setOnClickListener(this);
        buttonImgPick.setOnClickListener(this);
        buttonImgUp.setOnClickListener(this);

        outputImage = new File(Environment
                .getExternalStorageDirectory(),"out_image.jpg");
        imageUri = Uri.fromFile(outputImage);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_img_take:

                if (outputImage.exists()){
                    outputImage.delete();
                }
                try {
                    Boolean bool = outputImage.createNewFile();
                    Log.e("create new File",bool+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);

                break;

            case R.id.bt_img_pick:

               /* if (outputImage.exists()){
                    outputImage.delete();
                }
                try {
                    Boolean bool = outputImage.createNewFile();
                    Log.e("create new File2222", bool + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //imageUri = Uri.fromFile(outputImage);
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                intent1.putExtra("crop", true);
                intent1.putExtra("scale", true);

                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                //intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent1,PICK_PHOTO);
                break;

            case R.id.bt_img_upload:

                //System.out.println("start post");

                buttonImgUp.setEnabled(false);
                buttonImgUp.setText("上传中");
                //File file = new File(imageUri.getPath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), outputImage);

                if (selfId==0){
                    Log.e("上传",userId+" avatar");

                    ServiceFactory.getService().postAvatar(requestBody,userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {

                                User me = result.getUser();
                                String mes = me.toString();
                                Log.e("html", mes);
                                if (result.getResultCode()>0){
                                    Intent intent2 = new Intent();
                                    intent2.putExtra("me",me);
                                    setResult(RESULT_OK, intent2);
                                    finish();
                                }
                                ToastUtil.tShort(result.getResultDes());

                                buttonImgUp.setEnabled(true);

                            }, throwable -> {
                                buttonImgUp.setEnabled(true);
                                buttonImgUp.setText("上传");
                                throwable.printStackTrace();
                                ToastUtil.tShort("错误，请重试");
                            });
                }else {Log.e("上传",userId+" selfie");
                    Call<ResponseBody> call = ServiceFactory.getService().postImage(requestBody,userId,selfId);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                Log.e("upload image", response.body().string());
                                //outputImage.delete();
                                buttonImgUp.setEnabled(true);
                                buttonImgUp.setText("上传");
                                ToastUtil.tShort("上传成功！！");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            buttonImgUp.setEnabled(true);
                        }
                    });
                }


                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){

                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                    startActivityForResult(intent,CROP_PHOTO);//开启裁剪

                }
                break;

            case PICK_PHOTO:
                if (resultCode == RESULT_OK){


                    Log.e("准备裁剪","he1" +outputImage.getPath()+" \n"+imageUri.getPath());

                    Uri pickedUri = data.getData();

                    //imageUri= data.getData();//不能转为流 也不能上传。。。。。。
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(pickedUri,"image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                    startActivityForResult(intent,CROP_PHOTO);//开启裁剪

                }
                break;

            case CROP_PHOTO:
                Log.e("hehe","he1");
                if (resultCode == RESULT_OK){
                    Log.e("hehe","he1");


                    Log.e("hehe","he1");
                    Bitmap bitmap = null;
                    Log.e("准备显示","he1" +outputImage.getPath()+" \n"+imageUri.getPath());
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //Bitmap bitmap = BitmapFactory.decodeFile(outputImage.getPath());

                    imageViewTU.setImageBitmap(bitmap);

                    if (bitmap!=null){

                        buttonImgUp.setEnabled(true);

                        Log.e("hehe","he1");
                        try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputImage));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);

                            bos.flush();bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }



                }
                break;
            default:break;

        }
    }


}
