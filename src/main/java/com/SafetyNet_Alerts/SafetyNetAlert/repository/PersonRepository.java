package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class PersonRepository {
    public static List<Person> personList = new ArrayList<>();

    @Autowired
    private Services services;
    @Autowired
    private JsonFileRW jsonFileRW;

    /**
     * Data initialize get all person from json file
     */
    @PostConstruct
    private void setup() {
        personList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getPersons();
    }


    public List<Person> findAll() {
        List<Person> result;
        result = personList;
        return result;
    }

    /**
     * Add person to person list
     *
     * @param person to add
     * @return person to add
     */
    public Person savePerson(Person person) {
        personList.add(person);
        services.saveToJsonFile();
        return person;
    }

    /**
     * Update person in person list , first and last name cannot be modified , use add new person insted
     *
     * @param personBefore data before update
     * @param personAfter  data after update
     * @return person after update
     */
    public Person updatePerson(Person personBefore, Person personAfter) {
        personList.add(personAfter);
        personList.removeIf(personToDelete -> personToDelete.getFirstName().equals(personBefore.getFirstName()) &&
                personToDelete.getLastName().equals(personBefore.getLastName()));
        services.saveToJsonFile();
        return personAfter;
    }

    /**
     * Delete person from person list
     *
     * @param person
     * @return true in success
     */
    public boolean deletePerson(Person person) {
        boolean result = personList.removeIf(personToDelete -> personToDelete.getFirstName().equals(person.getFirstName()) &&
                personToDelete.getLastName().equals(person.getLastName()));
        services.saveToJsonFile();
        return result;
    }
}
