package com.kollaboralabs.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kollaboralabs.auth.config.LocalDateTimeConverter;

import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
    protected Auditable(){}

    @ApiModelProperty(example = "2014-10-09T16:23:00.000")
    @Column(name = "date_created", nullable = false)
    @CreatedDate
    @Convert(converter = LocalDateTimeConverter.class)
    @NotNull
    @lombok.Getter
    @lombok.Setter
    private LocalDateTime dateCreated;

    @ApiModelProperty(example = "2014-10-09T16:23:00.000")
    @Column(name = "date_modified", nullable = false)
    @LastModifiedDate
    @Convert(converter = LocalDateTimeConverter.class)
    @NotNull
    @lombok.Getter
    @lombok.Setter
    private LocalDateTime dateModified;
}