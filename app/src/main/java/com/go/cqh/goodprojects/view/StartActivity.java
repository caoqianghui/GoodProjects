package com.go.cqh.goodprojects.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.go.cqh.goodprojects.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.start_image)
    ImageView startImage;
    @BindView(R.id.kan_logo)
    ImageView kanLogo;
    @BindView(R.id.kan_text_zh)
    TextView kanTextZh;
    @BindView(R.id.kan_text_es)
    TextView kanTextEs;
    @BindView(R.id.kan_info_zh)
    TextView kanInfoZh;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        initImgs();
        startImage.setImageResource(list.get(new Random().nextInt(6)));
        init();
    }

    private void initImgs() {
        list = new ArrayList<>();
        list.add(R.mipmap.kan_1);
        list.add(R.mipmap.kan_2);
        list.add(R.mipmap.kan_3);
        list.add(R.mipmap.kan_4);
        list.add(R.mipmap.kan_5);
        list.add(R.mipmap.kan_6);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    };

    private void init() {
        //更改字体
        //AssetManager assets = this.getAssets();
        //Typeface typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf");
        //kanInfoZh.setTypeface(typeface);

        //获得动画集合对象
        AnimatorSet animatorSet = new AnimatorSet();
        //做动画
        ObjectAnimator objectAnimator_x = ObjectAnimator.ofFloat(startImage, View.SCALE_X, 1f, 1.1f);
        ObjectAnimator objectAnimator_y = ObjectAnimator.ofFloat(startImage, View.SCALE_Y, 1f, 1.1f);
        //设置时长
        animatorSet.setDuration(3000);
        //同时执行动画
        animatorSet.playTogether(objectAnimator_x, objectAnimator_y);
        //开始执行
        animatorSet.start();

        //设置LGGO眼睛不停的旋转
        AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(kanLogo, View.ROTATION, 0f, 360f);
        //设置循环次数
        objectAnimator.setRepeatCount(-1);
        //设置完成时间
        animatorSet2.setDuration(1000);
        //匀速转动
        //LinearInterpolator linearInterpolator = new LinearInterpolator();
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
        //播放动画
        animatorSet2.play(objectAnimator);
        //设置匀速
        animatorSet2.setInterpolator(accelerateDecelerateInterpolator);
        //animatorSet2.start();
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
