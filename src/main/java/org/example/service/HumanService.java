package org.example.service;

import org.example.models.Car;
import org.example.models.Human;
import org.example.repository.CarRepository;
import org.example.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HumanService {

    @Autowired
    private final HumanRepository humanRepository;

    @Autowired
    private CarRepository carRepository;

    public HumanService(HumanRepository humanRepository, CarRepository carRepository){
        this.humanRepository = humanRepository;
    }

    public List<Human> getAllHumans(){
        return humanRepository.findAll();
    }

    public Human getHumanByID(UUID humanID){
        return humanRepository.findById(humanID).orElseThrow(()-> new IllegalStateException("Human not found with id " +humanID));
    }

    public List<Human> getByName(String name){
        return humanRepository.findByName(name);
    }

    public Human addNewHuman (Human human){
        if (human == null) {
            throw new IllegalArgumentException("Human object cannot be null");
        }
        return humanRepository.save(human);
    }

    public void deleteHumanById(UUID humanID){
        boolean exists = humanRepository.existsById(humanID);
        if(!exists){
            throw new IllegalStateException("Human with id " + humanID + " does not exist");
        }
        humanRepository.deleteById(humanID);
    }

    public Human updateHuman(UUID humanID, @RequestBody Map<String, Object> updates){
        Human human = getHumanByID(humanID);
        if(updates.containsKey("name")){
            human.setName((String) updates.get("name"));
        }
        if(updates.containsKey("money")){
            human.setMoney((int) updates.get("money"));
        }

        return humanRepository.save(human);
    }

    public Human updateAllHuman(UUID humanID, Human humanDetails){
        Human human = getHumanByID(humanID);

        human.setMoney(humanDetails.getMoney());
        human.setName(humanDetails.getName());

        return humanRepository.save(human);
    }

    public ResponseEntity<String> purchaseCar(UUID humanID, UUID carID){

        Human human = getHumanByID(humanID);
        Car car = carRepository.findById(carID).orElseThrow(() -> new IllegalArgumentException("Машина з ID " + carID + " не знайдена"));
            if(human.getMoney() >= car.getPrice()){
                human.setMoney(human.getMoney() - car.getPrice());
                human.buy(car);
                car.setOwner(human);
                car.setNumberOfOwners(car.getNumberOfOwners() + 1);
                humanRepository.save(human);
                carRepository.save(car);
                return ResponseEntity.ok("покупка здійснена!");
            }
           else {
                throw new IllegalArgumentException("Недостатньо коштів для покупки");
            }
    }

}