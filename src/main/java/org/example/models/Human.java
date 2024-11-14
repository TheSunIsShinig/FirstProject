package org.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


@NoArgsConstructor
@Entity
@Table(name = "human")
public class Human implements Trade {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )

    @Getter
    @JsonProperty("human_id")
    private UUID id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Car> humanCars = new ArrayList<>();

    @Column(name = "purchase")
    private LinkedList<String> purchaseHistory = new LinkedList<>();

    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private int money;

    public Human(String name, int money){
        this.name = name;
        this.money = money;
    }

    @Override
    public void buy(Car car) {
        if(hasEnoughMoney(car)){
            performPurchase(car);
        }
        else {
            notifyInsufficientFunds();
        }
    }

    @Override
    public void sell(Car car) {
        saleCar(car);
    }

    private void addCar(Car car){
        humanCars.add(car);}
    private void removeCar(Car car){
        humanCars.remove(car);}
    private void addPurchaseHistory(String brand){
        purchaseHistory.add(brand);}

    private boolean hasEnoughMoney(Car car){
        return money >= car.getPrice();
    }

    private void updateMoney(Car car, boolean isBuying){
        if(isBuying){money -= car.getPrice();}
        else {money += car.getPrice();}
    }

    private void updateCarOwnerCount(Car car) {
        car.setNumberOfOwners(car.getNumberOfOwners() + 1);
    }

    private void performPurchase(Car car){
        addCar(car);
        updateCarOwnerCount(car);
        addPurchaseHistory(car.getBrand() +": buy");

        updateMoney(car, true);
    }

    private void saleCar(Car car){
        removeCar(car);
        addPurchaseHistory(car.getBrand() +": sell");
        updateMoney(car, false);
    }

    private void notifyInsufficientFunds(){
        System.out.println(name + " you don't have enough money to buy a car");
        System.out.println("You have " + money + " dollars");
    }
}