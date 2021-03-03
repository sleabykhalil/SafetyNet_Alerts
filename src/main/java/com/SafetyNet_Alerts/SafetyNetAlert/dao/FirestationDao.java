package com.SafetyNet_Alerts.SafetyNetAlert.dao;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirestationDao extends JpaRepository<Firestation,Integer> {
}
