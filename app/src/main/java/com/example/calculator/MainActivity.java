package com.example.calculator;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView ac;
    private TextView clear;

    private Button ppm;
    private Button pct;

    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button point;

    private Button div;
    private Button mul;
    private Button sub;
    private Button add;
    private Button equal;

    private Switch history;

    private TextView op;
    String op_l;
    private TextView tv;

    String a = "";
    String b = "";
    String b_l = "";

    boolean o = false;
    boolean e = false;

    boolean di = false;
    boolean mu = false;
    boolean ad = false;
    boolean su = false;
    boolean di_l = false;
    boolean mu_l = false;
    boolean ad_l = false;
    boolean su_l = false;

    double x,y,z;//运算符
    double x_l,y_l,z_l;

    double p;//结算符
    int q;//结果为整数时作为结算符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题栏，鉴定为无效操作，原因不明
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏显示，具体指隐藏状态栏，无法隐藏虚拟按键

        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        point = findViewById(R.id.point);

        ac = findViewById(R.id.ac);
        clear = findViewById(R.id.clear);
        ppm = findViewById(R.id.ppm);
        pct = findViewById(R.id.pct);

        div = findViewById(R.id.div);
        mul = findViewById(R.id.mul);
        sub = findViewById(R.id.sub);
        add = findViewById(R.id.add);
        equal = findViewById(R.id.equal);

        op = findViewById(R.id.textview_1);
        tv = findViewById(R.id.textview_0);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        point.setOnClickListener(this);

        clear.setOnClickListener(this);
        ac.setOnClickListener(this);
        ppm.setOnClickListener(this);
        pct.setOnClickListener(this);

        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        equal.setOnClickListener(this);

        history = findViewById(R.id.history);
        history.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tv.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                else{
                    tv.setVisibility(View.INVISIBLE);
                    clear.setVisibility(View.INVISIBLE);//不显示但保留占用空间
                }
            }
        });//按下按钮显示/隐藏textview及清空键
    }

    public void onClick(View view){
         switch (view.getId()) {
             case R.id.clear: {
                 b = "";
                 tv.setText("履歴");
             }
             break;

             case R.id.ac: {
                 op.setText("0");
                 ac.setText("AC");
                 buttonBack();
                 String bbb= b.substring(b.lastIndexOf("\n")+1);
                 Log.d("GGG", bbb);
                 if (bbb.equals("")){
                     break;
                 }
                 else{
                     b = b + "\n";
                     tvShowBottom();
                 }
                 x = 0;
                 y = 0;
                 z = 0;
                 o = false;
                 di = false;
                 mu = false;
                 ad = false;
                 su = false;
             }
             break;

             case R.id.ppm: {
                 a =op.getText().toString();
                 if (a.indexOf("エラー") != -1){
                     p = -0;
                 }
                 else {
                     p = (-1) * Double.parseDouble(op.getText().toString());
                     round();
                 }

             }
             break;

             case R.id.pct: {
                 if (a.indexOf("エラー") != -1){
                     break;
                 }
                 else {
                     p = 0.01*Double.parseDouble(op.getText().toString());
                     round();
                 }
             }
             break;

             case R.id.point: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "0";
                     buttonBack();
                 }
                 a = op.getText().toString();
                 if (a.indexOf(".") != -1){
                     break;
                 }
                 else if (a.indexOf("エラー") != -1){
                     a = "0.";
                 }
                 else {
                     a = op.getText().toString() + ".";
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.zero: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "0";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "0";
                 }
                 else{
                     a = op.getText().toString() + 0;
                 }
                 op.setText(a);
             }
             break;

             case R.id.one: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "1";
                     buttonBack();
                 }
                 else if (op.getText().toString().equals("0")) {
                     a = "1";
                 }
                 else{
                     a = op.getText().toString() + 1;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.two: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "2";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "2";
                 }
                 else{
                     a = op.getText().toString() + 2;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.three: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "3";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "3";
                 }
                 else{
                     a = op.getText().toString() + 3;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.four: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "4";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "4";
                 }
                 else{
                     a = op.getText().toString() + 4;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.five: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "5";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "5";
                 }
                 else{
                     a = op.getText().toString() + 5;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;


             case R.id.six: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "6";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "6";
                 }
                 else{
                     a = op.getText().toString() + 6;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.seven: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "7";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "7";
                 }
                 else{
                     a = op.getText().toString() + 7;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.eight: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "8";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "8";
                 }
                 else{
                     a = op.getText().toString() + 8;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

             case R.id.nine: {
                 if (o || e){
                     o = false;
                     e = false;
                     a = "9";
                     buttonBack();
                 }
                 else if(op.getText().toString().equals("0")) {
                     a = "9";
                 }
                 else{
                     a = op.getText().toString() + 9;
                 }
                 ac.setText("C");
                 op.setText(a);
             }
             break;

//按下div有6种情况
// (1)x÷y → x÷ ;
// (2)x×y → x÷ ;
// (3)x÷ ;
// (4)x+-y÷z → x+-y÷ ;
// (5)x+-y×z → x+-y÷ ;
// (6)x+-y → x+-y÷ ;
             case R.id.div: {
                 div.setEnabled(false);
                 mul.setEnabled(true);
                 sub.setEnabled(true);
                 add.setEnabled(true);

                 div.setTextColor(Color.parseColor("#daa520"));
                 mul.setTextColor(Color.WHITE);
                 sub.setTextColor(Color.WHITE);
                 add.setTextColor(Color.WHITE);

                 if (!o){
                     load();
                 }
                 else {
                     read();
                 }
                 b = b + "÷";
                 tvShowBottom();

                 if (!su && !ad) {
                     if (di) {
                         y = Double.parseDouble(op.getText().toString());
                         if (y == 0){
                             error();
                             break;
                         }
                         p = x / y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         o = true;
                     }
                     else if (mu) {
                         y = Double.parseDouble(op.getText().toString());
                         p = x * y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = true;
                         mu = false;
                         o = true;
                     }
                     else{
                         di = true;
                         o = true;
                         x = Double.parseDouble(op.getText().toString());
                     }
                 }
                 else {
                     if (di){
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0){
                             error();
                             break;
                         }
                         p = y / z;
                         round();
                         y = Double.parseDouble(op.getText().toString());
                         o = true;
                     }
                     else if (mu){
                         z = Double.parseDouble(op.getText().toString());
                         p = y * z;
                         round();
                         y = Double.parseDouble(op.getText().toString());
                         mu = false;
                         di = true;
                         o = true;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         di = true;
                         o = true;
                     }
                 }
             }
             break;

