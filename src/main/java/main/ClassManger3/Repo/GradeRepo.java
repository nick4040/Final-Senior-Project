package main.ClassManger3.Repo;

import main.ClassManger3.Entity.GradesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<GradesEntity, Long> {
}
