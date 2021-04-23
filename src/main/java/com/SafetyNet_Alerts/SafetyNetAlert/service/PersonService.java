package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Person savePerson(Person person) {
        return personRepository.savePerson(person);
    }

    public Person updatePerson(String firstName, String lastName, Person personAfter) {
        Person result;
        Person personBefore = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        result = personRepository.updatePerson(personBefore, personAfter);
        return result;
    }

    public boolean deletePerson(String firstName, String lastName) {
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        boolean result = personRepository.deletePerson(person);
        return result;
    }


}
