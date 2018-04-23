package com.liurui.htmlandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv = findViewById(R.id.web_view);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);  //设置运行使用JS
        ButtonClick click = new ButtonClick();
        //这里添加JS的交互事件，这样H5就可以调用原生的代码
        wv.addJavascriptInterface(click, click.toString());

        wv.loadUrl("file:///android_asset/h5.html"); //加载assets文件中的H5页面

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.loadUrl("javascript:setRed()");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.loadUrl("javascript:setBlue()");
            }
        });
    }

    /**
     * H5页面按钮点击触发事件
     */
    class ButtonClick {

        //这是 button.click0() 的触发事件
        //H5调用方法：javascript:button.click0()
        @JavascriptInterface
        public void click0() {
            Toast.makeText(MainActivity.this, "点击了button0", Toast.LENGTH_SHORT).show();
        }

        //这是 button.click0() 的触发事件，可以传递待参数
        //H5调用方法：javascript:button.click1()
        @JavascriptInterface
        public void click1() {
            Toast.makeText(MainActivity.this, "点击了button1", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface  //必须添加，这样才可以标志这个类的名称是 myButton
        public String toString() {
            return "myButton";
        }

    }

}
