package main.ClassManger3.Repo;

import main.ClassManger3.Entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepo extends JpaRepository<EnrollmentEntity, Long> {

    List<EnrollmentEntity> findByStudentID(Long studentid);

}
