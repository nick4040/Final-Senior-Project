package main.ClassManger3.Service;

import main.ClassManger3.Entity.CourceAssignmentEntity;
import main.ClassManger3.Entity.GradesEntity;
import main.ClassManger3.Entity.TeacherGradesEntity;
import main.ClassManger3.Repo.CourceAssignmentRepo;
import main.ClassManger3.Repo.GradeRepo;
import main.ClassManger3.Repo.TeacherGradesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private CourceAssignmentRepo courceAssignmentRepo;

    @Autowired
    private GradeRepo gradeRepo;

    @Autowired
    private TeacherGradesRepo teacherGradesRepo;

    public void insertAssignmentDetails() {
        List<GradesEntity> grades = gradeRepo.findAll();

        for (GradesEntity grade : grades) {
            Long assignmentId = grade.getAssignmentId();
            if (assignmentId != null) {
                courceAssignmentRepo.findById(assignmentId).ifPresent(assignment -> {
                    grade.setAssignmentName(assignment.getAssignmentName());
                    gradeRepo.save(grade);
                });
            }
        }
    }

    public void insertSubmissionName() {
        List<TeacherGradesEntity> tGrades = teacherGradesRepo.findAll();

        for (TeacherGradesEntity grade : tGrades){
            Long assignmentId = grade.getAssignmentId();
            if (assignmentId != null){
                gradeRepo.findById(assignmentId).ifPresent(assignment -> {
                    grade.setSubmissionName(assignment.getSubmissionName());
                    teacherGradesRepo.save(grade);
                });
            }
        }

    }

}
