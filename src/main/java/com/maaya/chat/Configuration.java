package com.maaya.chat;

import com.azure.communication.identity.models.CommunicationTokenScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {
    //endpoint
    public static final String ENDPOINT = "https://xxxxxx.communication.azure.com";
    public static final String ACCESS_KEY = "BI4eWx==";
    public static final String MASTER_USER = "8:acs:2x6";

    //TODO UserID
    public static final String USER_1 = "8:acs:2x5";
    public static final String USER_2 = "8:acs:2x4ad6";

    //TODO chat threadID
    public static final String CHAT_THREAD_ID = "19:3rx@thread.v2";

    //Translator
    public static final String SUBSCRIPTION_KEY = "xxxxxxxxxxxxxxxxxxx";
    public static final String LOCATION = "japaneast";

    //ユーザのcommunication service 利用権限
    public static final List<CommunicationTokenScope> SCOPES = new ArrayList<>(Arrays.asList(CommunicationTokenScope.CHAT));
}
