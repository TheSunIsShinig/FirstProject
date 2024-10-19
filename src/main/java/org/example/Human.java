package org.example;

import java.util.*;

public class Human implements Trade {

    private ArrayList<Car> list = new ArrayList<>();
    private LinkedList<String> historyList = new LinkedList<>();

    private String name;
    private int money;

    Human(String name, int money){
        this.name = name;
        this.money = money;
    }

    private void addCar(Car car){list.add(car);}
    private void removeCar(Car car){list.remove(car);}
    private void addPurchaseHistory(String brand){historyList.add(brand);}
    public void getPurchaseHistory(){
        for(String x : historyList){
            System.out.println(x);
        }
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public int getMoney(){return money;}
    public void setMoney(int money) {this.money = money;}

    @Override
    public void buy(Car x) {
        if(money >= x.getPrice()){
            addCar(x);
            x.setNumberOfOwners(x.getNumberOfOwners() + 1);
            money -= x.getPrice();
            addPurchaseHistory(x.getBrand() +": buy");
            System.out.println(name + " buy " + x.getBrand() + " for "+ x.getPrice() + " dollars");
        }
        else {
            System.out.println(name + " you don't have enough money to buy a car");
            System.out.println("You have " + money + " dollars");
        }
    }

    @Override
    public void sell(Car x) {
        removeCar(x);
        money += x.getPrice();
        addPurchaseHistory(x.getBrand() +": sell");
        System.out.println(name + " sell " + x.getBrand() + " for "+ x.getPrice() + " dollars");
    }

    public void info(){
        System.out.println("Name " + name);
        System.out.println("Money " + money);
        System.out.println();
        System.out.println("Car: ");
        for (Car x: list){
            System.out.println("Brand: " + x.getBrand());
            System.out.println("Model: " + x.getModel());
            System.out.println("Color: " + x.getColor());
            if (x.getPrice() == 0){System.out.println("Price: free");}
            else {System.out.println("Price: " + x.getPrice());}
            System.out.println("Year: " + x.getYear());
            System.out.println();
        }
    }
}