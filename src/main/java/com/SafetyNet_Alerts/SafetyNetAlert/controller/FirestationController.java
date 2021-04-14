package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    Services services;

    /**
     * Get  for all Firestations
     *
     * @return list of firestation
     */
    @GetMapping(value = "/firestations")
    public List<Firestation> getAllFirestations() {

        return firestationRepository.findAll();
    }

    /**
     * Add new firestation , json string passed in body
     *
     * @param firestation data to add
     * @return firestation after add
     */
    @PostMapping(value = "/firestation")
    public Firestation addFirestation(@RequestBody Firestation firestation) {
        firestationRepository.saveFirestation(firestation);
        return firestation;
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
        Firestation firestationBefore = Firestation.builder()
                .address(address)
                .build();
        firestationRepository.updateFirestation(firestationBefore, firestation);
        return firestation;
    }

    /**
     * Delete firestation , address must pass in url
     *
     * @param address adress of firestation to be deleted
     * @return true if delete
     */
    @DeleteMapping(value = "/firestation")
    public boolean deletaFirestation(@RequestParam String address) {
        Firestation firestation = Firestation.builder()
                .address(address)
                .build();
        boolean result = firestationRepository.deleteFirestation(firestation);
        return result;
    }

}
