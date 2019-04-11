package com.su.hang.nap.base;/**
 * Created by Administrator on 2016/10/17.
 */

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.su.hang.nap.R;


/**
 * 作者：苏航 on 2016/10/17 11:56
 * 邮箱：772192594@qq.com
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
