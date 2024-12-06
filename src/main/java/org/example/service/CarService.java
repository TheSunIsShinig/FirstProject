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

    private final CarRepository carRepository;
    private final HumanRepository humanRepository;

    @Autowired
    public CarService(CarRepository carRepository, HumanRepository humanRepository){
        this.carRepository = carRepository;
        this.humanRepository = humanRepository;
    }


    public List<Car> getCars(){
        return carRepository.findAll();
    }

    public Car getCarByID(UUID carID){
        return carRepository.findById(carID).orElseThrow(()-> new IllegalStateException("Car not found with id" + carID));
    }

    public List<Car> carsBrand(String brand){
        return carRepository.findByBrand(brand);
    }

    public List<Car> carsPriceFromTo(int min, int max){
        if (min < 0) {throw new IllegalArgumentException("Minimum price cannot be negative.");}
        if (max < 0) {throw new IllegalArgumentException("Maximum price cannot be negative.");}
        if (min > max) {throw new IllegalArgumentException("Minimum price cannot be greater than maximum price.");}

        return carRepository.findCarsByPriceRange(min, max);
    }

    public List<Car> carsYearFromTo(int min, int max){
        if (min < 0) {throw new IllegalArgumentException("Minimum year cannot be negative.");}
        if (max < 0) {throw new IllegalArgumentException("Maximum year cannot be negative.");}
        if (min > max) {throw new IllegalArgumentException("Minimum year cannot be greater than maximum year.");}

        return carRepository.findCarsByYearRange(min, max);
    }

    public List<Car> carsModel(String model){
        return carRepository.findByModel(model);
    }

    public List<Car> carsYear(int year){
        return carRepository.findByYear(year);
    }

    public Car addNewCar(Car car) {
        if(car == null){
            throw new IllegalArgumentException("Car object cannot be null");
        }
        return carRepository.save(car);
    }

    public void deleteCarById(UUID carID){
        Car car = getCarByID(carID);
        if(car.getOwner() != null){
            Human human = humanRepository.findById(car.getOwnerID()).orElseThrow(() -> new IllegalArgumentException("User with ID " + car.getOwnerID() + " not found"));
            human.setMoney(human.getMoney() + car.getPrice());
            humanRepository.save(human);
        }
        carRepository.deleteById(carID);
    }

    @Transactional
    public Car updateAllCar(UUID carID, Car carDetails){

        Car car = getCarByID(carID);

        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setColor(carDetails.getColor());
        car.setPrice(carDetails.getPrice());
        car.setYear(carDetails.getYear());
        car.setMileage(carDetails.getMileage());
        car.setNumberOfOwners(carDetails.getNumberOfOwners());

        return carRepository.save(car);
    }

    public Car updateCar(UUID carID, @RequestBody Map<String, Object> updates){
        Car car = getCarByID(carID);

        if(updates.containsKey("Brand")){car.setBrand((String) updates.get("Brand"));}
        if(updates.containsKey("Model")){car.setModel((String) updates.get("Model"));}
        if(updates.containsKey("Color")){car.setColor((String) updates.get("Color"));}
        if(updates.containsKey("Price")){car.setPrice((int) updates.get("Price"));}
        if(updates.containsKey("Year")){car.setYear((int) updates.get("Year"));}
        if(updates.containsKey("Mileage")){car.setMileage((int) updates.get("Mileage"));}
        if(updates.containsKey("NumberOfOwners")){car.setNumberOfOwners((int) updates.get("NumberOfOwners"));}

        return carRepository.save(car);
    }

}
