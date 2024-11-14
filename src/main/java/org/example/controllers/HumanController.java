package org.example.controllers;

import org.example.models.Human;
import org.example.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/humans")
public class HumanController {

    @Autowired
    private final HumanService humanService;

    public HumanController( HumanService humanService){
        this.humanService = humanService;
    }


    @GetMapping("/get")
    public List<Human> getHumans(){
        return humanService.getAllHumans();
    }

    @GetMapping("/getByID/{id}")
    public Human getHumanByID(@PathVariable("id") UUID humanID){
        return humanService.getHumanByID(humanID);
    }

    @GetMapping("/name/{name}")
    public List<Human> getHumanName(@PathVariable("name") String name){
        return humanService.getByName(name);
    }

    @PostMapping("/add")
    public Human addNewHuman (@RequestBody Human human){
        return humanService.addNewHuman(human);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHuman(@PathVariable UUID humanID) {
        humanService.deleteHumanById(humanID);  // Викликаємо сервіс для видалення машини

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Human> updateHuman(@PathVariable UUID humanID, @RequestBody Map<String, Object> updates){
        Human updatedHuman = humanService.updateHuman(humanID,updates);
        return ResponseEntity.ok(updatedHuman);
    }

    @PutMapping("/updateAll/{id}")
    public ResponseEntity<Human> updateHuman(@PathVariable UUID humanID, @RequestBody Human human){
        Human updatedHuman = humanService.updateAllHuman(humanID,human);
        return ResponseEntity.ok(updatedHuman);
    }

    @PostMapping("/{humanID}/buy/{carID}")
    public ResponseEntity<String> buyCar(@PathVariable("humanID") UUID humanID, @PathVariable("carID") UUID carID){
        try {
            humanService.purchaseCar(humanID, carID);
            return ResponseEntity.ok("Покупка здійснена");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}