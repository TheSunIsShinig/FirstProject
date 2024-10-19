package org.example;
import java.io.*;


class Main {
    public static void main(String[] args) throws IOException {

        AutoCompany autoCompany = new AutoCompany();
        Human human = new Human("Alex", 34300);
        Human human2 = new Human("Viktor", 10);

        Car car1 = new Car("f","fd","red",12,3,4,1);
        Car car2 = new Car("f","d","fade orange",2,3,8,1);
        Car car3 = new Car("g","fd","red",12,3,4,1);

        JsonParser<Car> parserCar = new JsonParser<>(Car.class);
        JsonParser<Human> parserHuman = new JsonParser<>(Human.class);

        Car car4 = parserCar.fromJson("object.json");
        Human human3 = parserHuman.fromJson("object.json");

        car4.info();
        System.out.println();
        human3.info();

    }

}