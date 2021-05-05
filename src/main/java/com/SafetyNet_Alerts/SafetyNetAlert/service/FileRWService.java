package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Service
public class FileRWService {
    private final Path path;

    public FileRWService() throws IOException {
        this.path = Paths.get(new ClassPathResource(JsonDataFileName.dataFileName).getURI());
        System.out.println(path.toString());
    }

    public FileRWService(Path path) {
        this.path = path;
        System.out.println(path.toString());
    }

    public void saveToJsonFile() {
        JsonFileModel jsonFileModel;
        jsonFileModel = JsonFileModel.builder()
                .persons(PersonRepository.personList)
                .firestations(FirestationRepository.firestationList)
                .medicalrecords(MedicalRecordRepository.medicalRecordList)
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
            //System.out.println(path.toString());
            return lines.collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
            return "No result found";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
