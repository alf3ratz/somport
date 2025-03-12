package com.hse.somport.somport.service;

import com.hse.somport.somport.config.exceptions.SomportBadRequestException;
import com.hse.somport.somport.config.exceptions.SomportNotFoundException;
import com.hse.somport.somport.config.mappers.FeedConfigMapper;
import com.hse.somport.somport.config.mappers.UserMapper;
import com.hse.somport.somport.dto.FeedConfigDto;
import com.hse.somport.somport.entities.repositories.FeedConfigRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedConfigService {
    private static final String CONFIG_NOT_FOUND_MSG = "Конфигурация не найдена";
    private static final String CONFIG_WRONG_MSG = "Неверный формат конфигурации";
    private final FeedConfigMapper feedConfigMapper = Mappers.getMapper(FeedConfigMapper.class);

    @Autowired
    private FeedConfigRepository feedConfigRepository;

    public FeedConfigDto createConfig(FeedConfigDto feedConfigDto) {
        if (feedConfigDto.getConfig().getFeedCount() == null || feedConfigDto.getConfig().getPoolNumber() == null) {
            throw new SomportBadRequestException(CONFIG_WRONG_MSG);
        }
        var entity = feedConfigMapper.feedConfigDtoToEntity(feedConfigDto);
        try {
            feedConfigRepository.save(entity);
        } catch (RuntimeException e) {
            throw new SomportBadRequestException(CONFIG_WRONG_MSG);
        }
        return feedConfigDto;
    }

    @Transactional
    public FeedConfigDto getConfigById(Long id) {
        return feedConfigRepository.findById(id)
                .map(feedConfigMapper::feedConfigEntityToDto)
                .orElseThrow(() -> new SomportNotFoundException(CONFIG_NOT_FOUND_MSG));
    }

    @Transactional
    public FeedConfigDto updateConfigById(Long id, FeedConfigDto feedConfigDto) {
        var existingEntity = feedConfigRepository.findById(id)
                .orElseThrow(() -> new SomportNotFoundException(CONFIG_NOT_FOUND_MSG));

        var updatedEntity = feedConfigMapper.feedConfigDtoToEntity(feedConfigDto);
        updatedEntity.setId(existingEntity.getId());

        var savedEntity = feedConfigRepository.save(updatedEntity);
        return feedConfigMapper.feedConfigEntityToDto(savedEntity);
    }

    public void deleteConfigById(Long id) {
        if (!feedConfigRepository.existsById(id)) {
            throw new SomportNotFoundException(CONFIG_NOT_FOUND_MSG);
        }
        feedConfigRepository.deleteById(id);
    }


}
