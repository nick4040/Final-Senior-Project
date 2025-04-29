package main.ClassManger3.Repo;

import main.ClassManger3.Entity.CourceNavInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourceNavRepo extends JpaRepository<CourceNavInfoEntity, Long> {
    //List<CourceNavInfoEntity> findAllByCourceID(Long CourceID);
}
