package com.maaya.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PageController {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @GetMapping(value = "/chat")
    String chatPage(@RequestParam("user") String username, Model model) {
        logger.debug("username = " + username);

        model.addAttribute("username", username);
        return "chat";
    }

    @GetMapping(value = "/")
    String selectUserPage(Model model) {
        logger.debug("selectUserPage Controller");
        return "index";
    }

}
