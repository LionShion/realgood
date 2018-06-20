package com.wenjing.yinfutong.common;

import android.Manifest;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.joker.api.Permissions4M;
import com.joker.api.wrapper.ListenerWrapper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.utils.TLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by ${luoyingtao} on 2018\3\22 0022.
 */

public class PhotoHelper {
    public static final String TAG = "PhotoHelper";

    public static final int REQUESTCODE_PERMISSION_CAMERA = 1001;

    //请求吗=码
    //拍照
    public static final int REQUESTCODE_CAMERA = 100;
    //相册
    public static final int REQUESTCODE_ALBUM = 101;
    //裁剪
    public static final int REQUESTCODE_ZOOM = 102;

    //临时文件路径
    public static final String DEFAULT_TEMPPATH = getSDCard() + File.separator + "tempPhoto.jpg";
//    public static final String DEFAULT_UPLOAD = getSDCard() + File.separator + "taozi.jpg";


    public static String getSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//有
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 默认使用 方式
     *
     * @param fragment
     */
    public static void getPhoto(BaseFragment fragment) {
        deleteDir();//清除  临时路径
        getPhoto(fragment, DEFAULT_TEMPPATH);
    }

    /**
     * @param fragment
     * @param path
     */
    public static void getPhoto(final BaseFragment fragment, final String path) {
        final QMUIBottomSheet qmuiBottomSheet = new QMUIBottomSheet.BottomListSheetBuilder(fragment.getContext(), true)
                .addItem(fragment.getResources().getString(R.string.album), "1")
                .addItem(fragment.getResources().getString(R.string.take_photo), "2")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        if (position == 0) {
                            dialog.dismiss();
                            getPhotoFromAlbum(fragment);
                        } else if (position == 1) {
                            dialog.dismiss();
                            Permissions4M.get(fragment.getCurActivity())
                                    .requestForce(true)
                                    .requestPermission(Manifest.permission.CAMERA)
                                    .requestCallback(new ListenerWrapper.PermissionRequestListener() {
                                        @Override
                                        public void permissionGranted() {
                                            getPhotoFromCamera(fragment, path);
                                        }

                                        @Override
                                        public void permissionDenied() {

                                        }

                                        @Override
                                        public void permissionRationale() {

                                        }
                                    })
                                    .requestCode(REQUESTCODE_PERMISSION_CAMERA)
                                    .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                                    .requestPage(new ListenerWrapper.PermissionPageListener() {
                                        @Override
                                        public void pageIntent(Intent intent) {

                                        }
                                    })
                                    .request();
                        }
                    }
                })
                .build();
        qmuiBottomSheet.show();
    }

    /**
     * 拍照获取图片
     *
     * @param fragment 上下文
     * @param tempPath 拍出的照片  临时文件路径
     */
    public static void getPhotoFromCamera(Fragment fragment, String tempPath) {
        Intent getImgByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        getImgByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(tempPath)));
        fragment.startActivityForResult(getImgByCamera, REQUESTCODE_CAMERA);
    }

    /**
     * 相册获取图片
     *
     * @param fragment 上下文
     */
    public static void getPhotoFromAlbum(Fragment fragment) {
        Intent getImgFromAlbum = new Intent(Intent.ACTION_PICK,null);
        //此处调用了图片选择器
        //如果直接写intent.setDataAndType("image/*");调用的是系统图库
        getImgFromAlbum.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//        getImgFromAlbum.addCategory(Intent.CATEGORY_OPENABLE);
//        getImgFromAlbum.putExtra("return-data", true);
//        getImgFromAlbum.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(tempPath)));
        fragment.startActivityForResult(getImgFromAlbum, REQUESTCODE_ALBUM);
    }

    /**
     * 默认使用
     *
     * @param fragment
     * @param uri
     * @param aspectX
     * @param aspectY
     */
    public static void getPhotoZoom(Fragment fragment, Uri uri,
                                    int aspectX, int aspectY) {
        getPhotoZoom(fragment, uri, DEFAULT_TEMPPATH, aspectX, aspectY);
    }

    /**
     * @param fragment 上下文
     * @param uri      需要裁剪图片的  uri
     * @param path     裁剪后的图片  保存的路径
     * @param aspectX  宽高比
     * @param aspectY
     */
    public static void getPhotoZoom(Fragment fragment, Uri uri, String path,
                                    int aspectX, int aspectY) {
        Log.i(TAG,uri.toString());

        Intent zoomIntent = new Intent("com.android.camera.action.CROP");
        zoomIntent.setDataAndType(uri, "image/*");
        zoomIntent.putExtra("crop", "true");
        //aspectX   aspectY   是宽高比例
        if (aspectY > 0) {
            zoomIntent.putExtra("aspectX", aspectX);
            zoomIntent.putExtra("aspectY", aspectY);
        }

        zoomIntent.putExtra("scale", aspectX == aspectY);
        zoomIntent.putExtra("return-data", true);
        zoomIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        zoomIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(path)));
        zoomIntent.putExtra("noFaceDetection", true);
        //输出图片大小
        zoomIntent.putExtra("outputX", 300);
        zoomIntent.putExtra("outputY", 300);

        fragment.startActivityForResult(zoomIntent, REQUESTCODE_ZOOM);
    }

    /**
     * 将   SD 卡 图片  转为Bitmap  对象
     *
     * @param path 图片路径
     * @param w    转为Bitmap  后的图片 的宽
     * @param h    转为Bitmap  后的图片 的高
     * @return
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //先只录入 图片  大小信息
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        //返回为空
        BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;

        float scaleWidth = 0.f;
        float scaleHeight = 0.f;
        if (width > w || height > h) {
            //缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }

        options.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        options.inSampleSize = (int) scale;
        WeakReference<Bitmap> weakReference = new WeakReference<Bitmap>(
                BitmapFactory.decodeFile(path, options));
        return Bitmap.createScaledBitmap(weakReference.get(), w, h, true);
    }

    /**
     * 删除临时文件
     */
    public static void deleteDir(){
        File file = new File(DEFAULT_TEMPPATH);
        if(file.exists()){
            file.delete();
        }
    }


    public static String getRealPathFromURI(Context context , Uri contentUri) { //传入图片uri地址
        String uriStr = contentUri.toString();
        Log.i(TAG,"Uri 路径:"+uriStr);
        if(!uriStr.contains("content://")){//   以 file://开头
            return uriStr;
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        Log.i(TAG,"转换后的真实地址: "+cursor.getString(column_index));
        return cursor.getString(column_index);
    }

    public static byte[] getImgFileByte(){
        Bitmap bitmap = BitmapFactory.decodeFile(DEFAULT_TEMPPATH);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , baos);
        int length = baos.toByteArray().length;
        TLog.log(TAG , "length : " + length + " , 即" + length * 1.0f / 1024 / 1024 + "MB");
        return baos.toByteArray();
    }
}
