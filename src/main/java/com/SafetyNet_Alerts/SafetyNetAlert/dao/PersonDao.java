package com.SafetyNet_Alerts.SafetyNetAlert.dao;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository<Person,Integer> {
}
