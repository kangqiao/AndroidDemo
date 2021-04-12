package com.zp.androidx.demo.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zp.androidx.demo.R;

import java.util.List;

/**
 * Created by zhaopan on 4/12/21
 */
public class AIDLActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn;
    private EditText codeEt;
    private EditText nameEt;
    private Button addBtn;
    private Button getBtn;
    private TextView studentListText;
    private IAIDLInterface proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        startBtn = findViewById(R.id.startBtn);
        codeEt = findViewById(R.id.codeEt);
        nameEt = findViewById(R.id.nameEt);
        addBtn = findViewById(R.id.addBtn);
        getBtn = findViewById(R.id.getBtn);
        studentListText = findViewById(R.id.studentListText);
        initListener();
    }

    private void initListener() {
        startBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        getBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                bindService(new Intent(this, RemoteService.class), connection, BIND_AUTO_CREATE);
                break;

            case R.id.addBtn:
                int code = Integer.valueOf(codeEt.getText().toString().trim());
                String name = nameEt.getText().toString().trim();
                try {
                    proxy.setStudent(new Student(code, name));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.getBtn:
                try {
                    List<Student> students = proxy.getStudent();
                    if (students != null && students.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (Student student : students) {
                            sb.append(student.toString());
                            sb.append("\n");
                        }
                        studentListText.setText(sb.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
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
}
