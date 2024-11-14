package org.example.repository;

import org.example.models.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface HumanRepository extends JpaRepository<Human, UUID> {

    @Query("SELECT h FROM Human h WHERE h.name = ?1")
    List<Human> findByName(String name);
}