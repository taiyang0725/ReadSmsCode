package com.bc.readsmscode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private SmsObServer mObServer;
    private TextView txt_time;
    private EditText edt_code;

    private boolean isRun;

    private int t = 120;

    private TestAdapter adapter;
    private ArrayList<ItemBean> list = new ArrayList<ItemBean>();
    private ListView lst;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int index = msg.what;

            if (index == 2002) {
                String code = (String) msg.obj;

                isRun = true;
                edt_code.setText(code);

            } else if (index == 2003) {
                int time = (int) msg.obj;
                txt_time.setText(time + "");

            } else if (index == 2004) {
                String va = (String) msg.obj;
                txt_time.setText(va);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            ItemBean bean = new ItemBean();
            bean.setTit("A_" + i);
            list.add(bean);

        }

        edt_code = (EditText) this.findViewById(R.id.edt_code);
        txt_time = (TextView) this.findViewById(R.id.txt_time);
        txt_time.setOnClickListener(this);

        mObServer = new SmsObServer(this, handler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, mObServer);


        lst = (ListView) this.findViewById(R.id.lst);
        adapter = new TestAdapter(list, this);
        lst.setAdapter(adapter);

        this.findViewById(R.id.txt_jump).setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent it=new Intent(MainActivity.this,IndexActivity.class);
                        startActivity(it);

                    }
                });

        setTime();

    }


    private void setTime() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    if (t > 0) {
                        t--;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        handler.obtainMessage(2003, t).sendToTarget();

                    } else {
                        isRun = true;
                        handler.obtainMessage(2004, "重新获取").sendToTarget();
                    }
                } while (!isRun);


            }
        }).start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObServer);
    }

    @Override
    public void onClick(View v) {
        if (("重新获取").equals(txt_time.getText())) {
            t = 120;
            isRun = false;
            setTime();
        }

    }
}
