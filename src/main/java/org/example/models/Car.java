package org.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "car")
@NoArgsConstructor
public class Car {

    @JsonProperty("car_id")
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )

    @Getter private UUID id;
    @Setter @Getter private String brand;
    @Setter @Getter private String model;
    @Setter @Getter private String color;
    @Setter @Getter private int price;
    @Setter @Getter private int year;
    @Setter @Getter private int mileage;
    @Setter @Getter private int numberOfOwners;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = true)
    @JsonBackReference
    private Human owner;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autoCompany_id", nullable = true)
    @JsonBackReference("autoCompanyCarsRef")
    private AutoCompany autoCompany;

    @JsonProperty("owner_id")
    public UUID getOwnerID() {
        return owner != null ? owner.getId() : null;
    }

    @JsonProperty("autoCompany_id")
    public UUID getAutoCompanyID() {
        return autoCompany != null ? autoCompany.getId() : null;
    }


    // Constructor------------------------------------
    public Car(String brand, String model, String color, int price, int year, int mileage, int numberOfOwners) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
        this.year = year;
        this.mileage = mileage;
        this.numberOfOwners = numberOfOwners;
    }
    //---------------------------------------------------
}
