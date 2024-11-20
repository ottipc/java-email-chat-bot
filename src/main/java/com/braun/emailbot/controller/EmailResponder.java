package com.braun.emailbot.controller;
import org.springframework.context.event.ContextRefreshedEvent;
import com.braun.emailbot.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EmailResponder {

    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());


    @Autowired
    private EmailService emailService;

    @Autowired
    private OpenAIService openAIService;

    @EventListener(ContextRefreshedEvent.class)
    public void startListening() {
        // Email-Listener wird hier initialisiert
    }

    public void respondToEmail(String senderEmail, String emailContent) {
        try {
            logger.info("Email Content: " + emailContent);
            String response = openAIService.getChatGPTResponse(emailContent);
            logger.info("GPT response: " + response);
            emailService.sendEmail(senderEmail, "Re: Ihre Anfrage", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
