package main.ClassManger3.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.ClassManger3.Entity.CourceEntity;

@Repository
public interface CourceRepo extends JpaRepository<CourceEntity, Long> {
}

