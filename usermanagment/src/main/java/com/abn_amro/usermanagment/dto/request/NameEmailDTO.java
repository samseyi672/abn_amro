package com.abn_amro.usermanagment.dto.request;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NameEmailDTO {
    private String name;
    private String email;
}
