package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.ChildAlertDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PeopleWithAgeCatDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.Child;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.People;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class URLsService {
    @Autowired
    PersonDaoImpl personDao;
    @Autowired
    FirestationDaoImpl firestationDao;
    @Autowired
    MedicalRecordDaoImpl medicalRecordDao;

    /**
     * get list of person with adult number and Child number
     *
     * @param stationNumber station cover specific address
     * @return PersonWithAgeCat Dto list of person + adults number + childes² number
     */
    public PeopleWithAgeCatDto getListOfPersonCoveredByFireStation(String stationNumber) {
        /*
         * get list of firestation by station number
         * for each firestation.address get list of person by address
         * for each person get medical record by first name and last name
         * from medical record lest get adult number and Child number
         * return dto contain list of person and number of adult and  Child number
         * */
        List<Firestation> firestationByAddress;
        List<People> peopleList = new ArrayList<>();
        List<Person> personByAddress = new ArrayList<>();
        List<MedicalRecord> medicalRecordByName = new ArrayList<>();
        int adultNumber = 0;
        int childNumber = 0;

        firestationByAddress = firestationDao.findFirestationByStation(stationNumber);
        firestationByAddress.forEach(firestation -> personByAddress.addAll(personDao
                .getPersonByAddress(firestation.getAddress())));

        personByAddress.forEach(person -> {
            medicalRecordByName.add(medicalRecordDao
                    .getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName()));

            peopleList.add(People.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .address(person.getAddress())
                    .phone(person.getPhone())
                    .build());


        });

        for (MedicalRecord medicalRecord : medicalRecordByName) {
            if (DateHelper.isAdult(medicalRecord.getBirthdate())) {
                adultNumber += 1;
            } else childNumber += 1;
        }

        PeopleWithAgeCatDto peopleWithAgeCatDto = PeopleWithAgeCatDto.builder()
                .peopleList(peopleList)
                .adultNumber(adultNumber)
                .childNumber(childNumber)
                .build();

        return peopleWithAgeCatDto;
    }

    public ChildAlertDto getListOFChildByAddress(String address) {
        /*
         * get person list by address
         * get birthday from medicalRecords for each person in person list
         * calculate age for each person
         * if is child add to child with age
         * if adult ad to people list
         * if there is no Child return empty list
         * */
        ChildAlertDto childAlertDto = ChildAlertDto.builder().build();
        List<Child> childList = new ArrayList<>();
        List<People> peopleListLivesWithChild = new ArrayList<>();

        List<Person> personByAddress = personDao.getPersonByAddress(address);
        MedicalRecord medicalRecordByName;
        int age;
        for (Person person : personByAddress) {
            medicalRecordByName = medicalRecordDao
                    .getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (!DateHelper.isAdult(medicalRecordByName.getBirthdate())) {
                age = DateHelper.calculateAge(medicalRecordByName.getBirthdate());
                childList.add(new Child(person.getFirstName(), person.getLastName(), age));
            } else {
                peopleListLivesWithChild.add(new People(person.getFirstName(), person.getLastName()
                        , person.getAddress(), person.getPhone()));
            }
        }
        if (!childList.isEmpty()) {
            for (Child child : childList) {
                child.setPeopleList(peopleListLivesWithChild);
            }
            childAlertDto = ChildAlertDto.builder()
                    .children(childList)
                    .build();
        }
        return childAlertDto;
    }

    public PhoneAlertDto getPhoneNumber(String firestationNumber) {
        /*
         * get firestation by number
         * for each firestation address get person by address
         * get phone number from person
         * add to phone number list if not exist
         * return list of phone number
         */
        return null;
    }

}
