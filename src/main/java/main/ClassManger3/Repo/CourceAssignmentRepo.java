package main.ClassManger3.Repo;

import main.ClassManger3.Entity.CourceAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourceAssignmentRepo extends JpaRepository<CourceAssignmentEntity, Long> {
}
