package org.example;

import java.util.*;

public class Human implements Trade {

    List<Car> list = new ArrayList<>();
    LinkedList<String> historyList = new LinkedList<>();

    String name;
    int money;

    Human(String name, int money){
        this.name = name;
        this.money = money;
    }


    @Override
    public void buy(Car x) {
        if(money >= x.price){
            list.add(x);
            money -= x.price;
            historyList.add(x.brand +": buy");
            System.out.println(name + " buy " + x.brand + " for "+ x.price + " dollars");
        }
        else {
            System.out.println(name + " you don't have enough money to buy a car");
            System.out.println("You have " + money + " dollars");
        }
    }

    @Override
    public void sell(Car x) {
        list.remove(x);
        money += x.price;
        historyList.add(x.brand +": sell");
    }

    public void money(){
        System.out.println(money);
    }


    public void purchaseHistory(){
        for(String x : historyList){
            System.out.println(x);
        }
    }
}
