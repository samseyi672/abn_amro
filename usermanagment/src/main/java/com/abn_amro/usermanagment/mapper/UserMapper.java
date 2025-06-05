package com.abn_amro.usermanagment.mapper;

import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.model.Role;
import com.abn_amro.usermanagment.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
    public interface UserMapper {

        @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
        UserDTO toDTO(User user);

        default Set<String> mapRoles(Set<Role> roles) {
            return roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
        }
    }