//按下mul有6种情况
// (1)x÷y → x× ;
// (2)x×y → x× ;
// (3)x× ;
// (4)x+-y÷z → x+-y× ;
// (5)x+-y×z → x+-y× ;
// (6)x+-y → x+-y× ;
             case R.id.mul: {
                 div.setEnabled(true);
                 mul.setEnabled(false);
                 sub.setEnabled(true);
                 add.setEnabled(true);

                 mul.setTextColor(Color.parseColor("#daa520"));
                 div.setTextColor(Color.WHITE);
                 sub.setTextColor(Color.WHITE);
                 add.setTextColor(Color.WHITE);

                 if (!o){
                     load();
                 }
                 else {
                     read();
                 }

                 b = b + "×";
                 tvShowBottom();

                 if (!su && !ad) {
                     if (di){
                         y = Double.parseDouble(op.getText().toString());
                         if (y == 0){
                             error();
                             break;
                         }
                         p = x / y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         mu = true;
                         o = true;
                     }
                     else if (mu){
                         y = Double.parseDouble(op.getText().toString());
                         p = x * y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         o = true;
                     }
                     else {
                         mu = true;
                         o = true;
                         x = Double.parseDouble(op.getText().toString());
                     }
                 }
                 else {
                     if (di){
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0){
                             error();
                             break;
                         }
                         p = y / z;
                         round();
                         y = Double.parseDouble(op.getText().toString());
                         di = false;
                         mu = true;
                         o = true;
                     }
                     else if(mu){
                         z = Double.parseDouble(op.getText().toString());
                         p = y * z;
                         round();
                         y = Double.parseDouble(op.getText().toString());
                         o = true;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         mu = true;
                         o = true;
                     }
                 }
             }
             break;

