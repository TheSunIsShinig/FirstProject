package org.example;

import com.google.gson.Gson;
import java.io.*;

public class Ship {


    public void toJson(Car x, String nameFile){ // для запису даних у json
        //Використовуємо Gson для серіалізації об'єкта в JSON
        Gson gson = new Gson();
        String json = gson.toJson(x);

        String filePath = nameFile + ".json";


        try(FileWriter fileWriter = new FileWriter(filePath)){

            fileWriter.write(json);
            System.out.println("JSON-документ створено: " + filePath);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public Car fromJson(String filePath) { //для читання даних з json файлу

        //Використовуємо Gson для десеріалізації об'єкта в JSON
        Gson gson = new Gson();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Десеріалізуємо JSON у об'єкт Car
            System.out.println("JSON-документ прочитан");
            return gson.fromJson(reader, Car.class);
        } catch (IOException e) {
            System.err.println("Помилка читання з файлу: " + e.getMessage());
            return null;
        }

    }

    public void deleteFile(String filePath){
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {System.out.println("Файл успішно видалено: " + filePath);}
            else {System.out.println("Не вдалося видалити файл: " + filePath);}
        }
        else {System.out.println("Файл не знайдено: " + filePath);}
    }
}
