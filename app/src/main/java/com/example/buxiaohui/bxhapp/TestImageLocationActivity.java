package com.example.buxiaohui.bxhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import bnav.baidu.com.sublog.LogUtil;

public class TestImageLocationActivity extends AppCompatActivity {
    public static final int REQUEST_PICK_IMAGE = 11101;
    private static final String TAG = "TestImageLocationActivity";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    TextView mDescTv;
    private String mImgPath;

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回图片经纬度信息
     *
     * @param imagePath 文件路径
     *
     * @return 返回值LatLng
     */
    public static String[] getPhotoLocation(String imagePath) {
        float lat = 0;
        float lng = 0;
        String[] strings = new String[2];
        try {
            ExifInterface exifInterface = new ExifInterface(imagePath);
            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                try {
                    lat = convertRationalLatLonToFloat(latValue, latRef);
                    lng = convertRationalLatLonToFloat(lngValue, lngRef);
                    strings[0] = String.valueOf(lat);
                    strings[1] = String.valueOf(lng);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strings;
    }

    private static double parseDouble(String doubleValue, double defaultValue) {
        try {
            return Double.parseDouble(doubleValue);
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    private static void testMedia() {
        String path = "/storage/emulated/0/VID_20191024_103842.mp4";
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);

        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
        Log.e(TAG, "bitmap.getWidth():" + bitmap.getWidth());
        Log.e(TAG, "bitmap.getHeight():" + bitmap.getHeight());
    }

    public static float convertRationalLatLonToFloat(String rationalString, String ref) {
        if (TextUtils.isEmpty(rationalString) || TextUtils.isEmpty(ref)) {
            return 0;
        }

        try {
            String[] parts = rationalString.split(",");

            String[] pair;
            pair = parts[0].split("/");
            double degrees = parseDouble(pair[0].trim(), 0)
                    / parseDouble(pair[1].trim(), 1);

            pair = parts[1].split("/");
            double minutes = parseDouble(pair[0].trim(), 0)
                    / parseDouble(pair[1].trim(), 1);

            pair = parts[2].split("/");
            double seconds = parseDouble(pair[0].trim(), 0)
                    / parseDouble(pair[1].trim(), 1);

            double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
            if (("S".equals(ref) || "W".equals(ref))) {
                return (float) -result;
            }
            LogUtil.e(TAG, "success:" + (float) result);
            return (float) result;
        } catch (NumberFormatException e) {
            LogUtil.e(TAG, "NumberFormatException,e:" + e);
            return 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            LogUtil.e(TAG, "ArrayIndexOutOfBoundsException,e:" + e);
            return 0;
        } catch (Throwable e) {
            LogUtil.e(TAG, "Throwable,e:" + e);
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_location);
        verifyStoragePermissions(this);

        mDescTv = (TextView) findViewById(R.id.desc);
        Button toUrls = (Button) findViewById(R.id.select_img);
        toUrls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
        Button execute = (Button) findViewById(R.id.do_it);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMedia();
                if (TextUtils.isEmpty(mImgPath)) {
                    Toast.makeText(v.getContext(), "请重新选择", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] strings = getPhotoLocation(mImgPath);
                mDescTv.setText(strings[0] + " & " + strings[1]);
            }
        });
    }
    private String mimeType = "*/*";
    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(mimeType);
            String[] mimeTypes = {"video/*", "image/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(mimeType);
            String[] mimeTypes = {"video/*", "image/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        String realPathFromUri =
                                RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                        mImgPath = realPathFromUri;
                        LogUtil.e(TAG, "mImgPath:" + mImgPath);
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }
}
