package com.SafetyNet_Alerts.SafetyNetAlert.tools;


import com.jsoniter.JsonIterator;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonFileRW {

    public JsonFileModel jsonAsStringToJsonFileModel(String jsonAsString) {
        JsonFileModel jsonFileModel = JsonIterator.deserialize(jsonAsString, JsonFileModel.class);
        /*JsonIterator iterator=JsonIterator.parse(jsonAsString);
        JsonFileModel jsonFileModel = null;
        try {
            jsonFileModel = iterator.read(JsonFileModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return jsonFileModel;
    }

    public String jsonFileToString() {
        Path path;
        Stream<String> lines = null;
        try {
            path = Paths.get(new ClassPathResource("data.json").getURI());
            lines = Files.lines(path);
            String data = lines.collect(Collectors.joining(""));
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            lines.close();

        }
    }


}
