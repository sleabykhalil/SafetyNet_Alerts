package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    /**
     * Get  for all persons
     *
     * @return list of person
     */
    @GetMapping(value = "/Persons")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping(value = "/Person")
    public Person addPerson(@RequestBody Person person) {
        personRepository.savePerson(person);
        return person;
    }
}
