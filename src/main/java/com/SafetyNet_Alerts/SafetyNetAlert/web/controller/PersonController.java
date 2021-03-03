package com.SafetyNet_Alerts.SafetyNetAlert.web.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.PersonDao;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonDao personDao;

    @GetMapping(value = "/Persons")
    public List<Person> getAllPersons(){
        return personDao.findAll();
    }
}
