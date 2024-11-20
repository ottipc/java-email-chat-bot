package com.braun.emailbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.braun.emailbot.controller.EmailService;
import com.braun.emailbot.service.OpenAIService;
import com.braun.emailbot.controller.EmailResponder;


import java.io.IOException;

import static org.mockito.Mockito.*;

class EmailResponderTest {

    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    @Mock
    private EmailService emailService; // Mock des EmailService

    @Mock
    private OpenAIService openAIService; // Mock des OpenAIService

    @InjectMocks
    private EmailResponder emailResponder; // Testobjekt mit den gemockten Services

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisiert die Mocks
    }

    @Test
    void testRespondToEmail() throws IOException {
        // Gegeben: Eine eingehende E-Mail und eine generierte Antwort
        String senderEmail = "test@example.com";
        String emailContent = "Wie funktioniert das Produkt?";
        String gptResponse = "Unser Produkt funktioniert so...";
        logger.info("Sender Email: " + senderEmail);
        logger.info("Email Content: " + emailContent);
        logger.info("GPT Response: " + gptResponse);

        // Mocking: OpenAIService gibt eine Beispielantwort zurück
        when(openAIService.getChatGPTResponse(emailContent)).thenReturn(gptResponse);

        // Wenn: Der EmailResponder auf eine E-Mail antwortet
        emailResponder.respondToEmail(senderEmail, emailContent);

        // Dann: Verifiziere, dass der EmailService die E-Mail sendet
        verify(emailService).sendEmail(senderEmail, "Re: Ihre Anfrage", gptResponse);

        // Stelle sicher, dass der OpenAIService nur einmal aufgerufen wurde
        verify(openAIService, times(1)).getChatGPTResponse(emailContent);
    }
}

