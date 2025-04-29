package main.ClassManger3.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StudentID;

    private String username;

    private String Password;

    private String role;

    public UserEntity(String US, String PW, String RO){
        this.setUsername(US);
        this.setPassword(PW);
        this.setRole(RO);
    }

    public UserEntity(){
        super();
    }

    public Long getStudentID() {
        return StudentID;
    }

    public void setStudentID(Long studentID) {
        StudentID = studentID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username1) {
        username = username1;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role1) {
        role = role1;
    }
}
