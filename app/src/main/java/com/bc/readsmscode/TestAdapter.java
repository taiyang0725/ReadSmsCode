package com.bc.readsmscode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by An on 2016/3/1.
 */
public class TestAdapter extends BaseAdapter implements SlideView.OnSlideListener {

    private ArrayList<ItemBean> lst;
    private Context context;

    private SlideView mLastSlideViewWithStatusOn;

    public TestAdapter(ArrayList<ItemBean> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodler hodler;

        SlideView slideView = (SlideView) convertView;

        if (slideView == null) {
            View view = View.inflate(context, R.layout.lst_item, null);

            slideView = new SlideView(context);
            slideView.setOnSlideListener(this);
            slideView.setContentView(view);

            hodler = new ViewHodler(slideView);
            slideView.setTag(hodler);

        } else {
            hodler = (ViewHodler) slideView.getTag();
        }

        ItemBean bean = lst.get(position);
        bean.slideView=slideView;
        bean.slideView.shrink();

        hodler.txt_item.setText(bean.getTit());


        return slideView;
    }

    @Override
    public void onSlide(View view, int status) {

        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }

    }


    class ViewHodler {

        private TextView txt_item;
        private TextView txt_edit;
        private TextView txt_delete;

        public ViewHodler(View view) {
            txt_item = (TextView) view.findViewById(R.id.txt_item);
            txt_edit = (TextView) view.findViewById(R.id.edit);
            txt_delete = (TextView) view.findViewById(R.id.delete);
        }
    }


}
