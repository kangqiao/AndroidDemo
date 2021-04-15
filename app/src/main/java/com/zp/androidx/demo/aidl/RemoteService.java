package com.zp.androidx.demo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopan on 4/12/21
 */
public class RemoteService extends Service {
    private static final String TAG = "Messenger:Server";
    public static final int SERVICEID = 0x0001;
    public static final int ACTIVITYID = 0x0002;
    private List<Student> studentList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (intent.getBooleanExtra("MESSENGER", false)) {
            return messenger.getBinder();
        } else {
            return stub;
        }
    }

    IAIDLInterface.Stub stub = new IAIDLInterface.Stub() {
        @Override
        public List<Student> getStudent() throws RemoteException {
            return studentList;
        }

        @Override
        public void setStudent(Student student) throws RemoteException {
            studentList.add(student);
        }
    };

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == SERVICEID) {
                //接受从客户端传来的消息
                Log.d(TAG, "客服端传来的消息===>>>>>>");
                String str = (String) msg.getData().get("content");
                Log.d(TAG, str);

                //发送数据给客户端
                Message msgTo = Message.obtain();
                msgTo.arg1 = ACTIVITYID;
                Bundle bundle = new Bundle();
                bundle.putString("content", "我是从服务器来的字符串");
                msgTo.setData(bundle);
                try {
                    //注意，这里把数据从服务器发出了
                    msg.replyTo.send(msgTo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}
