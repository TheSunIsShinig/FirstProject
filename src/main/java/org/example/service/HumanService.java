package org.example.service;

import org.example.models.Car;
import org.example.models.Human;
import org.example.repository.CarRepository;
import org.example.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HumanService implements UserDetailsService {

    private final HumanRepository humanRepository;
    private final CarRepository carRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);

    @Autowired
    public HumanService(HumanRepository humanRepository, CarRepository carRepository){
        this.humanRepository = humanRepository;
        this.carRepository = carRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Human human = humanRepository.findByUsername(username);

        if(human == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new HumanDetails(human);
    }

    public List<Human> getAllHumans(){
        return humanRepository.findAll();
    }

    public Human getHumanByID(UUID humanID){
        return humanRepository.findById(humanID).orElseThrow(()-> new IllegalStateException("Human not found with id " +humanID));
    }

    public List<Human> getByName(String name){
        return humanRepository.findHumansByName(name);
    }

    public Human addNewHuman (Human human){
        if (human == null) {
            throw new IllegalArgumentException("Human object cannot be null");
        }

        human.setPassword(encoder.encode(human.getPassword()));
        return humanRepository.save(human);
    }

    public void deleteHumanById(UUID humanID){
        boolean exists = humanRepository.existsById(humanID);
        if(!exists){
            throw new IllegalArgumentException("Human with id " + humanID + " does not exist");
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

    public void purchaseCar(UUID humanID, UUID carID){
        Human human = getHumanByID(humanID);
        Car car = carRepository.findById(carID).orElseThrow(() -> new IllegalArgumentException("Car no found with this id: " + carID));

        if(car.getOwnerID() != null){
            throw new IllegalArgumentException("This car already has an owner");
        }

        human.buy(car);
        car.setOwner(human);
        car.setAutoCompany(null);
        car.setNumberOfOwners(car.getNumberOfOwners()+1);
        humanRepository.save(human);
        carRepository.save(car);
    }

    public void sellCar(UUID humanID, UUID carID){
        Human human = getHumanByID(humanID);
        Car car = carRepository.findById(carID).orElseThrow(() -> new IllegalArgumentException("Car no found with this id: " + carID));

        if(car.getOwner() != human){
            throw new IllegalArgumentException("this car does not belong to this person");
        }

        human.sell(car);
        car.setOwner(null);
        humanRepository.save(human);
        carRepository.save(car);
    }

    public String verify(Human human) {
        UserDetails userDetails = loadUserByUsername(human.getUsername());

        if (encoder.matches(human.getPassword(), userDetails.getPassword()))
            return "Success"; // Пароль правильний

        return "Fail"; // Пароль неправильний
    }
}