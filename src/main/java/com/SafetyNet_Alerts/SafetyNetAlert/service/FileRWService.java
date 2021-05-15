package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.exception.TechnicalException;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Data
@Service
public class FileRWService {
    private final Path path;

    public FileRWService() {
        ClassPathResource classPathResource = new ClassPathResource(JsonDataFileName.dataFileName);
        try {
            this.path = Paths.get(classPathResource.getURI());
        } catch (IOException ex) {
            throw new TechnicalException(String.format("File %s not found in resource path", classPathResource.toString()), ex);
        }

    }

    public void saveToJsonFile() {
        JsonFileModel jsonFileModel;
        jsonFileModel = JsonFileModel.builder()
                .persons(PersonDaoImpl.personList)
                .firestations(FirestationDaoImpl.firestationList)
                .medicalrecords(MedicalRecordDaoImpl.medicalRecordList)
                .build();
        stringToJsonFile(jsonFileModelToJsonAsString(jsonFileModel));
    }

    public JsonFileModel readFromJsonFile() {
        return jsonAsStringToJsonFileModel(jsonFileToString());
    }
    //Read functionality

    /**
     * create File model from string
     *
     * @param jsonAsString data as string on json form
     * @return all data as java object
     */
    public JsonFileModel jsonAsStringToJsonFileModel(String jsonAsString) {
        return JsonIterator.deserialize(jsonAsString, JsonFileModel.class);
    }

    /**
     * create string from jason data file
     *
     * @return all data as string
     */
    public String jsonFileToString() {
        Stream<String> lines = null;
        try {
            lines = Files.lines(path);
            return lines.collect(Collectors.joining(""));
        } catch (IOException ex) {
            throw new TechnicalException(String.format("File %s is empty or not found in resource path", path.toString()), ex);
        } finally {
            assert lines != null;
            lines.close();
        }
    }


    //Write functionality

    /**
     * serialize jsonFileModel to string
     *
     * @param jsonFileModel data as java object
     * @return all data as string
     */
    public String jsonFileModelToJsonAsString(JsonFileModel jsonFileModel) {
        return JsonStream.serialize(jsonFileModel);
    }

    /**
     * write string in data.json
     *
     * @param jsonAsString data as string on json form
     */
    public void stringToJsonFile(String jsonAsString) {
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            Files.writeString(path, jsonAsString);
        } catch (IOException ex) {
            throw new TechnicalException(String.format("File %s not found in resource path", path.toString()), ex);

        }
    }
}
