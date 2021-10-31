package com.maaya.chat.repository;

import com.azure.communication.chat.ChatClientBuilder;
import com.azure.communication.chat.ChatThreadClient;
import com.azure.communication.chat.models.ChatMessageType;
import com.azure.communication.chat.models.SendChatMessageOptions;
import com.azure.communication.common.CommunicationTokenCredential;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.core.credential.AzureKeyCredential;

import java.util.List;
import java.util.stream.Collectors;

import static com.maaya.chat.Configuration.*;

public class CommunicationService {
    private ChatThreadClient createChatThreadClient(){
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

        //スレッド情報の取得
        return builder.buildClient().getChatThreadClient(CHAT_THREAD_ID);
    }


    public List<String> getHistory(String threadID){
//        ChatThreadClient chatThreadClient = createChatThreadClient();

        //メッセージの受信
//        List<String> historyMessages = new ArrayList<>();
//        chatThreadClient.listMessages()
//                .stream().filter(m -> m.getContent().getMessage() != null)
//                .forEach(message ->
//                        historyMessages.add(message.getSenderDisplayName() + " : " + message.getContent().getMessage())
//                );
//        return historyMessages;
        return createChatThreadClient().listMessages()
                .stream().filter(m -> m.getContent().getMessage() != null)
                .map(message ->
                        message.getSenderDisplayName() + " : " + message.getContent().getMessage()
                ).collect(Collectors.toList());
    }


    public void SendChatMessageForACS(String name, String message){
        //メッセージの送信
        SendChatMessageOptions sendChatMessageOptions = new SendChatMessageOptions()
                .setContent(message)
                .setType(ChatMessageType.TEXT)
                .setSenderDisplayName(name);

        createChatThreadClient().sendMessage(sendChatMessageOptions);
    }
 }
