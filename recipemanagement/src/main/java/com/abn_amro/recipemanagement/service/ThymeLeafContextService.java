package com.abn_amro.recipemanagement.service;


import com.abn_amro.recipemanagement.domain.dto.request.NameEmailDTO;
import com.abn_amro.recipemanagement.exception.MailServerException;

import java.util.Map;

public interface ThymeLeafContextService {
    Map<String, Object> initializeContextForUserRegistration(NameEmailDTO nameEmailDto) throws MailServerException;
}
