package com.abn_amro.usermanagment.dto.response;

import com.abn_amro.usermanagment.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String token;
    private String timeInstant;
}
