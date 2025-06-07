package com.abn_amro.usermanagment.mapper;

import com.abn_amro.usermanagment.dto.request.RoleDTO;
import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.model.Role;
import com.abn_amro.usermanagment.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToDtoList")
    UserDTO toDto(User user);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleDtosToSet")
    User toEntity(UserDTO dto);

    @Named("mapRolesToDtoList")
    default List<RoleDTO> mapRolesToDtoList(Set<Role> roles) {
        return roles == null ? null : roles.stream().map(this::toRoleDto).collect(Collectors.toList());
    }

    @Named("mapRoleDtosToSet")
    default Set<Role> mapRoleDtosToSet(List<RoleDTO> roleDtos) {
        return roleDtos == null ? null : roleDtos.stream().map(this::toRoleEntity).collect(Collectors.toSet());
    }

    default RoleDTO toRoleDto(Role role) {
        RoleDTO dto = new RoleDTO();
       // dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    default Role toRoleEntity(RoleDTO dto) {
        Role role = new Role();
       // role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }
}


