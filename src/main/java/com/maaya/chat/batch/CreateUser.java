package com.maaya.chat.batch;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.AzureKeyCredential;

import java.time.OffsetDateTime;


import static com.maaya.chat.Configuration.*;

/**
 * 1つめに動かす(1回でOK)
 */
public class CreateUser {
    public static void main(String...args){
        System.out.println("Creating new USERS - start");

        //chatクライアントの作成
        CommunicationIdentityClient communicationIdentityClient = new CommunicationIdentityClientBuilder()
                .endpoint(ENDPOINT)
                .credential(new AzureKeyCredential(ACCESS_KEY))
                .buildClient();

        //ユーザの作成
        CommunicationUserIdentifier user1 = communicationIdentityClient.createUser();
        //トークンの発行
        AccessToken accessTokenUser1 = communicationIdentityClient.getToken(user1, SCOPES);
        OffsetDateTime expiresAtUser1 = accessTokenUser1.getExpiresAt();
        System.out.println("user1's Id: " + user1.getId());
        System.out.println("user1 access token expire at: " + expiresAtUser1);

        //ユーザの作成
        CommunicationUserIdentifier user2 = communicationIdentityClient.createUser();
        //トークンの発行
        AccessToken accessTokenUser2 = communicationIdentityClient.getToken(user2, SCOPES);
        OffsetDateTime expiresAtUser2 = accessTokenUser2.getExpiresAt();
        System.out.println("user2's Id: " + user2.getId());
        System.out.println("user2 access token expire at: " + expiresAtUser2);

        //TODO ユーザIDをアプリ用DBに保存する
        //TODO ユーザの使用言語をアプリ用DBに保存する
    }
}
