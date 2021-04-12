package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    FirestationRepository firestationRepository;

    /**
     * Get  for all Firestations
     *
     * @return list of firestation
     */
    @GetMapping(value = "/firestations")
    public List<Firestation> getAllFirestations() {
        return firestationRepository.findAll();
    }

}