//按下sub有9种情况
// (1)x-y → x- ;
// (2)x+y → x- ;
// (3)x- ;
// (4)x-y÷z → x- ;
// (5)x+y÷z → x- ;
// (6)x÷y → x- ;
// (7)x-y×z → x- ;
// (8)x+y×z → x- ;
// (9)x×y → x- ;
             case R.id.sub: {
                 sub.setEnabled(false);
                 div.setEnabled(true);
                 mul.setEnabled(true);
                 add.setEnabled(true);

                 sub.setTextColor(Color.parseColor("#daa520"));
                 div.setTextColor(Color.WHITE);
                 mul.setTextColor(Color.WHITE);
                 add.setTextColor(Color.WHITE);

                 if (!o){
                     load();
                 }
                 else {
                     read();
                 }

                 b = b + "-";
                 tvShowBottom();

                 if (!di && !mu){
                     if(su){
                         y = Double.parseDouble(op.getText().toString());
                         p = x - y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         o = true;
                     }
                     else if (ad){
                         y = Double.parseDouble(op.getText().toString());
                         p = x + y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         ad = false;
                         su = true;
                         o = true;
                     }
                     else{
                         su = true;
                         o = true;
                         x = Double.parseDouble(op.getText().toString());
                     }
                 }
                 else if (di) {
                     if (su){
                          z = Double.parseDouble(op.getText().toString());
                         if (z == 0) {
                             error();
                             break;
                         }
                         p = x - y / z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         o = true;
                     }
                     else if (ad){
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0) {
                             error();
                             break;
                         }
                         p = x + y / z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         ad = false;
                         su = true;
                         o = true;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         if (y == 0){
                             error();
                             break;
                         }
                         p = x / y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         su = true;
                         o = true;
                     }
                 }
                 else{
                     if (su){
                         z = Double.parseDouble(op.getText().toString());
                         p = x - y * z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         mu = false;
                         o = true;
                     }
                     else if (ad){
                         z = Double.parseDouble(op.getText().toString());
                         p = x + y * z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         mu = false;
                         su = true;
                         ad = false;
                         o = true;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         p = x * y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         mu = false;
                         su = true;
                         o = true;
                     }
                 }
             }
             break;

//按下add有9种情况
// (1)x-y → x+ ;
// (2)x+y → x+ ;
// (3)x+ ;
// (4)x-y÷z → x+ ;
// (5)x+y÷z → x+ ;
// (6)x÷y → x+ ;
// (7)x-y×z → x+ ;
// (8)x+y×z → x+ ;
// (9)x×y → x+ ;
             case R.id.add: {
                 add.setEnabled(false);
                 sub.setEnabled(true);
                 div.setEnabled(true);
                 mul.setEnabled(true);

                 add.setTextColor(Color.parseColor("#daa520"));
                 div.setTextColor(Color.WHITE);
                 mul.setTextColor(Color.WHITE);
                 sub.setTextColor(Color.WHITE);

                 if (!o){
                     load();
                 }
                 else {
                     read();
                 }

                 b = b + "+";
                 tvShowBottom();

                 if (!di && !mu){
                     if(su){
                         y = Double.parseDouble(op.getText().toString());
                         p = x - y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         su = false;
                         ad = true;
                         o = true;
                     }
                     else if (ad){
                         y = Double.parseDouble(op.getText().toString());
                         p = x + y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         o = true;
                     }
                     else{
                         ad = true;
                         o = true;
                         x = Double.parseDouble(op.getText().toString());
                     }
                 }
                 else if (di) {
                     if (su){
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0) {
                             error();
                             break;
                         }
                         p = x - y / z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         su = false;
                         ad = true;
                         o = true;
                     }
                     else if (ad){
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0) {
                             error();
                             break;
                         }
                         p = x + y / z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         o = true;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         if (y == 0){
                             error();
                             break;
                         }
                         p = x / y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         di = false;
                         ad = true;
                         o = true;
                     }
                 }
                 else{
                     if (su){
                         z = Double.parseDouble(op.getText().toString());
                         p = x - y * z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         mu = false;
                         ad = true;
                         o = true;
                     }
                     else if (ad){
                         z = Double.parseDouble(op.getText().toString());
                         p = x + y * z;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         mu = false;
                         o = true;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         p = x * y;
                         round();
                         x = Double.parseDouble(op.getText().toString());
                         mu = false;
                         ad = true;
                         o = true;
                     }
                 }
             }
             break;

