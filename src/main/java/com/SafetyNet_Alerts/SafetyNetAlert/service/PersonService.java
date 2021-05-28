package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.People;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.debug("create person object for update from full name {}=[first name] {}=[last name]", firstName, lastName);
        result = personDao.update(personBefore, personAfter);
        return result;
    }

    public boolean deletePerson(String firstName, String lastName) {
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        log.debug("create person object for delete from full name {}=[first name] {}=[last name]", firstName, lastName);
        boolean result = personDao.delete(person);
        return result;
    }

    public static boolean isPersonListValid(List<Person> personListToValid) {
        log.debug("Validate Person data");
        List<People> peopleValidationList = new ArrayList<>();
        for (Person person : personListToValid) {
            peopleValidationList.add(People.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName()).build());
        }
        for (People peopleEx : peopleValidationList) {
            int flag = 0;
            for (People peopleIn : peopleValidationList) {
                if (peopleIn.equals(peopleEx)) {
                    flag += 1;
                    if (flag == 2) {
                        log.error(String
                                .format("person with name %s %s already exist", peopleEx.getFirstName(), peopleEx.getLastName()));
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
