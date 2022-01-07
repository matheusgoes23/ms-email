package com.ms.email.application.services;

import com.ms.email.application.domain.Email;
import com.ms.email.application.domain.enums.StatusEmail;
import com.ms.email.application.ports.EmailRepositoryPort;
import com.ms.email.application.ports.EmailServicePort;
import com.ms.email.application.ports.SendEmailServicePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class EmailServiceImpl implements EmailServicePort {

    private final EmailRepositoryPort emailRepositoryPort;
    private final SendEmailServicePort sendEmailServicePort;

    public EmailServiceImpl(final EmailRepositoryPort emailRepositoryPort, final SendEmailServicePort sendEmailServicePort) {
        this.emailRepositoryPort = emailRepositoryPort;
        this.sendEmailServicePort = sendEmailServicePort;
    }

    @Override
    public Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        try {
            sendEmailServicePort.sendEmailSmtp(email);
            email.setStatusEmail(StatusEmail.SENT);
        } catch (Exception e) {
            email.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return save(email);
        }
    }

    @Override
    public Page<Email> findAll(Pageable pageable) {
        return emailRepositoryPort.findAll(pageable);
    }

    @Override
    public Optional<Email> findById(UUID emailId) {
        return emailRepositoryPort.findById(emailId);
    }

    @Override
    public Email save(Email email) {
        return emailRepositoryPort.save(email);
    }
}