package com.abn_amro.usermanagment.mapper;

import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.model.Role;
import com.abn_amro.usermanagment.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    UserDTO toDto(User user);

    @Mapping(target = "roles", source = "roles")
    User toEntity(UserDTO userDTO);

    default List<Role> mapRoles(Set<Role> roles) {
        return roles == null ? null : List.copyOf(roles);
    }

    default Set<Role> mapRoles(List<Role> roles) {
        return roles == null ? null : Set.copyOf(roles);
    }

}

