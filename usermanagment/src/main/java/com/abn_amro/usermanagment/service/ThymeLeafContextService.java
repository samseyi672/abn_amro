package com.abn_amro.usermanagment.service;


import com.abn_amro.usermanagment.dto.request.NameEmailDTO;
import com.abn_amro.usermanagment.exceptions.MailServerException;

import java.util.Map;

public interface ThymeLeafContextService {
    Map<String, Object> initializeContextForUserRegistration(NameEmailDTO nameEmailDto) throws MailServerException;
}
