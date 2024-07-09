package com.UIAutomation.helpers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonHelpers {
    //Json Path
    private static BufferedReader bufferedReader;
    private static StringBuffer stringBuffer;
    private static DocumentContext jsonContext;
    private static String lines;
    private static String jsonFilePathDefault = Helpers.getCurrentDir() + "src/test/resources/datajson/store.json";

    public void setJsonFile(String jsonPath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(Helpers.getCurrentDir() + jsonPath));
            stringBuffer = new StringBuffer();
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
            jsonContext = JsonPath.parse(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getData(String key) {
        return jsonContext.read(key);
    }

    public List<Object> getListOfData(String key) {
        return jsonContext.read(key, List.class);
    }
}
