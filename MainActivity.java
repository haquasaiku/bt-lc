package com.example.win7.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.os.Looper;
import java.io.BufferedReader;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**得到这个按钮对象**/
        button = (Button) findViewById(R.id.button0);
        /**监听这个按钮**/
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /**输出一段Log信息**/
                Log.i("Mytest", "this is a test");
/**开启线程用于监听log输出的信息**/
                new Thread(MainActivity.this).start();
            }
        });
        @Override
        public void run () {
            Process mLogcatProc = null;
            BufferedReader reader = null;
            try {
                //获取logcat日志信息
                mLogcatProc = Runtime.getRuntime().exec(new String[]{"logcat", "Mytest:I *:S"});
                reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.indexOf("this is a test") > 0) {
                        //logcat打印信息在这里可以监听到
                        // 使用looper 把给界面一个显示
                        Looper.prepare();
                        Toast.makeText(this, "监听到log信息", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

}
