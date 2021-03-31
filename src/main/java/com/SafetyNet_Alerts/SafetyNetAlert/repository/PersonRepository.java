package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PersonRepository {
    private static List<Person> personList;

    @PostConstruct
    private void setup() {
        JsonFileRW jsonFileRW = new JsonFileRW();
        personList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString()).getPersons();
    }
public List<Person> findAll(){
        List<Person> result= new ArrayList<>();
        result=personList;
        return result;
    }

}
