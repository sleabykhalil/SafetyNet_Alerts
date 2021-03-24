package com.SafetyNet_Alerts.SafetyNetAlert.web.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.FirestationDao;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    FirestationDao firestationDao;

    @GetMapping(value = "/Firestations")
    public List<Firestation> getAllFirestations(){
        return firestationDao.findAll();
    }

}
