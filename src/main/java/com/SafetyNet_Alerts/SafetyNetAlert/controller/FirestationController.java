package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.PeopleWithAgeCatDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PeopleWithSpecificAgeDto;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FirestationService;
import com.SafetyNet_Alerts.SafetyNetAlert.service.URLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    @Autowired
    URLsService urLsService;

    /**
     * Get  for all Firestations
     *
     * @return list of firestation
     */
    @GetMapping(value = "/firestations")
    public List<Firestation> getAllFirestations() {

        return firestationService.getAllFirestation();
    }

    /**
     * Add new firestation , json string passed in body
     *
     * @param firestation data to add
     * @return firestation after add
     */
    @PostMapping(value = "/firestation")
    public Firestation addFirestation(@RequestBody Firestation firestation) {
        return firestationService.saveFirestation(firestation);
    }

    /**
     * Update firestation ,new data passed throw body
     *
     * @param address     firestation adress to be updated
     * @param firestation new data
     * @return firestation after update
     */
    @PutMapping(value = "/firestation")
    public Firestation uprateFirestation(@RequestParam String address,
                                         @RequestBody Firestation firestation) {

        return firestationService.updateFirestation(address, firestation);
    }

    /**
     * Delete firestation , address must pass in url
     *
     * @param address adress of firestation to be deleted
     * @return true if delete
     */
    @DeleteMapping(value = "/firestation")
    public boolean deletaFirestation(@RequestParam String address) {
        return firestationService.deleteFirestation(address);
    }


    //URLs

    @GetMapping(value = "/firestation")
    public PeopleWithAgeCatDto getPersonWithAgeCatDto(@RequestParam String station_number) {
        return urLsService.getListOfPersonCoveredByFireStation(station_number);
    }

    @GetMapping(value = "/fire")
    public PeopleWithSpecificAgeDto getPeopleWithSpecificAgeDto(@RequestParam String address) {
        return urLsService.getPeopleListServedByFirestationNumberByAddress(address);
    }
}
