package org.example.repository;

import org.example.models.AutoCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AutoCompaniesRepository extends JpaRepository<AutoCompany, UUID> {
}
