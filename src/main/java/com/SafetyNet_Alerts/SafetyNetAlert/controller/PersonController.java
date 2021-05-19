package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.ChildAlertDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PersonInfoDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PhoneAlertDto;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.service.PersonService;
import com.SafetyNet_Alerts.SafetyNetAlert.service.URLsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Get all persons from data source")
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
    @Operation(summary = "Add person to data source")
    @PostMapping(value = "/person")
    public Person addPerson(@RequestBody(description = "Person to add",
            required = true, content = @Content(schema = @Schema(implementation = Person.class))) Person person) {
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
    @Operation(summary = "Update person in data source")
    @PutMapping(value = "/person")
    public Person updatePerson(@RequestParam @Parameter(description = "First name to update", required = true) String firstName,
                               @RequestParam @Parameter(description = "Last name to update", required = true) String lastName,
                               @RequestBody(description = "New data of person",
                                       required = true, content = @Content(schema = @Schema(implementation = Person.class))) Person person) {
        return personService.updatePerson(firstName, lastName, person);
    }

    /**
     * Delete person , name must pass in url
     *
     * @param firstName first param for person who will deleted
     * @param lastName  second param for person who will deleted
     * @return true if delete
     */
    @Operation(summary = "Delete person from data source")
    @DeleteMapping(value = "/person")
    public boolean deletePerson(@RequestParam @Parameter(description = "First name to update", required = true) String firstName,
                                @RequestParam @Parameter(description = "Last name to update", required = true) String lastName) {
        return personService.deletePerson(firstName, lastName);
    }

    //URLs

    @Operation(summary = "Get children with age and adult live with them by address")
    @GetMapping(value = "/childAlert")
    public ChildAlertDto getChildAlertDto(@RequestParam @Parameter(description = "Address to search by", required = true) String address) {
        return urLsService.getListOFChildByAddress(address);
    }

    @Operation(summary = "Get all phone numbers for people lives in address corresponding to firestation number")
    @GetMapping(value = "/phoneAlert")
    public PhoneAlertDto getPhoneNumberDto(@RequestParam @Parameter(description = "Firestation number", required = true) String firestation_number) {
        return urLsService.getPhoneNumber(firestation_number);
    }

    @Operation(summary = "Get information of specific person")
    @GetMapping(value = "/personInfo")
    public List<PersonInfoDto> getListOfPersonalInfo(@RequestParam @Parameter(description = "Person first name", required = true) String firstName,
                                                     @RequestParam @Parameter(description = "Person last name ", required = true) String lastName) {
        return urLsService.getListOfPersonInfo(firstName, lastName);
    }

    @Operation(summary = "Get all email addresses by city name")
    @GetMapping(value = "/communityEmail")
    public List<String> getListOfPersonalInfo(@RequestParam @Parameter(description = "City name to search by", required = true) String city) {
        return urLsService.getEmailAddressByCity(city);
    }
}
