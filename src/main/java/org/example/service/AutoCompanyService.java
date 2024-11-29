package org.example.service;

import org.example.models.AutoCompany;;
import org.example.models.Car;
import org.example.models.Human;
import org.example.repository.AutoCompaniesRepository;
import org.example.repository.CarRepository;
import org.example.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutoCompanyService {

    private final AutoCompaniesRepository autoCompaniesRepository;
    private final CarRepository carRepository;
    private final HumanRepository humanRepository;

    @Autowired
    public AutoCompanyService(AutoCompaniesRepository autoCompaniesRepository, CarRepository carRepository, HumanRepository humanRepository){
        this.autoCompaniesRepository = autoCompaniesRepository;
        this.carRepository = carRepository;
        this.humanRepository = humanRepository;
    }

    public AutoCompany getAutoCompanyByID(UUID autoCompanyID){
        return autoCompaniesRepository.findById(autoCompanyID).orElseThrow(()-> new IllegalStateException("AutoCompany not found with id " + autoCompanyID));
    }


    public List<AutoCompany> getAutoCompany(){
        return autoCompaniesRepository.findAll();
    }

    public AutoCompany addAutoCompany(AutoCompany autoCompany){
        return autoCompaniesRepository.save(autoCompany);
    }

    public void deleteAutoCompany(UUID autoCompanyID){
        boolean exists = autoCompaniesRepository.existsById(autoCompanyID);
        if(!exists){
            throw new IllegalArgumentException("AutoCompany with id " + autoCompanyID + " does not exist");
        }
        autoCompaniesRepository.deleteById(autoCompanyID);
    }

    public void buyCar(UUID autoCompanyID, UUID carID){
        AutoCompany autoCompany = getAutoCompanyByID(autoCompanyID);
        Car car = carRepository.findById(carID).orElseThrow(()-> new IllegalStateException("Car not found with id" + carID));

        if(car.getOwnerID() != null){
            Human human = humanRepository.findById(car.getOwnerID()).orElseThrow();
            autoCompany.buy(car);
            human.sell(car);
            car.setOwner(null);
            car.setAutoCompany(autoCompany);
            autoCompaniesRepository.save(autoCompany);
            humanRepository.save(human);
            carRepository.save(car);
        }
        else if(car.getAutoCompany() == null){
            autoCompany.buy(car);
            car.setAutoCompany(autoCompany);
            autoCompaniesRepository.save(autoCompany);
            carRepository.save(car);
        }
        else {
            throw new IllegalArgumentException("the car is the property of the car company");
        }
    }

}
