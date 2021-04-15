package com.zp.androidx.demo.handler;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import com.zp.androidx.base.SysUtils;
import com.zp.androidx.demo.R;
import com.zp.androidx.demo.databinding.ActivityHandlerBarrierBinding;

import java.lang.reflect.Method;

public class HandlerSyncBarrierActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HandlerSyncBarrier";
    private Handler handler;
    private int token;

    public static final int MESSAGE_TYPE_SYNC = 1;
    public static final int MESSAGE_TYPE_ASYN = 2;

    ActivityHandlerBarrierBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncLayoutInflater(this).inflate(R.layout.activity_handler_barrier, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                setContentView(view);
                initView();
            }
        });
    }

    private void initView() {
        //setContentView(R.layout.activity_handler_barrier);
        initHandler();
        initListener();

        findViewById(R.id.btn_frame_choreographer).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final long starTime=System.nanoTime();
                Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                    @Override
                    public void doFrame(long frameTimeNanos) {
                        Log.e(TAG,"starTime="+starTime+", frameTimeNanos="+frameTimeNanos+", frameDueTime="+(frameTimeNanos-starTime)/1000000);
                    }
                });
            }
        });

        SysUtils.printTrack();
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == MESSAGE_TYPE_SYNC) {
                            Log.d(TAG, "收到普通消息");
                        } else if (msg.what == MESSAGE_TYPE_ASYN) {
                            Log.d(TAG, "收到异步消息");
                        }
                    }
                };
                Looper.loop();
            }
        }).start();
    }

    private void initListener() {
        findViewById(R.id.btn_postSyncBarrier).setOnClickListener(this);
        findViewById(R.id.btn_removeSyncBarrier).setOnClickListener(this);
        findViewById(R.id.btn_postSyncMessage).setOnClickListener(this);
        findViewById(R.id.btn_postAsynMessage).setOnClickListener(this);
    }

    //往消息队列插入同步屏障
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendSyncBarrier() {
        try {
            Log.d(TAG, "插入同步屏障1");
            MessageQueue queue = handler.getLooper().getQueue();
            Method method = MessageQueue.class.getDeclaredMethod("postSyncBarrier");
            method.setAccessible(true);
            token = (int) method.invoke(queue);
            Log.d(TAG, "插入同步屏障2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //移除屏障
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void removeSyncBarrier() {
        try {
            Log.d(TAG, "移除屏障1");
            MessageQueue queue = handler.getLooper().getQueue();
            Method method = MessageQueue.class.getDeclaredMethod("removeSyncBarrier", int.class);
            method.setAccessible(true);
            method.invoke(queue, token);
            Log.d(TAG, "移除屏障2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //往消息队列插入普通消息
    public void sendSyncMessage() {
        Log.d(TAG, "插入普通消息");
        Message message = Message.obtain();
        message.what = MESSAGE_TYPE_SYNC;
        handler.sendMessageDelayed(message, 1000);
    }

    //往消息队列插入异步消息
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void sendAsynMessage() {
        Log.d(TAG, "插入异步消息");
        Message message = Message.obtain();
        message.what = MESSAGE_TYPE_ASYN;
        message.setAsynchronous(true);
        handler.sendMessageDelayed(message, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_postSyncBarrier) {
            sendSyncBarrier();
        } else if (id == R.id.btn_removeSyncBarrier) {
            removeSyncBarrier();
        } else if (id == R.id.btn_postSyncMessage) {
            sendSyncMessage();
        } else if (id == R.id.btn_postAsynMessage) {
            sendAsynMessage();
        }
    }
}