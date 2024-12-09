package com.hse.somport.somport.config.mappers;

import com.hse.somport.somport.entities.UserEntity;
import com.hse.somport.somport.entities.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userDTOToUserEntity(UserDto userDto);

    UserDto userEntityToUserDTO(UserEntity userEntity);
}
