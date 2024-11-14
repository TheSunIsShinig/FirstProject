package org.example.service;

import org.example.models.Car;
import org.example.models.Human;
import org.example.repository.CarRepository;
import org.example.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private final CarRepository carRepository;
    @Autowired
    private final HumanRepository humanRepository;

    public CarService(CarRepository carRepository, HumanRepository humanRepository){
        this.carRepository = carRepository;
        this.humanRepository = humanRepository;
    }


    public List<Car> getCars(){
        return carRepository.findAll();
    }

    public Car carID(UUID carID){
        return carRepository.findById(carID).orElseThrow(()-> new IllegalStateException("Car not found with id" + carID));
    }

    public List<Car> carsBrand(String brand){
        return carRepository.findByBrand(brand);
    }

    public List<Car> carsPriceFromTo(int from, int to){
        return carRepository.findCarsByPriceRange(from, to);
    }

    public List<Car> carsYearFromTo(int from, int to){
        return carRepository.findCarsByYearRange(from, to);
    }

    public List<Car> carsModel(String model){
        return carRepository.findByModel(model);
    }

    public List<Car> carsYear(int year){
        return carRepository.findByYear(year);
    }


    public UUID CarOwner(UUID carID){
        Car car = carID(carID);
        return car.getOwnerId();
    }

    public Car addNewCar(Car car) {
        return carRepository.save(car);
    }

    //?? Питання щодо SOLID. чи тут все правильно?
    public void deleteCarById(UUID carID){
        Car car = carID(carID);
        if(car.getId() != null){
            Human human = humanRepository.findById(car.getOwnerId()).orElseThrow(() -> new IllegalArgumentException("Користувач з ID " + car.getOwnerId() + " не знайдений"));
            human.setMoney(human.getMoney() + car.getPrice());
            humanRepository.save(human);
        }
        carRepository.deleteById(carID);
    }

    // Це якщо треба поміняти всі дані про машину
    @Transactional
    public Car updateAllCar(UUID carID, Car carDetails){

        Car car = carID(carID);

        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setColor(carDetails.getColor());
        car.setPrice(carDetails.getPrice());
        car.setYear(carDetails.getYear());
        car.setMileage(carDetails.getMileage());
        car.setNumberOfOwners(carDetails.getNumberOfOwners());

        return carRepository.save(car);
    }

    //Це для того щоб змінити поля які потрібно
    public Car updateCar(UUID carID, @RequestBody Map<String, Object> updates){
        Car car = carID(carID);

        if(updates.containsKey("Brand")){car.setBrand((String) updates.get("Brand"));}
        if(updates.containsKey("Model")){car.setModel((String) updates.get("Model"));}
        if(updates.containsKey("Color")){car.setColor((String) updates.get("Color"));}
        if(updates.containsKey("Price")){car.setPrice((int) updates.get("Price"));}
        if(updates.containsKey("Year")){car.setPrice((int) updates.get("Year"));}
        if(updates.containsKey("Mileage")){car.setPrice((int) updates.get("Mileage"));}
        if(updates.containsKey("NumberOfOwners")){car.setPrice((int) updates.get("NumberOfOwners"));}

        return carRepository.save(car);
    }

}
