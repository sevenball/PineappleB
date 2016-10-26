package com.wangshiqi.pineappleb.ui.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;
import com.wangshiqi.pineappleb.ui.activity.focus.RGBLuminanceSource;
import com.wangshiqi.pineappleb.ui.activity.focus.ShowActivity;

import java.util.Hashtable;

import zxing.activity.CaptureActivity;

/**
 * Created by dllo on 16/10/26.
 */
public class QRPlayActivity extends AbsBaseActivity implements View.OnClickListener {
    private static final int CHOOSE_PIC = 0;
    private static final int PHOTO_PIC = 1;

    private EditText contentEditText = null;
    private ImageView qrcodeImageView = null;
    private String imgPath = null;

    private ImageView codeBackImg;

    @Override
    protected int setLayout() {
        return R.layout.activity_code;
    }

    @Override
    protected void initViews() {
        codeBackImg  =byView(R.id.code_back);
    }

    @Override
    protected void initDatas() {
//        contentEditText = (EditText) findViewById(R.id.editText1);
//        findViewById(R.id.button1).setOnClickListener(this);
//        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.code_img).setOnClickListener(this);
        codeBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        qrcodeImageView = (ImageView) findViewById(R.id.img1);
    }


    private Result parseQRcodeBitmap(String bitmapPath) {

        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);

        /**
         options.outHeight = 400;
         options.outWidth = 400;
         options.inJustDecodeBounds = false;
         bitmap = BitmapFactory.decodeFile(bitmapPath, options);
         */
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1;
        }
        /**
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgPath = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PIC:
                    String[] proj = new String[]{MediaStore.Images.Media.DATA};
                    Cursor cursor = QRPlayActivity.this.getContentResolver().query(data.getData(), proj, null, null, null);

                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        System.out.println(columnIndex);
                        imgPath = cursor.getString(columnIndex);
                    }
                    cursor.close();

                    Result ret = parseQRcodeBitmap(imgPath);
                    Toast.makeText(QRPlayActivity.this, "���������" + ret.toString(), Toast.LENGTH_LONG).show();
                    break;
                case PHOTO_PIC:
                    String result = data.getExtras().getString("result");
                    Intent intent = new Intent(QRPlayActivity.this, ShowActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                    Toast.makeText(QRPlayActivity.this, "" + result, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

    }

    @SuppressLint("InlinedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button1:
//                // 获取文本字符串
//                String content = contentEditText.getText().toString();
//                // 判断是内容否为空
//                if (null == content || "".equals(content)) {
//                    Toast.makeText(QRPlayActivity.this, "请输入内容",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                try {
//                    //���ɶ�ά��ͼƬ����һ�������Ƕ�ά������ݣ��ڶ���������������ͼƬ�ı߳�����λ������
//                    Bitmap qrcodeBitmap = EncodingHandler.createQRCode(content, 400);
//                    qrcodeImageView.setImageBitmap(qrcodeBitmap);
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.button2:
//                Intent intent1 = new Intent();
////			if(android.os.Build.VERSION.SDK_INT < 19){
////				intent1.setAction(Intent.ACTION_GET_CONTENT);
////			}else{
////				intent1.setAction(Intent.ACTION_OPEN_DOCUMENT);
////			}
//                intent1.setAction(Intent.ACTION_PICK);
//
//                intent1.setType("image/*");
//
//                Intent intent2 =  Intent.createChooser(intent1, "ѡ���ά��ͼƬ");
//                startActivityForResult(intent2, CHOOSE_PIC);
//                break;
            case R.id.code_img:
                Intent intent3 = new Intent(QRPlayActivity.this, CaptureActivity.class);
                startActivityForResult(intent3, PHOTO_PIC);
                break;

            default:
                break;
        }

    }


}
