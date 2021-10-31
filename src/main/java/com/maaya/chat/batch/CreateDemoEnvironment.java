package com.maaya.chat.batch;

import com.azure.communication.chat.ChatClient;
import com.azure.communication.chat.ChatClientBuilder;
import com.azure.communication.chat.models.ChatParticipant;
import com.azure.communication.chat.models.CreateChatThreadOptions;
import com.azure.communication.chat.models.CreateChatThreadResult;
import com.azure.communication.common.CommunicationTokenCredential;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.core.credential.AzureKeyCredential;

import static com.maaya.chat.Configuration.*;

/**
 * 2つめに動かす(1回でOK)
 */
public class CreateDemoEnvironment {
    public static void main(String...args){
        System.out.println("Creating new environment - start");

        //chatクライアントの作成
        CommunicationIdentityClient communicationIdentityClient = new CommunicationIdentityClientBuilder()
                .endpoint(ENDPOINT)
                .credential(new AzureKeyCredential(ACCESS_KEY))
                .buildClient();

        //chat client 初期化
        CommunicationUserIdentifier masterId = new CommunicationUserIdentifier(MASTER_USER);
        CommunicationTokenCredential userCredential = new CommunicationTokenCredential(communicationIdentityClient.getToken(masterId, SCOPES).getToken());
        final ChatClientBuilder builder = new ChatClientBuilder();
        builder.endpoint(ENDPOINT).credential(userCredential);
        ChatClient chatClient = builder.buildClient();

        //チャット参加と開始
        CommunicationUserIdentifier id1 = new CommunicationUserIdentifier(USER_1);
        CommunicationUserIdentifier id2 = new CommunicationUserIdentifier(USER_2);

        ChatParticipant threadUser1 = new ChatParticipant()
                .setCommunicationIdentifier(id1)
                .setDisplayName("user A");
        ChatParticipant threadUser2 = new ChatParticipant()
                .setCommunicationIdentifier(id2)
                .setDisplayName("user B");

        //TODO スレッドの作成(1回つくればずっと残るよ。functionsとかで1タイム呼び出しとかにするといいかも)
        CreateChatThreadOptions createChatThreadOptions = new CreateChatThreadOptions("DEMO")
                .addParticipant(threadUser1).addParticipant(threadUser2);
        CreateChatThreadResult result = chatClient.createChatThread(createChatThreadOptions);
        System.out.println("Chat thread ID:  " + result.getChatThread().getId());

    }
}
