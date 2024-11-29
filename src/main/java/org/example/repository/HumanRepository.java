package org.example.repository;

import org.example.models.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface HumanRepository extends JpaRepository<Human, UUID> {

    List<Human> findHumansByName(String name);

    Human findByUsername(String username);
}