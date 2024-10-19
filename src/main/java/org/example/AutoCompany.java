package org.example;

import java.util.HashSet;
import java.util.ArrayList;


public class AutoCompany implements Trade {

    private final HashSet<String> set = new HashSet<>();
    private final ArrayList<Car> list = new ArrayList<>();

    private void addBrand(String brand){set.add(brand);}
    private void removeBrand(String brand){set.remove(brand);}
    private void addCar(Car car){list.add(car);}
    private void removeCar(Car car){list.remove(car);}

    private boolean brandContains(String brand){
        for(Car x: list){
            if(x.getBrand().equals(brand)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void buy(Car x){
        addCar(x);
        addBrand(x.getBrand());
        System.out.println("AutoCompany buy this car");
    }
    @Override
    public void sell(Car x){
        removeCar(x);
        if(brandContains(x.getBrand())){
            removeBrand(x.getBrand());
        }
        System.out.println("AutoCompany sold this car");
        x.info();
    }

    public void brandHave(){
        int i = 1;
        System.out.println("brand we have:");
        for(String x : set){
            System.out.println(i + ")");
            System.out.println(x);
            i++;
        }

    }

    public void autoHave(){
        int i = 1;
        System.out.println("Auto we have:");
        for(Car x : list){
            System.out.println(i + ")");
            System.out.println("brand: " + x.getBrand());
            System.out.println("color: " + x.getColor());
            System.out.println("price: " +x.getPrice());
            System.out.println("year: " +x.getYear());
            System.out.println();
            i++;
        }
    }
}
