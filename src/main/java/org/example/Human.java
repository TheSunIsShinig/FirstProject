package org.example;

import com.google.gson.Gson;
import java.io.*;
import java.util.*;

public class Human implements Trade {

    List<Car> list = new ArrayList<>();
    LinkedList<String> historyList = new LinkedList<>();

    private String name;
    private int money;

    Human(String name, int money){
        this.name = name;
        this.money = money;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){return name;}

    public void setMoney(int money) {this.money = money;}
    public int getMoney(){return money;}


    @Override
    public void buy(Car x) {
        if(money >= x.getPrice()){
            list.add(x);
            money -= x.getPrice();
            historyList.add(x.getBrand() +": buy");
            System.out.println(name + " buy " + x.getBrand() + " for "+ x.getPrice() + " dollars");
        }
        else {
            System.out.println(name + " you don't have enough money to buy a car");
            System.out.println("You have " + money + " dollars");
        }
    }

    @Override
    public void sell(Car x) {
        list.remove(x);
        money += x.getPrice();
        historyList.add(x.getBrand() +": sell");
        System.out.println(name + " sell " + x.getBrand() + " for "+ x.getPrice() + " dollars");
    }

    public void getPurchaseHistory(){
        for(String x : historyList){
            System.out.println(x);
        }
    }
}
