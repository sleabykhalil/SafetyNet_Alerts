package com.SafetyNet_Alerts.SafetyNetAlert.tools;


import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JsonFileRW {


    //Read functionality

    /**
     * create File model from string
     *
     * @param jsonAsString
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
    public String jsonFileToString(String fileName) {
        Path path;
        Stream<String> lines = null;
        try {
            path = Paths.get(new ClassPathResource(fileName).getURI());
            lines = Files.lines(path);
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
     * @param jsonFileModel
     * @return all data as string
     */
    public String jsonFileModelToJsonAsString(JsonFileModel jsonFileModel) {
        return JsonStream.serialize(jsonFileModel);
    }

    /**
     * write string in data.json
     *
     * @param jsonAsString
     */
    public void stringToJsonFile(String jsonAsString, String fileName) {
        Path path;
        try {
            path = Paths.get(new ClassPathResource(fileName).getURI());
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            Files.writeString(path, jsonAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
