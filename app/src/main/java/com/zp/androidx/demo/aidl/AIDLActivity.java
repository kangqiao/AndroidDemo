package com.zp.androidx.demo.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zp.androidx.demo.databinding.ActivityAidlBinding;

import java.util.List;

/**
 * Created by zhaopan on 4/12/21
 */
public class AIDLActivity extends AppCompatActivity {
    private static final String TAG = "Messenger:Client";
    private IAIDLInterface proxy;

    ActivityAidlBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAidlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.startBtn.setOnClickListener(v -> {
            bindService(new Intent(this, RemoteService.class), connection, BIND_AUTO_CREATE);
        });
        binding.btnUnbind.setOnClickListener(v -> {
            unbindService(connection);
        });

        binding.addBtn.setOnClickListener(v -> {
            int code = Integer.valueOf(binding.codeEt.getText().toString().trim());
            String name = binding.nameEt.getText().toString().trim();
            try {
                proxy.setStudent(new Student(code, name));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        binding.getBtn.setOnClickListener(v -> {
            try {
                List<Student> students = proxy.getStudent();
                if (students != null && students.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Student student : students) {
                        sb.append(student.toString());
                        sb.append("\n");
                    }
                    binding.studentListText.setText(sb.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        binding.btnMessengerBind.setOnClickListener(v -> {
            Intent service = new Intent(this, RemoteService.class);
            //startService(service);
            service.putExtra("MESSENGER", true);
            bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
        });
        binding.btnMessengerSend.setOnClickListener(v -> {
            Message message = Message.obtain();
            message.arg1 = RemoteService.SERVICEID;
            //注意这里，把`Activity`的`Messenger`赋值给了`message`中，当然可能你已经发现这个就是`Service`中我们调用的`msg.replyTo`了。
            message.replyTo = aMessenger;

            Bundle bundle = new Bundle();
            bundle.putString("content", "我就是Activity传过来的字符串");
            message.setData(bundle);

            try {
                //消息从客户端发出
                sMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        binding.btnMessengerUnbind.setOnClickListener(v -> {
            unbindService(serviceConnection);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            proxy = IAIDLInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //服务端传来的Messenger
    Messenger sMessenger;
    //客户端的Messnger
    private Messenger aMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == RemoteService.ACTIVITYID) {
                //客户端接受服务端传来的消息
                Log.d(TAG, "服务端传来了消息=====>>>>>>>");
                String str = (String) msg.getData().get("content");
                Log.d(TAG, str);
            }
        }
    });
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
