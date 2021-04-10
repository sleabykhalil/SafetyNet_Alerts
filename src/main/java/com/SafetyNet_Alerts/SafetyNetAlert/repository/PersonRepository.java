package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository.firestationList;
import static com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository.medicalRecordList;

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
        //JsonFileRW jsonFileRW = new JsonFileRW();
        personList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getPersons();
    }


    public List<Person> findAll() {
        List<Person> result;
        result = personList;
        return result;
    }

    public Person savePerson(Person person) {
        personList.add(person);
        services.saveToJsonFile();
        return person;
    }

    public Person updatePerson(Person personBefore, Person personAfter) {
        personList.add(personAfter);
        //personList.remove(personBefore);
        personList.removeIf(personToDelete -> personToDelete.getFirstName().equals(personBefore.getFirstName()) &&
                personToDelete.getLastName().equals(personBefore.getLastName()));
        services.saveToJsonFile();
        return personAfter;
    }

    public boolean deletePerson(Person person) {
        boolean result = personList.removeIf(personToDelete -> personToDelete.getFirstName().equals(person.getFirstName()) &&
                personToDelete.getLastName().equals(person.getLastName()));
        services.saveToJsonFile();
        return result;
    }

/*    private void writeToFile() {
        jsonFileRW.stringToJsonFile(jsonFileRW.jsonFileModelToJsonAsString(JsonFileModel.builder()
                .persons(personList)
                .firestations(firestationList)
                .medicalrecords(medicalRecordList)
                .build()), JsonDataFileName.dataFileName);
    }*/
}
