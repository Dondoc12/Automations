package com.project.dataprovider;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.helpers.ExcelHelpers;
import com.UIAutomation.helpers.Helpers;
import com.UIAutomation.helpers.PropertiesHelpers;
import com.project.setupdata.setUpExcelKey;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public final class dataProviderFromExcelFile {

    private dataProviderFromExcelFile() {
        super();
        PropertiesHelpers.loadAllFiles();
    }

    @Test(dataProvider = "getExcelData")
    public void testGetSignInData(Hashtable<String, String> data) {
        System.out.println("ExcelDataHeader.ProductName = " + data.get(setUpExcelKey.getProductName()));
        System.out.println("ExcelDataHeader.Unit = " + data.get(setUpExcelKey.getUnit()));
        System.out.println("ExcelDataHeader.Category = " + data.get(setUpExcelKey.getCategory()));
        System.out.println("ExcelDataHeader.Weight = " + data.get(setUpExcelKey.getWeight()));
        System.out.println("ExcelDataHeader.Tags = " + data.get(setUpExcelKey.getTags()));

    }


    @DataProvider(name = "getExcelData", parallel = true)
    public static Object[][] getExcelData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        Object[][] data = excelHelpers.getDataHashTable(Helpers.getCurrentDir() + Constants.EXCEL_DATA_FILE_PATH, "Sheet1", 1, 1);
        System.out.println("getExcelData: " + data);
        return data;
    }


}
