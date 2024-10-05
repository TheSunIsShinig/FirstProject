package org.example;

import java.util.HashSet;
import java.util.ArrayList;


public class AutoCompany implements Trade {

    private HashSet<String> set = new HashSet<>();
    private ArrayList<Car> list = new ArrayList<>();

    @Override
    public void buy(Car x){
        list.add(x);
        set.add(x.brand);
        System.out.println("AutoCompany buy this car");
    }
    @Override
    public void sell(Car x){
        list.remove(x);
        if(!list.contains(x)){
            set.remove(x.brand);
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
            System.out.println("brand: " + x.brand);
            System.out.println("color: " + x.color);
            System.out.println("price: " +x.price);
            System.out.println("year: " +x.year);
            System.out.println();
            i++;
        }
    }

}
