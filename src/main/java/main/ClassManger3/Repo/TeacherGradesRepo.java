package main.ClassManger3.Repo;

import main.ClassManger3.Entity.TeacherGradesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherGradesRepo extends JpaRepository<TeacherGradesEntity, Long> {
}
