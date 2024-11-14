package org.example.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;


public class JsonParser<T> {

    private final Class<T> type;

    public JsonParser(Class<T> type){
        this.type = type;
    }

    public void toJson(T x, String nameFile) {

        Gson gson = new Gson();
        String json = gson.toJson(x);

        String filePath = nameFile + ".json";

        try (FileWriter fileWriter = new FileWriter(filePath)) {

            fileWriter.write(json);
            System.out.println("JSON-документ створено: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T fromJson(String filePath) {
        Gson gson = new Gson();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("JSON-документ прочитан");
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            return  gson.fromJson(json.getAsJsonObject(type.getSimpleName()), type);
        } catch (IOException e) {
            System.err.println("Помилка читання з файлу: " + e.getMessage());
            return null;
        }
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Файл успішно видалено: " + filePath);
            } else {
                System.out.println("Не вдалося видалити файл: " + filePath);
            }
        } else {
            System.out.println("Файл не знайдено: " + filePath);
        }
    }
}
