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

    public PersonDaoImpl(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        personList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getPersons();
    }

    @Override
    public List<Person> findAll() {
        List<Person> result;
        result = personList;
        return result;
    }

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

    @Override
    public boolean delete(Person person) {
        boolean result = personList.removeIf(personToDelete ->
                personToDelete.getFirstName().equals(person.getFirstName()) &&
                        personToDelete.getLastName().equals(person.getLastName()));
        fileRWService.saveToJsonFile();
        return result;
    }
}
