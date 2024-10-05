package org.example;
import java.io.*;

class Main {

    public static void main(String[] args) throws IOException {

        AutoCompany autoCompany = new AutoCompany();
        Human human = new Human("Alex", 34300);

        //Створенні машини
        Car car1 = new Car("BMW", "BLACK", 14000, 2020);
        Car car2 = new Car("BMW", "BLACK", 14000, 2020);
        Car car3 = new Car("MRS", "BLACK", 14000, 2020);
        Car car4 = new Car("VW", "BLACK", 14000, 2020);


        Ship ship = new Ship();

        ship.toJson(car1,"BMW");
        autoCompany.buy(ship.fromJson("BMW.json"));
        autoCompany.autoHave();
        ship.deleteFile("BMW.json");





















    }

}