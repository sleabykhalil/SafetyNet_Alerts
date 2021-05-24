package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileNames;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.*;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.Child;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.MedicalHistory;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithAddressAndPhone;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithMedicalBackground;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class URLsService {
    @Autowired
    PersonDaoImpl personDao;
    @Autowired
    FirestationDaoImpl firestationDao;
    @Autowired
    MedicalRecordDaoImpl medicalRecordDao;
    @Autowired
    FileRWService fileRWService;

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
         * create output file
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

        PeopleWithAgeCatDto result;

        if (peopleWithAddressAndPhoneList.isEmpty()) {
            result = null;
        } else {
            result = PeopleWithAgeCatDto.builder()
                    .peopleWithAddressAndPhoneList(peopleWithAddressAndPhoneList)
                    .adultNumber(adultNumber)
                    .childNumber(childNumber)
                    .build();
        }
        String filename = fileRWService.createFileName(JsonDataFileNames.PERSONS_COVERED_BY_FIRE_STATION);
        fileRWService.saveOutputToJsonFile(result, filename);

        return result;
    }

    /**
     * Get list of children with there ages and adults lives with them
     *
     * @param address address to search by
     * @return Dto with list of children or empty list if no children found
     */
    public ChildAlertDto getListOFChildByAddress(String address) {
        /*
         * get person list by address
         * get birthday from medicalRecords for each person in person list
         * calculate age for each person
         * if is child add to child with age
         * if adult ad to people list
         * if there is no Child return empty list
         * */
        ChildAlertDto childAlertDto;
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
            childAlertDto = ChildAlertDto.builder()
                    .children(childList)
                    .peopleWithAddressAndPhoneList(peopleWithAddressAndPhoneListLivesWithChild)
                    .build();
        } else {
            childAlertDto = null;
        }

        ChildAlertDto result = childAlertDto;

        String filename = fileRWService.createFileName(JsonDataFileNames.CHILD_ALERT);
        fileRWService.saveOutputToJsonFile(result, filename);
        return result;
    }

    /**
     * Get phone number for all persons corresponding to firestation number
     *
     * @param firestationNumber fire station number to search by
     * @return Dto with list of phone number without repeating if phone number present in multiple records
     */
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

        PhoneAlertDto result;
        if (!phoneNumberList.isEmpty()) {
            phoneAlertDto = PhoneAlertDto.builder()
                    .phoneNumberList(phoneNumberList)
                    .build();
            result = phoneAlertDto;
        } else {
            result = null;
        }

        String filename = fileRWService.createFileName(JsonDataFileNames.PHONE_ALERT);
        fileRWService.saveOutputToJsonFile(result, filename);

        return result;
    }

    /**
     * Get person lives in specific address after calculating ther ages
     *
     * @param address address to search by
     * @return Dto list of people with age
     */
    public PeopleWithSpecificAgeDto getPeopleListServedByFirestationByAddress(String address) {
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
        PeopleWithSpecificAgeDto result;
        if (!peopleWithLastNamePhoneAgesList.isEmpty()) {
            peopleWithSpecificAgeDto = PeopleWithSpecificAgeDto.builder()
                    .firestationNumber(firestationByAddress.getStation())
                    .peopleWithLastNamePhoneAgesList(peopleWithLastNamePhoneAgesList)
                    .build();
            result = peopleWithSpecificAgeDto;
        } else {
            result = null;
        }

        String filename = fileRWService.createFileName(JsonDataFileNames.FIRE_ACCIDENT);
        fileRWService.saveOutputToJsonFile(result, filename);

        return result;
    }

    /**
     * Get people who lives in addresses corresponding to list of firestation number
     *
     * @param stationNumberList List of firestation number
     * @return Dto containing mab with address as key and list of people lives in this address as value
     */
    public HouseDto getHousesByStationNumber(List<String> stationNumberList) {
        /*
         * "/flood/stations?stations=<a list of station_numbers> "
         *  public String getFoos(@RequestParam List<String> id)
         *
         * Cette url doit retourner une liste de tous les foyers desservis par la caserne.
         * Cette liste doit regrouper les personnes par adresse.
         * Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
         * faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom. ???
         *
         * create houseDto
         * for each station number => getFirestationByStationnomber
         * for each stationadress get list of people lives there

         */
        List<String> addressList = new ArrayList<>();
        for (String stationNumber : stationNumberList) {
            List<Firestation> firestationListByStationNumber = firestationDao.findFirestationByStation(stationNumber);
            for (Firestation firestation :
                    firestationListByStationNumber) {
                if (!addressList.contains(firestation.getAddress())) {
                    addressList.add(firestation.getAddress());
                }
            }
        }

        List<PeopleWithMedicalBackground> peopleWithMedicalBackgroundList = new ArrayList<>();
        Map<String, List<PeopleWithMedicalBackground>> addressAndPeopleWithSpecificAgeDtoMap = new HashMap<>();

        for (String address : addressList) {
            List<Person> personListByAddress = personDao.getPersonByAddress(address);
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
                if (!peopleWithMedicalBackgroundList.contains(peopleWithMedicalBackground)) {
                    peopleWithMedicalBackgroundList.add(peopleWithMedicalBackground);
                }

            }
            if (!peopleWithMedicalBackgroundList.isEmpty()) {
                addressAndPeopleWithSpecificAgeDtoMap.putIfAbsent(address, peopleWithMedicalBackgroundList);
            }
        }
        HouseDto result;
        if (!addressAndPeopleWithSpecificAgeDtoMap.isEmpty()) {
            result = HouseDto.builder()
                    .addressAndPeopleWithSpecificAgeDtoMap(addressAndPeopleWithSpecificAgeDtoMap)
                    .build();
        } else {
            result = null;
        }

        String filename = fileRWService.createFileName(JsonDataFileNames.FLOOD_ACCIDENT);
        fileRWService.saveOutputToJsonFile(result, filename);

        return result;
    }

    /**
     * Get persons how has the same first name and last name
     * //TODO ask product owner because the combination of first and last name are unique identifier
     *
     * @param firstName First name to search by
     * @param lastName  Last name to search by
     * @return list of all people have the same first and last name
     */
    public List<PersonInfoDto> getListOfPersonInfo(String firstName, String lastName) {
        /*
         * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>

         *  Cette url doit retourner
         *  le nom,  l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments, posologie, allergies)
         *  de chaque habitant.
         *  Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
         *
         * create dto PersonInfoDto
         * getPersonByFirstNameAndLastName
         * getMedicalRecordByFirstNameAndLastName
         * return ListOfPersonInfo
         * */
        List<Person> personListByFirstNameAndLastName = personDao.getListOfPersonByFirstNameAndLastName(firstName, lastName);
        List<PersonInfoDto> personInfoDtoList = new ArrayList<>();
        for (Person person : personListByFirstNameAndLastName) {
            MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(firstName, lastName);
            int age = DateHelper.calculateAge(medicalRecord.getBirthdate());
            personInfoDtoList.add(PersonInfoDto.builder()
                    .lastName(person.getLastName())
                    .address(person.getAddress())
                    .age(age)
                    .email(person.getEmail())
                    .medicalHistory(new MedicalHistory(medicalRecord.getMedications(), medicalRecord.getAllergies()))
                    .build());
        }
        List<PersonInfoDto> result;
        if (!personInfoDtoList.isEmpty()) {
            result = personInfoDtoList;
        } else {
            result = null;
        }
        String filename = fileRWService.createFileName(JsonDataFileNames.PERSON_INFO);
        fileRWService.saveOutputToJsonFile(result, filename);

        return result;
    }

    /**
     * Get all email addresses for persons live in city
     *
     * @param cityName city name to search by
     * @return list of email address , the address present one time in list
     */
    public List<String> getEmailAddressByCity(String cityName) {
        /*
         * get person by city
         *  each person get emailAddress for add if not exist
         *
         */
        List<Person> personListByCity = personDao.getPersonByCity(cityName);
        List<String> addressListByCity = new ArrayList<>();
        for (Person person : personListByCity) {
            if (person.getCity().equals(cityName)) {
                if (!ObjectUtils.isEmpty(person.getEmail()) && !addressListByCity.contains(person.getEmail())) {
                    addressListByCity.add(person.getEmail());
                }
            }
        }
        List<String> result;
        if (!addressListByCity.isEmpty()) {
            result = addressListByCity;
        } else {
            result = null;
        }
        String filename = fileRWService.createFileName(JsonDataFileNames.EMAIL_ALERT_BY_CITY);
        fileRWService.saveOutputToJsonFile(result, filename);

        return result;
    }
}
