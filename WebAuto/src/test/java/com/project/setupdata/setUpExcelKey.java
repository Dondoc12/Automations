package com.project.setupdata;

import lombok.Data;

@Data
public class setUpExcelKey {

    public static int row;

    public static String productName = "Product Name";

    public static String category = "Category";

    public static String unit = "Unit";

    public static String weight = "Weight";

    public static String tags = "Tags";

    public static int getRow() {
        return row;
    }

    public static String getProductName() {
        return productName;
    }

    public static String getCategory() {
        return category;
    }

    public static String getUnit() {
        return unit;
    }

    public static String getWeight() {
        return weight;
    }

    public static String getTags() {
        return tags;
    }
}
