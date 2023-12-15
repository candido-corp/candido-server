package com.candido.server.service.email;

public interface EmailService {
    void sendSimpleMessage(String from, String fromPersonal, String to, String subject, String text);
}
