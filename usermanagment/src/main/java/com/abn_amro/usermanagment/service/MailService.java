package com.abn_amro.usermanagment.service;

import com.abn_amro.usermanagment.dto.request.Mail;
import com.abn_amro.usermanagment.exceptions.MailServerException;

public interface MailService {
void sendEmail(Mail mail) throws MailServerException;
void sendEmailAsync(Mail mail);
}
