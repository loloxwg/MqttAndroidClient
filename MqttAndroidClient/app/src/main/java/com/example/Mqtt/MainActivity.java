package com.example.Mqtt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements IGetMessageCallBack {

    //    private TextView txt3,txt4,txt5;
    private String str[]={"","","","",""};//change
    private SimpleAdapter adapter;
    private TextView txt;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private ArrayList list,newlist;
    private ListView listView;
    private MyServiceConnection serviceConnection;
    int j = 0;
//    private MqttService mqttService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        txt3 =  findViewById(R.id.txt3);
//        txt4 =  findViewById(R.id.txt4);
//        txt5 =  findViewById(R.id.txt5);

        txt  = findViewById(R.id.item_content);
        button = findViewById(R.id.button);
        button2= findViewById(R.id.button2);
        button3= findViewById(R.id.button3);
        button4= findViewById(R.id.button4);
        button5= findViewById(R.id.button5);
        button6= findViewById(R.id.button6);
        button7= findViewById(R.id.button7);
        listView = findViewById(R.id.lv);
        initDataList();

        // key值数组，适配器通过key值取value，与列表项组件一一对应
        String[] from = { "img", "title", "content" };
        // 列表项组件Id 数组
        int[] to = { R.id.item_img, R.id.item_title, R.id.item_content};

        /**
         * SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
         * context：activity界面类
         * data 数组内容是map的集合数据
         * resource 列表项文件
         * from map key值数组
         * to 列表项组件id数组      from与to一一对应，适配器绑定数据
         */
        adapter = new SimpleAdapter(this, list,
                R.layout.listview_item, from, to);

        listView.setAdapter(adapter);

        serviceConnection = new MyServiceConnection();
        //MyServiceConnection类中定义的方法setIGetMessageCallBack
        serviceConnection.setIGetMessageCallBack(MainActivity.this);

        Intent intent = new Intent(this, MqttService.class);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        //灯
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j++;
                if (j == 1) {
                    MqttService.publish("0");
                    button.setBackgroundResource(R.mipmap.light3);
                    Toast.makeText(MainActivity.this,"关灯",Toast.LENGTH_SHORT).show();
                } else if (j == 2) {
                    MqttService.publish("1");
                    button.setBackgroundResource(R.mipmap.light);
                    Toast.makeText(MainActivity.this,"开灯",Toast.LENGTH_SHORT).show();
                    j = 0;
                }
            }
        });
        //风扇
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if (j==1){
                    MqttService.publish("2");
                    button2.setBackgroundResource(R.mipmap.dianfengshan1);
                    Toast.makeText(MainActivity.this,"关闭风扇",Toast.LENGTH_SHORT).show();
                }else if(j==2){
                    MqttService.publish("3");
                    button2.setBackgroundResource(R.mipmap.dianfengshan2);
                    Toast.makeText(MainActivity.this,"打开风扇",Toast.LENGTH_SHORT).show();
                    j=0;
                }
            }
        });
        //门锁
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if(j==1){
                    button3.setBackgroundResource(R.mipmap.mensuo);
                    Toast.makeText(MainActivity.this,"5s后锁自动关闭",Toast.LENGTH_SHORT).show();
                }else if(j==2){
                    MqttService.publish("5");
                    button3.setBackgroundResource(R.mipmap.mensuokai);
                    Toast.makeText(MainActivity.this,"开锁",Toast.LENGTH_SHORT).show();
                    j=0;
                }
            }
        });
        //刷新
        button4.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MqttService.publish("6");
                Toast.makeText(MainActivity.this,"正在刷新监控数据",Toast.LENGTH_SHORT).show();
            }
        }));
        //遥控电视
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if (j==1){
                    MqttService.publish("7");
                    button5.setBackgroundResource(R.mipmap.yaokongpingmu);
                    Toast.makeText(MainActivity.this,"关闭电视",Toast.LENGTH_SHORT).show();
                }else if(j==2){
                    MqttService.publish("8");
                    button5.setBackgroundResource(R.mipmap.yaokongpingmu2);
                    Toast.makeText(MainActivity.this,"打开电视",Toast.LENGTH_SHORT).show();
                    j=0;
                }
            }
        });
        //遥控盒子
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if (j==1){
                    MqttService.publish("9");
                    button6.setBackgroundResource(R.mipmap.yangkonghezi);
                    Toast.makeText(MainActivity.this,"关闭电视盒子",Toast.LENGTH_SHORT).show();
                }else if(j==2){
                    MqttService.publish("10");
                    button6.setBackgroundResource(R.mipmap.yangkonghezi2);
                    Toast.makeText(MainActivity.this,"打开电视盒子",Toast.LENGTH_SHORT).show();
                    j=0;
                }
            }
        });
        //web support
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://256356n7p7.wicp.vip"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void setMessage(String message) {
        str[0]=message;
        updateDataList();

    }
    @Override
    public void setMessage1(String message) {
        str[1]=message;
        updateDataList();
    }

    @Override
    public void setMessage2(String message) {
        str[2]=message;
        updateDataList();
    }
    @Override
    public void setMessage3(String message) {
        str[3]=message;
        updateDataList();
    }

    @Override
    public void setMessage4(String message){
        str[4]=message;
        updateDataList();
    }
    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    /**
     * 初始化适配器需要的数据格式
     */
    private void initDataList() {
        //图片资源
        int img[] = {R.mipmap.taiyang2, R.mipmap.wendu, R.mipmap.shidu,R.mipmap.qitizhengchang,R.mipmap.tianqi};
        String text[] = {"光敏","温度","湿度","气体","天气"};
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", img[i]);
            map.put("title",text[i]);
            map.put("content", "0");
            list.add(map);
        }
    }

    private void updateDataList(){
        list.clear();
        int img[] = {R.mipmap.taiyang2, R.mipmap.wendu, R.mipmap.shidu,R.mipmap.qitizhengchang,R.mipmap.tianqi};
        String text[] = {"光敏","温度","湿度","气体","天气"};
        newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", img[i]);
            map.put("title",text[i]);
            map.put("content",str[i]);
            list.add(map);
        }
        //txt.setText(message);
        list.addAll(newlist);
        adapter.notifyDataSetChanged();
    }
}