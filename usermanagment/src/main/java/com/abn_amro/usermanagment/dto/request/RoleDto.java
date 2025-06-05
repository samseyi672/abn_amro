package com.abn_amro.usermanagment.dto.request;

import com.abn_amro.usermanagment.model.Permission;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
public class RoleDto {
    private String role ;
    private List<Permission> permissions;
}
