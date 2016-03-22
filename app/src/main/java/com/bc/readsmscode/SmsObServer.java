package com.bc.readsmscode;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by An on 2016/2/26.
 */
public class SmsObServer extends ContentObserver {

    private Context context;
    private Handler handler;

    public SmsObServer(Context context, Handler handler) {
        super(handler);

        this.context = context;
        this.handler = handler;

    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        /**
         * 短信验证码
         * */
        String code = "";

        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Uri inboxUri = Uri.parse("content://sms/inbox");// 收件箱uri：content://sms/inbox

        Cursor mCursor = context.getContentResolver().query(inboxUri, null, null, null, "date desc");

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                String address = mCursor.getString(mCursor.getColumnIndex("address"));//短信发件人
                String body = mCursor.getString(mCursor.getColumnIndex("body"));//短信内容

                Log.d("info", address + ":" + body);

                /**
                 * 正则表达式提取验证码
                 * */
                Pattern pattern = Pattern.compile("[0-9]{6}");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()) {
                    code = matcher.group(0);

                    Log.d("********", code);

                    handler.obtainMessage(2002, code).sendToTarget();
                }


            }
            mCursor.close();
        }


    }
}
