package main.ClassManger3.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_grade_assignment")
public class TeacherGradesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tGradeId;

    private Long Grade;

    private Long assignmentId;

    private String teacherDescription;

    private String submissionName;

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getGradeId() {
        return tGradeId;
    }

    public void setGradeId(Long grade) {
        this.tGradeId = grade;
    }

    public Long getGrade() {
        return Grade;
    }

    public void setGrade(Long grade) {
        this.Grade = grade;
    }

    public String getTeacherDescription() {
        return teacherDescription;
    }

    public void setTeacherDescription(String teacherDescription) {
        this.teacherDescription = teacherDescription;
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public void setSubmissionName(String submissionName) {
        this.submissionName = submissionName;
    }
}
