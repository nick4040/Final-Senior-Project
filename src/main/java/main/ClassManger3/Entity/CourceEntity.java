package main.ClassManger3.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="courcec")
public class CourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String class_names;


    private String class_des;


    private String class_teacher;


    private String class_assigments;


    private String class_link;

    public CourceEntity(String ClassN, String ClassD, String ClassT, String ClassA, String ClassL) {
        this.class_names = ClassN;
        this.class_des = ClassD;
        this.class_teacher = ClassT;
        this.class_assigments = ClassA;
        this.setClass_link(ClassL);
    }

    public CourceEntity(){
        super();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClass_names() {
        return class_names;
    }

    public void setClass_names(String class_names) {
        this.class_names = class_names;
    }

    public String getClass_des() {
        return class_des;
    }

    public void setClass_des(String class_des) {
        this.class_des = class_des;
    }

    public String getClass_teacher() {
        return class_teacher;
    }

    public void setClass_teacher(String class_teacher) {
        this.class_teacher = class_teacher;
    }

    public String getClass_assigments() {
        return class_assigments;
    }

    public void setClass_assigments(String class_assigments) {
        this.class_assigments = class_assigments;
    }

    public String getClass_link() { return class_link; }

    public void setClass_link(String class_link) { this.class_link = class_link; }
}