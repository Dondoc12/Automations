package com.project.dataprovider;

import com.UIAutomation.helpers.JsonHelpers;
import com.UIAutomation.utils.JsonUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public final class dataProviderGetSingleDataFromJson {
    private static final String jsonFilePath = "src/test/resources/dataJson/sample.json";

    @DataProvider(name = "getConfigJsonData", parallel = true)
    public static Object[][] getConfigJsonData() {
        String data1 = JsonUtils.get("test1");
        String data2 = JsonUtils.get("test2");
        String data3 = JsonUtils.get("test3");
        return new Object[][]{{data1}, {data2}, {data3}};
    }
    @Test(dataProvider = "getConfigJsonData")
    public void getConfigJsonDataTest(String data){
        System.out.println(data);
    }
    @DataProvider(name = "getFileJsonData", parallel = true)
    public static Object[][] getFileJsonData() {
        JsonHelpers jsonHelpers = new JsonHelpers();
        jsonHelpers.setJsonFile(jsonFilePath);

        Object data1 = jsonHelpers.getData("$.sample[0]['release date']");
        Object data2 = jsonHelpers.getData("$.sample[0].id");
        Object data3 = jsonHelpers.getData("$.sample[0].title");

        return new Object[][]{
                {data1 != null ? data1.toString() : null,data2 != null ? data2.toString() : null,data3 != null ? data3.toString() : null},
        };
    }

    @Test(dataProvider = "getFileJsonData")
    public void testGetFileJsonData(String data,String data2, String data3) {
        System.out.println(data);
        System.out.println(data2);
        System.out.println(data3);
    }

    @DataProvider(name = "getAllJsonDataList", parallel = true)
    public static Object[][] getAllJsonDataList() {
        JsonHelpers jsonHelpers = new JsonHelpers();
        jsonHelpers.setJsonFile(jsonFilePath);
        List<Object> releaseDates = jsonHelpers.getListOfData("$.sample[*].['release date']");
        List<Object> Ids = jsonHelpers.getListOfData("$.sample[*].id");
        Object[][] data = new Object[releaseDates.size()][2];
        for (int i = 0; i < releaseDates.size(); i++) {
            data[i][0] = releaseDates.get(i).toString();
            data[i][1] = Ids.get(i).toString();
        }
        return data;
    }

    @Test(dataProvider = "getAllJsonDataList")
    public void getAllJsonDataList(String data, String id) {
        System.out.println(data);
        System.out.println(id);
    }
}
