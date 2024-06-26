package com.privilledge.pma.repository;

import com.privilledge.pma.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepo extends JpaRepository<Project,Long> {
}
