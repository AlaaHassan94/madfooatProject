package com.madfooat.billinquiry;

public class TestConstants {

    public static final String VALID_XML_RESPONSE =
            "<?xml version=\"1.0\"?> " +
                    "<Bills> " +
                    "<bill id='1'> " +
                    "<dueDate>15-08-2017</dueDate> " +
                    "<dueAmount>198.98</dueAmount> " +
                    "<fees>0.139</fees> " +
                    "</bill>" +
                    "<bill id='2'>" +
                    "<dueDate>13-07-2017</dueDate>" +
                    "<dueAmount>50.989</dueAmount>" +
                    "</bill>" +
                    "</Bills>";

    public static final String INVALID_XML_RESPONSE =
            "<?xml version=\"1.0\"?>" +
                    "<Bills>" +
                    "   <bill id='1'>" +
                    "       <dueDate>15-08-2017</dueDate>" +
                    "       <dueAmount>198.9889</dueAmount>" +
                    "       <fees>0.139</fees>" +
                    "   </bill>" +
                    "</Bills>";


    public static final String VALID_JSON_RESPONSE = "[{\"dueDate\": \"15-08-2017\",\"dueAmount\": \"198.98\",\"fees\": \"0.139\"},{\"dueDate\": \"13-07-2017\",\"dueAmount\": \"50.989\"}]";

    public static final String INVALID_JSON_RESPONSE = "[{\"dueDate\": \"15-08-2017\",\"amount\": \"198.98\",\"stamps\": \"0.139\"}]";
}
