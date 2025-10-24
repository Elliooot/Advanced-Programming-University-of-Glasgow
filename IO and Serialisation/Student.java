package lab8;

import java.io.Serializable;

class Student implements Serializable {
    int rollNumber;
    String name;
    short age;
    float grade;
    int nameOffset, nameLength;  // for part 4

    Student(int rollNumber_, String name_, short age_, float grade_) {
        rollNumber = rollNumber_;
        name = name_;
        age = age_;
        grade = grade_;
    }
}
