package org.example.controllers;

import org.example.models.AutoCompany;
import org.example.service.AutoCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/autoCompanies")
public class AutoCompanyController {

    @Autowired
    private final AutoCompanyService autoCompanyService;

    public AutoCompanyController( AutoCompanyService autoCompanyService){
        this.autoCompanyService = autoCompanyService;
    }

    @GetMapping
    public List<AutoCompany> getCars(){
        return autoCompanyService.getAutoCompany();
    }
}