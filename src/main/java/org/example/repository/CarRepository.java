package org.example.repository;

import org.example.models.Car;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    List<Car> findByBrand(String brand);

    List<Car> findByModel(String model);

    List<Car> findByYear(int year);

    @Query("SELECT c FROM Car c WHERE c.price BETWEEN :minPrice AND :maxPrice")
    List<Car> findCarsByPriceRange(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice);

    @Query("SELECT c FROM Car c WHERE c.year BETWEEN :minYear AND :maxYear")
    List<Car> findCarsByYearRange(@Param("minYear") int minYear, @Param("maxYear") int maxYear);
}
