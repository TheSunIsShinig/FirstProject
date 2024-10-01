package org.example;

public class Car {

    public String brand;
    public String color;
    public int price;
    public int year;

    //інкапсуляція-----------------------------------
    private boolean isEngineActive = false;
    //-----------------------------------------------

    // Конструктор------------------------------------
    Car(String brand, String color, int price, int year){
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.year = year;
    }
    //------------------------------------------------

    void beep(){System.out.println(brand + " Make 'beep beep'");}

    void start(){
        if(isEngineActive){
            System.out.println("the engine is already running");
        }
        else {
            System.out.println(brand + " Started the engine");
            isEngineActive = true;
        }
    }

    void info(){
        System.out.println("Brand: " + brand);
        System.out.println("Color: " + color);
        if (price == 0){System.out.println("Price: free");}
        else {System.out.println("Price: " + price);}
        System.out.println("Year: " + year);
    }

    void brandInfo (){System.out.println(brand);}

}
