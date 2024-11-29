package org.example.controllers;

import org.example.models.AutoCompany;
import org.example.models.Car;
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

    @Autowired
    private final AutoCompanyService autoCompanyService;

    public AutoCompanyController( AutoCompanyService autoCompanyService){
        this.autoCompanyService = autoCompanyService;
    }

    @GetMapping("/get")
    public List<AutoCompany> getCars(){
        return autoCompanyService.getAutoCompany();
    }

    @PostMapping("/add")
    public AutoCompany addAutoCompany(@RequestBody AutoCompany autoCompany){
        return autoCompanyService.addAutoCompany(autoCompany);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAutoCompany(@PathVariable("id") UUID autoCompanyID){
        autoCompanyService.deleteAutoCompany(autoCompanyID);
    }

    @PostMapping("/{id_autoCompany}/buy/{id_car}")
    private ResponseEntity<String> autoCompanyBuyCar(@PathVariable("id_autoCompany") UUID autoCompanyID, @PathVariable("id_car") UUID caraID){
        try {
            autoCompanyService.buyCar(autoCompanyID, caraID);
            return ResponseEntity.ok("покупка здійснена!");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}