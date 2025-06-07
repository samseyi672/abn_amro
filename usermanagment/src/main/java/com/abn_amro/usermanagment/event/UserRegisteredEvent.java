package com.abn_amro.usermanagment.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
    private Long userId;
    private String firstname ;
    private  String lastname ;
    private  String email;
    public String getEmail(){
        return email ;
    }
}