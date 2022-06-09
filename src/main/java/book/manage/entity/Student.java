package book.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {
    int sid;
    String name;
    String gender;
    int grade;

    public Student(String name, String gender, int grade) {
        this.name = name;
        this.gender = gender;
        this.grade = grade;
    }
}
