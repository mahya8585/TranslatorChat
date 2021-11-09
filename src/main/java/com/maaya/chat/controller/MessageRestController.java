package com.maaya.chat.controller;

import com.maaya.chat.data.HistoryMessages;
import com.maaya.chat.repository.CommunicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.maaya.chat.Configuration.CHAT_THREAD_ID;

@RestController
public class MessageRestController {
    private static final Logger logger = LoggerFactory.getLogger(MessageRestController.class);

    @GetMapping(value = "/history")
    HistoryMessages getHistoryMessages(){
        HistoryMessages historyMessages = new HistoryMessages();
        historyMessages.setHistoryMessages(new CommunicationService().getHistory(CHAT_THREAD_ID));
        return historyMessages;
    }
}
