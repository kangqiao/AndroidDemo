// IAIDLInterface.aidl
package com.zp.androidx.demo.aidl;

// Declare any non-default types here with import statements
import com.zp.androidx.demo.aidl.Student;

interface IAIDLInterface {

    List<Student> getStudent();
    void setStudent(in Student student);
}