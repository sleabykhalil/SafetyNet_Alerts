package com.SafetyNet_Alerts.SafetyNetAlert.tools;


import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
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
        return JsonIterator.deserialize(jsonAsString, JsonFileModel.class);
    }

    public String jsonFileToString() {
        Path path;
        Stream<String> lines = null;
        try {
            path = Paths.get(new ClassPathResource("data.json").getURI());
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


}
