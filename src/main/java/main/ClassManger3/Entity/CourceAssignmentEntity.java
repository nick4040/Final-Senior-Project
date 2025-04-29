package main.ClassManger3.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courceAssignments")
public class CourceAssignmentEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long AssignmentID;
    private Long CourceID;

    private String category;

    private String assignmentName;

    private String assignmentdueDate;

    private String assignmentLink;

    private String assignmentType;

    private String assignmentDescription;

    private String linkToAssignment;

    public CourceAssignmentEntity(Long CI, String Cat, String AN, String AD, String AL, String AT, String ADT, String LTA){
        this.CourceID = CI;
        this.category = Cat;
        this.assignmentName = AN;
        this.assignmentdueDate = AD;
        this.assignmentLink = AL;
        this.assignmentType = AT;
        this.assignmentDescription = ADT;
        this.linkToAssignment = LTA;
    }

    public CourceAssignmentEntity(){
        super();
    }

    public Long getCourceID() {
        return CourceID;
    }

    public void setCourceID(Long courceID) {
        CourceID = courceID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentdueDate() {
        return assignmentdueDate;
    }

    public void setAssignmentdueDate(String assignmentdueDate) {
        this.assignmentdueDate = assignmentdueDate;
    }

    public String getAssignmentLink() {
        return assignmentLink;
    }

    public void setAssignmentLink(String assignmentLink) {
        this.assignmentLink = assignmentLink;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }
    public Long getAssignmentID() {
        return AssignmentID;
    }

    public void setAssignmentID(Long assignmentID) {
        AssignmentID = assignmentID;
    }

    public String getLinkToAssignment() {
        return linkToAssignment;
    }

    public void setLinkToAssignment(String linkToAssignment) {
        this.linkToAssignment = linkToAssignment;
    }
}
