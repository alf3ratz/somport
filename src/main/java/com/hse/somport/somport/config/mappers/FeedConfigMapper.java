package com.hse.somport.somport.config.mappers;

import com.hse.somport.somport.dto.FeedConfigDto;
import com.hse.somport.somport.dto.UserDto;
import com.hse.somport.somport.entities.FeedConfigEntity;
import com.hse.somport.somport.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FeedConfigMapper {
    FeedConfigDto feedConfigEntityToDto(FeedConfigEntity feedConfigEntity);

    @Mapping(target = "config", source = "config")
    FeedConfigEntity feedConfigDtoToEntity(FeedConfigDto feedConfigDto);
}
