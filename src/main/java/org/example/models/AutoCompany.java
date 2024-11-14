package org.example.models;

import java.util.HashSet;
import java.util.ArrayList;


public class AutoCompany implements Trade {

    private final HashSet<String> brandSet = new HashSet<>();
    private final ArrayList<Car> autoCompanyCars = new ArrayList<>();

    @Override
    public void buy(Car car){
        performPurchase(car);
    }

    @Override
    public void sell(Car car){
        saleCar(car);
    }

    public void brandHave(){
        int i = 1;
        System.out.println("brand we have:");
        for(String x : brandSet){
            System.out.println(i + ")");
            System.out.println(x);
            i++;
        }
    }

    public void allAutoHave(){
        int i = 1;
        System.out.println("Auto we have:");
        for(Car x : autoCompanyCars){
            System.out.println(i + ")");
            System.out.println("brand: " + x.getBrand());
            System.out.println("color: " + x.getColor());
            System.out.println("price: " +x.getPrice());
            System.out.println("year: " +x.getYear());
            System.out.println();
            i++;
        }
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
