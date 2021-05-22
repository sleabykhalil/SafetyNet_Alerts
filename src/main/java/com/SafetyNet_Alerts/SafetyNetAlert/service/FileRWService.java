package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileNames;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.exception.TechnicalException;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Managing files read write
 * there are two case
 * 1-managing input file :
 * in put file is in resources folder
 * the functionality is read and update
 * 2-managing output files corresponding to endpoints
 * no reading just writing
 */
@Slf4j
@Service
public class FileRWService {

    /**
     * get path for input files and output files
     *
     * @param fileName input or output file name
     * @return path of file
     */
    private Path getFilePath(String fileName, boolean isInput) {
        Path path;
        if (isInput) {
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            try {
                path = Paths.get(classPathResource.getURI());
            } catch (IOException ex) {
                throw new TechnicalException(String.format("File %s not found in resource path", classPathResource.toString()), ex);
            }
        } else {
            Path outputDir = Paths.get(JsonDataFileNames.OUTPUT_DIRECTORY_NAME);
            if (Files.notExists(outputDir)) {
                try {
                    Files.createDirectory(outputDir);
                } catch (IOException ex) {
                    throw new TechnicalException("Error when create output dir", ex);
                }
            }
            path = Paths.get(JsonDataFileNames.OUTPUT_DIRECTORY_NAME, fileName);
        }
        return path;
    }

    /* manage input file
    there are two functionality
       1- read input file when app start
       2- update input file when change input file
            - save
            - delete
            - update
     */

    //Read functionality

    /**
     * Read input data file
     *
     * @return
     */
    public JsonFileModel readInputFromInputJsonFileAndMabToJsonFileModel(String inputFileName) {
        String inputString = inputJsonFileToString(inputFileName);
        return inputStringToJsonFileModel(inputString);
    }

    /**
     * create string from json data file
     *
     * @return all data as string
     */
    private String inputJsonFileToString(String inputFileName) {
        Path path = getFilePath(inputFileName, true);
        try {
            String input = Files.readString(path);
            return input;
        } catch (IOException ex) {
            throw new TechnicalException(String.format("File %s is empty or not found in resource path", path.toString()), ex);
        }
    }

    /**
     * create File model from string
     *
     * @param jsonAsString data as string on json form
     * @return all data as java object
     */
    private JsonFileModel inputStringToJsonFileModel(String jsonAsString) {
        return JsonIterator.deserialize(jsonAsString, JsonFileModel.class);
    }

    // input update functionality

    /**
     * Update input file
     */
    public void updateInputFile() {
        JsonFileModel jsonFileModel;
        jsonFileModel = JsonFileModel.builder()
                .persons(PersonDaoImpl.personList)
                .firestations(FirestationDaoImpl.firestationList)
                .medicalrecords(MedicalRecordDaoImpl.medicalRecordList)
                .build();
        String newInputString = jsonFileModelToString(jsonFileModel);
        stringToJsonFile(newInputString, JsonDataFileNames.INPUT_FILE_NAME, true);
    }

    /**
     * serialize jsonFileModel to string
     *
     * @param jsonFileModel data as java object
     * @return all data as string
     */
    private String jsonFileModelToString(JsonFileModel jsonFileModel) {
        return JsonStream.serialize(jsonFileModel);
    }

    /* Create out put file corresponding to Endpoints:
     * 1- create output folder if not exist
     * 2- create out put file
     */

    //Write functionality
    public void saveOutputToJsonFile(Object object, String fileName) {
        String serializeEndPointResult = objectToString(object);
        stringToJsonFile(serializeEndPointResult, fileName, false);
    }


    private String objectToString(Object objectToSerialize) {
        return JsonStream.serialize(objectToSerialize);
    }

    /**
     * write string in data.json
     *
     * @param jsonAsString json string to write in file
     * @param filename     output file name for iEndpoint result or input file name to update
     */
    private void stringToJsonFile(String jsonAsString, String filename, boolean isInput) {
        Path filePath = getFilePath(filename, isInput);

        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
            Files.writeString(filePath, jsonAsString);
        } catch (IOException ex) {
            throw new TechnicalException(String.format("File %s not found in resource path", filePath.toString()), ex);
        }

    }
}
