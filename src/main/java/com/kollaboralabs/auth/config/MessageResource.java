package com.kollaboralabs.auth.config;


import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

public final class MessageResource {
    private static MessageResource instance = new MessageResource();
    private ResourceBundleMessageSource messageSource = null;

    private MessageResource() {
        this.messageSource = new ResourceBundleMessageSource();
        this.messageSource.setBasename("messages");
        this.messageSource.setDefaultEncoding("UTF-8");
    }

    public static MessageResource getInstance() {
        return instance;
    }

    public String resolveMessage(String fieldString, Object... fields) {
        return this.messageSource.getMessage(fieldString, fields, LocaleContextHolder.getLocale());
    }
}