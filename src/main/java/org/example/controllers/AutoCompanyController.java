package org.example.controllers;

import org.example.models.AutoCompany;
import org.example.service.AutoCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/autoCompanies")
public class AutoCompanyController {

    private final AutoCompanyService autoCompanyService;

    @Autowired
    public AutoCompanyController( AutoCompanyService autoCompanyService){
        this.autoCompanyService = autoCompanyService;
    }

    @GetMapping("/get")
    public List<AutoCompany> getCars(){
        return autoCompanyService.getAutoCompany();
    }

    @PostMapping("/add")
    public ResponseEntity<AutoCompany> addAutoCompany(@RequestBody AutoCompany autoCompany){
        AutoCompany newAutoCompany = autoCompanyService.addAutoCompany(autoCompany);
        return ResponseEntity.ok(newAutoCompany);
    }

    @PostMapping("/{id_autoCompany}/buy/{id_car}")
    public ResponseEntity<String> autoCompanyBuyCar(@PathVariable("id_autoCompany") UUID autoCompanyID, @PathVariable("id_car") UUID carID){
        try {
            autoCompanyService.buyCar(autoCompanyID, carID);
            return ResponseEntity.ok("purchase is made!");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{id_autoCompany}/sell/{id_car}")
    public ResponseEntity<String> autoCompanySellCar(@PathVariable("id_autoCompany") UUID autoCompanyID, @PathVariable("id_car") UUID carID){
        try {
            autoCompanyService.sellCar(autoCompanyID, carID);
            return ResponseEntity.ok("the car is sold");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAutoCompany(@PathVariable("id") UUID autoCompanyID){
        AutoCompany autoCompany = autoCompanyService.getAutoCompanyByID(autoCompanyID);
        autoCompanyService.deleteAutoCompany(autoCompanyID);
        return ResponseEntity.ok("the company " + autoCompany.getCompanyName() + " was removed");
    }
}