package com.example.adapter.output.repository.utils;


public class Constants {

    private Constants() {
    }

    /**
     * LOG_KEY
     */
    public static final String LOG_KEY_METHOD = "method={} ";
    public static final String LOG_KEY_MESSAGE = "msg=\"{}\" ";
    public static final String LOG_KEY_ENTITY = "entity=\"{}\" ";
    public static final String LOG_KEY_ENTITY_ID = "entityId={} ";


    /**
     * LOG_METHOD
     * */
    public static final Object LOG_METHOD_FIND_BY_FILTER = "findByFilter";
    public static final Object LOG_METHOD_DELETE_BY_ID = "deleteById";
    public static final Object LOG_METHOD_FIND_BY_NAME = "findUserIdsByName";
    public static final String LOG_METHOD_FIND_BY_ID = "findById";
    public static final String LOG_METHOD_SAVE = "save";
    public static final String USER_ID = "userId";
    public static final String ORDER_ID = "orderId";
    public static final String USERNAME = "name";
    public static final String PROCESSED = "processed";
    public static final String DATE = "date";
    public static final String TOTAL = "total";
    public static final String COMMA = ",";
    public static final String REGEX_DIGITS = "\\d+";
    public static final String REGEX_START = "^";
    public static final String REGEX_ANY = ".*";
    public static final String IGNORE_CASE = "(?i)";
    public static final String NO_MATCH = "NO_MATCH";
    public static final String PAD_FORMAT = "%010d";

}
