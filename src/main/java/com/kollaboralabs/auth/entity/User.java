package com.kollaboralabs.auth.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "auth_user")
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.ToString()
public class User extends Auditable<String> implements Serializable {

    @Id
    @Column(name = "uuid", insertable = false, updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @lombok.Getter
    @lombok.Setter
    private UUID uuid;

    @Column(name = "login", nullable = false)
    @NotNull
    @lombok.Getter
    @lombok.Setter
    private String login;

    @Column(name = "email", nullable = false)
    @lombok.Getter
    @lombok.Setter
    private String email;

    @Column(name = "firstName")
    @lombok.Getter
    @lombok.Setter
    private String firstName;

    @Column(name = "lastName")
    @lombok.Getter
    @lombok.Setter
    private String lastName;

    @Column(name = "password")
    @JsonIgnore
    @lombok.Getter
    @lombok.Setter
    private String passwordHash;
}