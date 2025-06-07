package com.abn_amro.recipemanagement.service;

import com.abn_amro.recipemanagement.domain.dto.request.Mail;
import com.abn_amro.recipemanagement.exception.MailServerException;

public interface MailService {
void sendEmail(Mail mail) throws MailServerException;
void sendEmailAsync(Mail mail) throws MailServerException;
}
