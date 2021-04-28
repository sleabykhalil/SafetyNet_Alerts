package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.PersonDao;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {
    public static List<Person> personList = new ArrayList<>();

    @Autowired
    private FileRWService fileRWService;

    /**
     * Constructor and Data initialize get all person from json file
     *
     * @param fileRWService File read write manager
     */
    public PersonDaoImpl(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        personList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getPersons();
    }

    /**
     * Get list of Persons
     *
     * @return list of persons
     */
    @Override
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
    @Override
    public Person create(Person person) {
        personList.add(person);
        fileRWService.saveToJsonFile();
        return person;
    }

    @Override
    public Person read(String firstName, String lastName) {
        return null;
    }

    /**
     * Update person in person list , first and last name cannot be modified
     * and create data json file
     *
     * @param personBefore data before update
     * @param personAfter  data after update
     * @return person after update
     */
    @Override
    public Person update(Person personBefore, Person personAfter) {
        for (Person person : personList) {
            if ((personBefore.getFirstName().equals(person.getFirstName())) &&
                    (personBefore.getLastName().equals(person.getLastName()))) {
                personList.set(personList.indexOf(person), personAfter);
                break;
            }
        }
        fileRWService.saveToJsonFile();
        return personAfter;
    }

    /**
     * Delete person from person list  and create data json file
     *
     * @param person to be deleted
     * @return true if success
     */
    @Override
    public boolean delete(Person person) {
        boolean result = personList.removeIf(personToDelete ->
                personToDelete.getFirstName().equals(person.getFirstName()) &&
                        personToDelete.getLastName().equals(person.getLastName()));
        fileRWService.saveToJsonFile();
        return result;
    }

    //URLs

    /**
     * Get list of person corresponding to address
     *
     * @param address specific address
     * @return list of person lives in the same address
     */
    public List<Person> getPersonByAddress(String address) {
        List<Person> result = new ArrayList<>();
        personList.forEach(person -> {
            if (person.getAddress().equals(address)) {
                result.add(person);
            }
        });
        return result;
    }
}
