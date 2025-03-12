package com.hse.somport.somport.entities;


import com.hse.somport.somport.dto.FeedConfigDetails;
import com.hse.somport.somport.utils.JsonConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "feed_config_ref")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "config", columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private FeedConfigDetails config;
}
