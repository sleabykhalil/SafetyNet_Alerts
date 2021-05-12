package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.*;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.Child;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithAddressAndPhone;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithMedicalBackground;
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
        List<PeopleWithAddressAndPhone> peopleWithAddressAndPhoneList = new ArrayList<>();
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

            peopleWithAddressAndPhoneList.add(PeopleWithAddressAndPhone.builder()
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

        return PeopleWithAgeCatDto.builder()
                .peopleWithAddressAndPhoneList(peopleWithAddressAndPhoneList)
                .adultNumber(adultNumber)
                .childNumber(childNumber)
                .build();
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
        List<PeopleWithAddressAndPhone> peopleWithAddressAndPhoneListLivesWithChild = new ArrayList<>();

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
                peopleWithAddressAndPhoneListLivesWithChild.add(new PeopleWithAddressAndPhone(person.getFirstName(), person.getLastName()
                        , person.getAddress(), person.getPhone()));
            }
        }
        if (!childList.isEmpty()) {
            for (Child child : childList) {
                child.setPeopleWithAddressAndPhoneList(peopleWithAddressAndPhoneListLivesWithChild);
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
        List<Firestation> firestationByStationNumber;
        List<Person> personListFromFirestation = new ArrayList<>();
        List<String> phoneNumberList = new ArrayList<>();
        PhoneAlertDto phoneAlertDto;

        firestationByStationNumber = firestationDao.findFirestationByStation(firestationNumber);
        for (Firestation firestation : firestationByStationNumber) {
            personListFromFirestation.addAll(personDao.getPersonByAddress(firestation.getAddress()));
        }
        for (Person person : personListFromFirestation) {
            // phone number in list will be unique
            if (!phoneNumberList.contains(person.getPhone())) {
                phoneNumberList.add(person.getPhone());
            }
        }
        phoneAlertDto = PhoneAlertDto.builder()
                .phoneNumberList(phoneNumberList)
                .build();
        return phoneAlertDto;
    }

    public PeopleWithSpecificAgeDto getPeopleListServedByFirestationNumberByAddress(String address) {
        /*
         * get person by address
         * for each firestation address get person by address
         * get medical records and medicines
         * calculate le age pour chaque person
         * add to people list if not exist
         * return peopleWithSpecificAgeDto
         */
        PeopleWithSpecificAgeDto peopleWithSpecificAgeDto;
        Firestation firestationByAddress;
        List<PeopleWithMedicalBackground> peopleWithLastNamePhoneAgesList = new ArrayList<>();
        List<Person> personListByAddress;

        firestationByAddress = firestationDao.findFirestationByAddress(address);

        personListByAddress = personDao.getPersonByAddress(address);
        for (Person person : personListByAddress) {
            MedicalRecord medicalRecord = medicalRecordDao
                    .getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            int age = DateHelper.calculateAge(medicalRecord.getBirthdate());
            PeopleWithMedicalBackground peopleWithMedicalBackground = PeopleWithMedicalBackground.builder()
                    .lastName(person.getLastName())
                    .phone(person.getPhone())
                    .age(age)
                    .medications(medicalRecord.getMedications())
                    .allergies(medicalRecord.getAllergies())
                    .build();
            if (!peopleWithLastNamePhoneAgesList.contains(peopleWithMedicalBackground)) {
                peopleWithLastNamePhoneAgesList.add(peopleWithMedicalBackground);
            }
        }
        peopleWithSpecificAgeDto = PeopleWithSpecificAgeDto.builder()
                .firestationNumber(firestationByAddress.getStation())
                .peopleWithLastNamePhoneAgesList(peopleWithLastNamePhoneAgesList)
                .build();

        return peopleWithSpecificAgeDto;
    }

    public HouseDto getHousesByStationNumber(List<String> stationNumberList) {
        /*
         * "/flood/stations?stations=<a list of station_numbers> "
         *  public String getFoos(@RequestParam List<String> id)
         *
         * Cette url doit retourner une liste de tous les foyers desservis par la caserne.
         * Cette liste doit regrouper les personnes par adresse.
         * Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
         * faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
         *
         * create houseDto
         * for each station number => getFirestationByStationnomber
         * for each stationadress get list of people lives there

         */
        return null;
    }
}
