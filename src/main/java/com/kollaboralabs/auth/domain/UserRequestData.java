package com.kollaboralabs.auth.domain;

@lombok.EqualsAndHashCode(callSuper = false)
@lombok.ToString()
public class UserRequestData {
    @lombok.Getter
    @lombok.Setter
    protected String login;

    @lombok.Getter
    @lombok.Setter
    protected String email;

    @lombok.Getter
    @lombok.Setter
    protected String firstName;

    @lombok.Getter
    @lombok.Setter
    protected String lastName;
}
