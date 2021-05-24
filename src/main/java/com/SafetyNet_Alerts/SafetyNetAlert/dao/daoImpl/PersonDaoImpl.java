package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileNames;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.PersonDao;
import com.SafetyNet_Alerts.SafetyNetAlert.exception.ValidationException;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import com.SafetyNet_Alerts.SafetyNetAlert.service.PersonService;
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
        personList = fileRWService.readInputFromInputJsonFileAndMapToJsonFileModel(JsonDataFileNames.INPUT_FILE_NAME).getPersons();
        PersonService.isPersonListValid(personList);
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
        if (result.isEmpty()) {
            throw new ValidationException("Data  file is empty");
        }
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
        if (personList.contains(person)) {
            throw new ValidationException(String.format("Person with first name: %s  last name: %s is already exist."
                    , person.getFirstName(), person.getLastName()));
        }

        List<Person> personListForValidate = personList;
        personListForValidate.add(person);
        if (!PersonService.isPersonListValid(personListForValidate)) {
            throw new ValidationException(String.format("Person with the same first name: %s  last name: %s is already exist."
                    , person.getFirstName(), person.getLastName()));
        }

        personList.add(person);
        fileRWService.updateInputFile();
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
    @Override
    public Person update(Person personBefore, Person personAfter) {
        if (personList.contains(personAfter)) {
            throw new ValidationException(String.format("Person with, first name: %s  last name: %s is already exist."
                    , personAfter.getFirstName(), personAfter.getLastName()));
        }
        for (Person person : personList) {
            if ((personBefore.getFirstName().equals(person.getFirstName())) &&
                    (personBefore.getLastName().equals(person.getLastName()))) {
                personList.set(personList.indexOf(person), personAfter);
                break;
            }
        }
        fileRWService.updateInputFile();
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
        if (!result) {
            throw new ValidationException(String.format("Person with, first name: %s  last name: %s cant be found."
                    , person.getFirstName(), person.getLastName()));
        }
        fileRWService.updateInputFile();
        return true;
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

    public Person getPersonByFirstNameAndLastName(String firstName, String lastName) {
        for (Person person : personList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    public List<Person> getListOfPersonByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> personListByFirstNameAndLastName = new ArrayList<>();
        for (Person person : personList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                personListByFirstNameAndLastName.add(person);
            }
        }
        return personListByFirstNameAndLastName;
    }

    public List<Person> getPersonByCity(String cityName) {
        List<Person> personListByCity = new ArrayList<>();
        for (Person person : personList) {
            if (person.getCity().equals(cityName)) {
                personListByCity.add(person);
            }
        }
        return personListByCity;
    }
}
