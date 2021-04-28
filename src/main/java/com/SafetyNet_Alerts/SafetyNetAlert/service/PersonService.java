package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonService {
    @Autowired
    PersonDaoImpl personDao;

    public List<Person> getAllPerson() {
        return personDao.findAll();
    }

    public Person savePerson(Person person) {
        return personDao.create(person);
    }

    public Person updatePerson(String firstName, String lastName, Person personAfter) {
        Person result;
        Person personBefore = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        result = personDao.update(personBefore, personAfter);
        return result;
    }

    public boolean deletePerson(String firstName, String lastName) {
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        boolean result = personDao.delete(person);
        return result;
    }


}
