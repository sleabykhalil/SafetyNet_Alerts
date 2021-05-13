package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.ChildAlertDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PersonInfoDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PhoneAlertDto;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.service.PersonService;
import com.SafetyNet_Alerts.SafetyNetAlert.service.URLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @Autowired
    URLsService urLsService;

    /**
     * Get  for all persons
     *
     * @return list of person
     */
    @GetMapping(value = "/persons")
    public List<Person> getAllPersons() {
        return personService.getAllPerson();
    }

    /**
     * Add new person, json string passed in body
     *
     * @param person to add
     * @return person after add
     */
    @PostMapping(value = "/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    /**
     * Update person passed in body
     *
     * @param firstName passed in url
     * @param lastName  passed in url
     * @param person    new information passed in body
     * @return person after update
     */
    @PutMapping(value = "/person")
    public Person updatePerson(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestBody Person person) {
        return personService.updatePerson(firstName, lastName, person);
    }

    /**
     * Delete person , name must pass in url
     *
     * @param firstName first param for person who will deleted
     * @param lastName  second param for person who will deleted
     * @return true if delete
     */
    @DeleteMapping(value = "/person")
    public boolean deletePerson(@RequestParam String firstName,
                                @RequestParam String lastName) {
        return personService.deletePerson(firstName, lastName);
    }

    //URLs

    @GetMapping(value = "/childAlert")
    public ChildAlertDto getChildAlertDto(@RequestParam String address) {
        return urLsService.getListOFChildByAddress(address);
    }

    @GetMapping(value = "/phoneAlert")
    public PhoneAlertDto getPhoneNumberDto(@RequestParam String firestation_number) {
        return urLsService.getPhoneNumber(firestation_number);
    }

    @GetMapping(value = "/personInfo")
    public List<PersonInfoDto> getListOfPersonalInfo(@RequestParam String firstName,
                                                     @RequestParam String lastName) {
        return urLsService.getListOfPersonInfo(firstName, lastName);
    }

    @GetMapping(value = "/communityEmail")
    public List<String> getListOfPersonalInfo(@RequestParam String city) {
        return urLsService.getEmailAddressByCity(city);
    }
}
