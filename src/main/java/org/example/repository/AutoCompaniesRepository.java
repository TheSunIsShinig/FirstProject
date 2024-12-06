package org.example.repository;

import org.example.models.AutoCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutoCompaniesRepository extends JpaRepository<AutoCompany, UUID> {
}
