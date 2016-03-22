package com.bc.readsmscode;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by An on 2016/3/2.
 */
public class IndexActivity extends Activity {

    private TestAdapter adapter;
    private ArrayList<ItemBean> list = new ArrayList<ItemBean>();
    private ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.index_activity);

        for (int i = 0; i < 10; i++) {
            ItemBean bean = new ItemBean();
            bean.setTit("A_" + i);
            list.add(bean);

        }

        lst = (ListView) this.findViewById(R.id.lst);
        adapter = new TestAdapter(list, this);
        lst.setAdapter(adapter);
    }
}
