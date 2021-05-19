package com.SafetyNet_Alerts.SafetyNetAlert.dao;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDao {
    List<Person> findAll();

    Person create(Person person);

    Person update(Person personBefore, Person personAfter);

    boolean delete(Person person);
}
