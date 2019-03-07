package com.kollaboralabs.auth.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Convert;

import com.kollaboralabs.auth.config.LocalDateTimeConverter;

import io.swagger.annotations.ApiModelProperty;

@lombok.EqualsAndHashCode(callSuper = false)
@lombok.ToString()
public class UserResponseData {
    @lombok.Getter
    @lombok.Setter
    private UUID uuid;

    @lombok.Getter
    @lombok.Setter
    private String login;

    @lombok.Getter
    @lombok.Setter
    private String email;

    @lombok.Getter
    @lombok.Setter
    private String firstName;

    @lombok.Getter
    @lombok.Setter
    private String lastName;

    @ApiModelProperty(example = "2014-10-09T16:23:00.000")
    @Convert(converter = LocalDateTimeConverter.class)
    @lombok.Getter
    @lombok.Setter
    private LocalDateTime dateCreated;

    @ApiModelProperty(example = "2014-10-09T16:23:00.000")
    @Convert(converter = LocalDateTimeConverter.class)
    @lombok.Getter
    @lombok.Setter
    private LocalDateTime dateModified;
}
