package org.example.controllers;

import org.example.models.Car;
import org.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/cars")
public class CarController {

    private final CarService carService;


    @Autowired
    public CarController( CarService carService){
        this.carService = carService;
    }

    @GetMapping("/get")
    public List<Car> getCars(){return carService.getCars();}

    @GetMapping("/getByID/{id}")
    public Car getCarByID(@PathVariable("id") UUID carID){
        return carService.getCarByID(carID);
    }

    @GetMapping("/brand")
    public List<Car> getCarByBrand(@RequestParam String brand){
        return carService.carsBrand(brand);
    }

    @GetMapping("/model")
    public List<Car> getCarByModel(@RequestParam String model){
        return carService.carsModel(model);
    }

    @GetMapping("/year")
    public List<Car> getCarYear(@RequestParam int year){
        return carService.carsYear(year);
    }

    @GetMapping("/yearRange")
    public List<Car> carsYearFromTo(@RequestParam int from, @RequestParam int to){
        return carService.carsYearFromTo(from, to);
    }

    @GetMapping("/priceRange")
    public List<Car> carsPriceFromTo(@RequestParam int min, @RequestParam int max){
        return carService.carsPriceFromTo(min,max);
    }

    @PostMapping("/add")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        carService.addNewCar(car);
        return ResponseEntity.ok(car);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") UUID carID) {
        Car deletedCar = carService.getCarByID(carID);
        carService.deleteCarById(carID);
        return ResponseEntity.ok(deletedCar.getBrand() + " " + deletedCar.getModel() + " deleted");
    }

    @PutMapping("/updateAll/{id}")
    public ResponseEntity<Car> updateAllCar(@PathVariable UUID carID, @RequestBody Car carDetails){
        Car updatedCar = carService.updateAllCar(carID, carDetails);
        return ResponseEntity.ok(updatedCar);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") UUID carID, @RequestBody Map<String, Object> updates){
        Car updatedCar = carService.updateCar(carID, updates);
        return ResponseEntity.ok(updatedCar);
    }
}
