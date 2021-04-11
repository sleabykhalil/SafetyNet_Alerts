package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.websocket.server.PathParam;
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

    /**
     * Add new person json string passed in body
     *
     * @param person to add
     * @return person after add
     */
    @PostMapping(value = "/Person")
    public Person addPerson(@RequestBody Person person) {
        personRepository.savePerson(person);
        return person;
    }

    /**
     * Update person passed in body
     *
     * @param firstName passed in url
     * @param lastName  passed in url
     * @param person    new information passed in body
     * @return
     */
    @PutMapping(value = "/Person")
    public Person updatePerson(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestBody Person person) {
        Person personBefore = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        personRepository.updatePerson(personBefore, person);
        return person;
    }

    /**
     * Delete person , name must pass in url
     *
     * @param firstName
     * @param lastName
     * @return
     */
    @DeleteMapping(value = "/Person")
    public Person deletePerson(@RequestParam String firstName,
                               @RequestParam String lastName) {
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        personRepository.deletePerson(person);
        return person;
    }
}