//只有在前一个符号后有数字的情况下才能按下等于号
//按下equal有8种情况
// (1)x+y ;
// (2)x-y ;
// (3)x+y÷z ;
// (4)x-y÷z ;
// (5)x÷y ;
// (6)x+y×z ;
// (7)x-y×z ;
// (8)x×y ;
             case R.id.equal: {
                 b = b + op.getText() + "=";
                 tvShowBottom();

                 if (!di && !o && !mu) {
                     if (ad) {
                         y = Double.parseDouble(op.getText().toString());
                         p = x + y;
                         round();
                         ad = false;

                     }
                     else if (su) {
                         y = Double.parseDouble(op.getText().toString());
                         p = x - y;
                         round();
                         su = false;

                     }
                 }
                 else if (di && !o) {
                     if (ad) {
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0) {
                             error();
                             break;
                         }
                         else {
                             p = x + y /z;
                             round();
                             ad = false;
                             di = false;
                         }
                     }
                     else if (su) {
                         z = Double.parseDouble(op.getText().toString());
                         if (z == 0) {
                             error();
                             break;
                         }
                         else {
                             p = x - y /z;
                             round();
                             su = false;
                             di = false;
                         }
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         if (y == 0) {
                             error();
                             break;
                         }
                         else {
                             p = x / y;
                             round();
                             di = false;
                         }
                     }
                 }
                 else if (mu && !o) {
                     if (ad) {
                         z = Double.parseDouble(op.getText().toString());
                         p = x + y * z;
                         round();
                         ad = false;
                         mu = false;
                     }
                     else if (su) {
                         z = Double.parseDouble(op.getText().toString());
                         p = x - y * z;
                         round();
                         su = false;
                         mu = false;
                     }
                     else {
                         y = Double.parseDouble(op.getText().toString());
                         p = x * y;
                         round();
                         mu = false;
                     }
                 }
                 b = b + op.getText() +"\n";
                 tvShowBottom();

                 e = true;
                 break;
             }
         }
    }

    public void buttonBack(){
        div.setEnabled(true);
        mul.setEnabled(true);
        sub.setEnabled(true);
        add.setEnabled(true);

        div.setTextColor(Color.WHITE);
        mul.setTextColor(Color.WHITE);
        sub.setTextColor(Color.WHITE);
        add.setTextColor(Color.WHITE);
    }//颜色复位

    public void tvShowBottom(){
        tv.setText(b);
        int offset=tv.getLineCount()*tv.getLineHeight();
        if (offset > tv.getHeight()) {
            tv.scrollTo(0, offset - tv.getHeight());
        }
    }//自动滑动带文本框底部

    public void round(){
        if (p % 1 == 0){
            q = (Double.valueOf(p)).intValue();
            op.setText(Integer.toString(q));
        }
        else {
            p = new BigDecimal(p).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            op.setText(Double.toString(p));
        }
    }//整数输出int，小数保留小数点后两位输出double

     public void error(){
         op.setText("エラー");
         b = b + "エラー" + "\n";
         tvShowBottom();
         buttonBack();
         x = 0;
         y = 0;
         z = 0;
         o = true;
         di = false;
         mu = false;
         ad = false;
         su = false;
     }//除数为零时停止运算并重置程序

     public void load(){
        x_l = x;
        y_l = y;
        z_l = z;
        di_l = di;
        mu_l = mu;
        su_l = su;
        ad_l = ad;
        op_l = op.getText().toString();
        b = b + op.getText().toString();
        b_l = b;
         tvShowBottom();
     }//按下多重运算符时切换处理（保存）

    public void read(){
        x = x_l;
        y = y_l;
        z = z_l;
        di = di_l;
        mu = mu_l;
        su = su_l;
        ad = ad_l;
        op.setText(op_l);
        b = b_l;
        tvShowBottom();
    }
    //按下多重运算符时切换处理（读取）
}
//按下div有6种情况
// (1)x÷y → x÷ ;
// (2)x×y → x÷ ;
// (3)x÷ ;
// (4)x+-y÷z → x+-y÷ ;
// (5)x+-y×z → x+-y÷ ;
// (6)x+-y → x+-y÷ ;

//按下mul有6种情况
// (1)x÷y → x× ;
// (2)x×y → x× ;
// (3)x× ;
// (4)x+-y÷z → x+-y× ;
// (5)x+-y×z → x+-y× ;
// (6)x+-y → x+-y× ;

//按下sub有9种情况
// (1)x-y → x- ;
// (2)x+y → x- ;
// (3)x- ;
// (4)x-y÷z → x- ;
// (5)x+y÷z → x- ;
// (6)x÷y → x- ;
// (7)x-y×z → x- ;
// (8)x+y×z → x- ;
// (9)x×y → x- ;

//按下add有9种情况
// (1)x-y → x+ ;
// (2)x+y → x+ ;
// (3)x+ ;
// (4)x-y÷z → x+ ;
// (5)x+y÷z → x+ ;
// (6)x÷y → x+ ;
// (7)x-y×z → x+ ;
// (8)x+y×z → x+ ;
// (9)x×y → x+ ;

//只有在前一个符号后有数字的情况下才能按下等于号
//按下equal有8种情况
// (1)x+y ;
// (2)x-y ;
// (3)x+y÷z ;
// (4)x-y÷z ;
// (5)x÷y ;
// (6)x+y×z ;
// (7)x-y×z ;
// (8)x×y ;