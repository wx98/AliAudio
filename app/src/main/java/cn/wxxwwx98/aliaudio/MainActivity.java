package cn.wxxwwx98.aliaudio;


import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

import play.AudioPlayer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditView;
    private Button btnPlay;
    public static int cd = 20;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEditView =  findViewById(R.id.audio_edit);
        btnPlay = findViewById(R.id.audio_button);
        btnPlay.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.audio_button:


                String money = mEditView.getText().toString().trim();
                AudioPlayer.getInstance().startPlay(getApplicationContext(), money);
                new Thread(new MyCountDownTimer()).start();
                if (!(money.length() <= 0)){
                    if(money.contains(".")){
                        String str[]  = money.split("\\.");
                        if(str[0].length()>4){
                            Toast.makeText(MainActivity.this, "数值大于一万", Toast.LENGTH_SHORT).show();
                            break;
                        }else if(str[1].length()>2){
                            Toast.makeText(MainActivity.this, "小数位高于两位", Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            new Thread(new MyCountDownTimer()).start();
                            AudioPlayer.getInstance().startPlay(getApplicationContext(), money);
                        }
                    }else {
                        if(money.length()>4){
                            Toast.makeText(MainActivity.this, "数值大于一万", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        new Thread(new MyCountDownTimer()).start();
                        AudioPlayer.getInstance().startPlay(getApplicationContext(), money);
                    }
                }else {

                    Toast.makeText(MainActivity.this, "没有输入", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    class MyCountDownTimer implements Runnable{

        @Override
        public void run() {

            //倒计时开始，循环
            while (cd > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnPlay.setEnabled(false);
                        btnPlay.setTextColor(Color.parseColor("#FFE6417D"));
                        btnPlay.setText(cd +"S");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cd--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    btnPlay.setEnabled(true);
                    btnPlay.setTextColor(Color.parseColor("#FF3D94C6"));
                    btnPlay.setText("播报语音");
                }
            });
            cd = 20;
        }
    }
}
