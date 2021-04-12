package com.zp.androidx.demo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhaopan on 4/12/21
 */
public class Student implements Parcelable {
    private int code;
    private String name;

    public Student(int code, String name) {
        this.code = code;
        this.name = name;
    }

    protected Student(Parcel in) {
        code = in.readInt();
        name = in.readString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(name);
    }
}
