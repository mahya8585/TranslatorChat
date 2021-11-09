package com.maaya.chat.controller;

import com.maaya.chat.data.Message;
import com.maaya.chat.repository.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/message")
    @SendTo("/receive/message")
    public Message send(Message message) throws Exception {
//        Thread.sleep(1000);

        String name = message.getName();
        String toLanguage = "";
        String fromLanguage = "";

        //翻訳
        if(name.equals("maaya")){
            logger.debug("english user");

            toLanguage = "ja";
            fromLanguage = "en";

        } else if (name.equals("まーや")){
            logger.debug("japanese user");
            toLanguage = "en";
            fromLanguage = "ja";
        } else {
            logger.debug("登録されていないユーザです。日->英判定します");
            toLanguage = "en";
            fromLanguage = "ja";
        }
        String translatedMessage = new Translator().createTranslatedMessage(message.getStatement(), fromLanguage, toLanguage);
        return new Message(HtmlUtils.htmlEscape(message.getName()), HtmlUtils.htmlEscape(message.getStatement() + " (" + translatedMessage + " )"));
    }
}
