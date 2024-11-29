package org.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autoCompany")
@NoArgsConstructor
public class AutoCompany implements Trade {

    @JsonProperty("autoCompany_id")
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )

    @Getter
    private UUID id;

    @Getter
    @Setter
    public String companyName;

    @Getter
    @Setter
    private HashSet<String> brandSet = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "autoCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("autoCompanyCarsRef")
    private List<Car> autoCompanyCars = new ArrayList<>();

    public AutoCompany(String companyName){
        this.companyName = companyName;
    }

    @Override
    public void buy(Car car){
        performPurchase(car);
    }

    @Override
    public void sell(Car car){
        saleCar(car);
    }

    private void addBrand(String brand){
        brandSet.add(brand);}
    private void addCar(Car car){
        autoCompanyCars.add(car);}
    private void removeCar(Car car){
        autoCompanyCars.remove(car);}

    private void performPurchase(Car car) {
        addCar(car);
        addBrand(car.getBrand());
    }

    private void saleCar(Car car){
        removeCar(car);
        removeBrandIfNecessary(car.getBrand());
    }

    private void removeBrandIfNecessary(String brand) {
        boolean brandExists = autoCompanyCars.stream()
                .anyMatch(car -> car.getBrand().equals(brand));

        if (!brandExists) {
            brandSet.remove(brand);
        }
    }
}
