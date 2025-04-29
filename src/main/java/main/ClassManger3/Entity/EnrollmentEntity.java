package main.ClassManger3.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Enrollment")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EnrollmentID;

    private Long studentID;

    private Long CourceID;

    public EnrollmentEntity(Long EID, Long SID, Long CID){
        this.setEnrollmentID(EID);
        this.setStudentID(SID);
        this.setCourceID(CID);
    }

    public EnrollmentEntity(){
        super();
    }

    public Long getEnrollmentID() {
        return EnrollmentID;
    }

    public void setEnrollmentID(Long enrollmentID) {
        EnrollmentID = enrollmentID;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getCourceID() {
        return CourceID;
    }

    public void setCourceID(Long courceID) {
        CourceID = courceID;
    }
}
