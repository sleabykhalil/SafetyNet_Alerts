package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    public static List<Person> personList = new ArrayList<>();

    @Autowired
    private Services services;
    @Autowired
    private JsonFileRW jsonFileRW;

    /**
     * Constructor and Data initialize get all person from json file
     *
     * @param services
     * @param jsonFileRW
     */
    public PersonRepository(final Services services, final JsonFileRW jsonFileRW) {
        this.services = services;
        this.jsonFileRW = jsonFileRW;
        personList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getPersons();
    }

    /**
     * Get list of Persons
     *
     * @return list of persons
     */
    public List<Person> findAll() {
        List<Person> result;
        result = personList;
        return result;
    }

    /**
     * Add person to person list , and create data json file
     *
     * @param person to add
     * @return person added
     */
    public Person savePerson(Person person) {
        personList.add(person);
        services.saveToJsonFile();
        return person;
    }

    /**
     * Update person in person list , first and last name cannot be modified
     * and create data json file
     *
     * @param personBefore data before update
     * @param personAfter  data after update
     * @return person after update
     */
    public Person updatePerson(Person personBefore, Person personAfter) {
        for (Person person : personList) {
            if ((personBefore.getFirstName().equals(person.getFirstName())) &&
                    (personBefore.getLastName().equals(person.getLastName()))) {
                personList.set(personList.indexOf(person), personAfter);
                break;
            }
        }
        services.saveToJsonFile();
        return personAfter;
    }

    /**
     * Delete person from person list  and create data json file
     *
     * @param person to be deleted
     * @return true if success
     */
    public boolean deletePerson(Person person) {
        boolean result = personList.removeIf(personToDelete ->
                personToDelete.getFirstName().equals(person.getFirstName()) &&
                        personToDelete.getLastName().equals(person.getLastName()));
        services.saveToJsonFile();
        return result;
    }
}
