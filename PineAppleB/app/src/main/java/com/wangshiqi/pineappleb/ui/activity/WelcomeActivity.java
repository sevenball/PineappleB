package com.wangshiqi.pineappleb.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;


/**
 * Created by dllo on 16/10/31.
 */
public class WelcomeActivity extends AbsBaseActivity {
    private ImageView welImg;
    private TextView welTv;
    private MyTask myTask;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {
        welImg = byView(R.id.welcome_img);
        welTv = byView(R.id.welcome_tv);
        welImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.cancel(true);
                goTo(WelcomeActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        myTask = new MyTask();
        myTask.execute(0);
    }
    /**
     * AsyncTask是一个抽象类,需要子类继承并实现其方法
     * ASyncTask在继承时需要填入三个参数
     * 参数一:参与任务执行的参数, 如网址填String
     *       我们当前例子演示的是:计数,因此填Integer
     * 参数二:进度的类型, 计数的进度是查到了几
     * 参数三:结果的类型, 当前例子:在处理完成显示 完成 二字
     * 如果不需要哪个参数,填无, 即 Void,注意大小写
     */

    /**   1
     * 必须复写的方法: 类似于run方法,参数就是填写类的声明时 那三个参数的第一个
     * ...形式是Java的一种数据类型方式,代表数组
     */
    private class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected String doInBackground(Integer... params) {
            // 获得任务执行的参数,指的是计数的最大值
            int sum = params[0];
            int now = 3;
            while(now>sum){
                // 循环发送任务执行的进度
                publishProgress(now);
                now--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return "0";
        }
        // 接收publishProgress发布的进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            welTv.setText(String.valueOf(values[0])+"s");
        }
        //处理结果
        //该方法是处理后台运行结果的,会运行在主线程中,Handler封装在此方法中
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            welTv.setText(s+"s");
            goTo(WelcomeActivity.this, MainActivity.class);
            finish();
        }

    }


}
