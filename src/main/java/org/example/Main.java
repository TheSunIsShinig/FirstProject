package org.example;
import java.io.*;


class Main {
    public static void main(String[] args) throws IOException {

        AutoCompany autoCompany = new AutoCompany();
        Human human = new Human("Alex", 34300);
        Human human2 = new Human("Viktor", 10);

        //Створенні машини
        Car car1 = new Car("BMW", "BLACK", 14000, 2020);
        Car car2 = new Car("BMW", "BLACK", 14000, 2020);
        Car car3 = new Car("MRS", "BLACK", 14000, 2020);
        Car car4 = new Car("VW", "BLACK", 14000, 2020);

        JsonParser<Car> jparseCar = new JsonParser<>(Car.class);
        JsonParser<Human> jparseHuman = new JsonParser<>(Human.class);

        jparseHuman.toJson(human2, "human2");

        human = jparseHuman.fromJson("human2.json");

        System.out.println(human.getName());


    }

}