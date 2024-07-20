package com.UIAutomation.emails;

import static com.UIAutomation.constants.Constants.REPORT_TITLE;

public class EmailConfigs {
    //Remember to create an app password (App Password) for Gmail before sending
    //If you use Hosting's email, it's normal
    //Enable Override Report and Send mail in config file => src/test/resources/config/config.properties
    //OVERRIDE_REPORTS=yes
    //send_email_to_users=yes

    public static final String SERVER = "smtp.gmail.com";
    public static final String PORT = "587";

    public static final String FROM = "dondocim@gmail.com";
    public static final String PASSWORD = "mqpbezyjwcewqjqb";

    public static final String[] TO = {};
    public static final String SUBJECT = REPORT_TITLE;
}
