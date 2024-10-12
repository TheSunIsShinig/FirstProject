package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayList;


public class AutoCompany implements Trade {

    private final HashSet<String> set = new HashSet<>();
    private final ArrayList<Car> list = new ArrayList<>();

    @Override
    public void buy(Car x){
        list.add(x);
        set.add(x.getBrand());
        System.out.println("AutoCompany buy this car");
    }
    @Override
    public void sell(Car x){
        list.remove(x);
        if(!list.contains(x)){
            set.remove(x.getBrand());
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
