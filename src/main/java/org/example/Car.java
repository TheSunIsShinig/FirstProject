package org.example;

public class Car {

    private String brand;
    private String model;
    private String color;
    private int price;
    private int year;
    private int mileage;
    private int numberOfOwners;

    // Constructor------------------------------------
    Car(String brand, String model, String color, int price, int year, int mileage, int numberOfOwners){
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
        this.year = year;
        this.mileage = mileage;
        this.numberOfOwners = numberOfOwners;
    }
    //---------------------------------------------------

    //get,set--------------------------------------------
    public String getBrand(){ return brand;}
    public void setBrand(String brand){this.brand = brand;}

    public String getModel(){return model;}
    public void setModel(String model){this.model = model;}

    public String getColor(){ return color;}
    public void setColor(String color){this.color = color;}

    public int getPrice(){return price;}
    public void setPrice(int price){this.price = price;}

    public int getYear(){return year;}
    public void setYear(int year){this.year = year;}

    public int getNumberOfOwners(){return numberOfOwners;}
    public void setNumberOfOwners(int numberOfOwners){this.numberOfOwners = numberOfOwners;}

    int getMileage(){return mileage;}
    void setMileage(int mileage){this.mileage = mileage;}
    //-------------------------------------------------

    void info(){
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Color: " + color);
        if (price == 0){System.out.println("Price: free");}
        else {System.out.println("Price: " + price);}
        System.out.println("Year: " + year);
    }

    void brandInfo (){System.out.println(brand);}

}
