package ru.itis.controlworkstub.config;

import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Configuration
public class FreemarkerConfig {

    private static final DateTimeFormatter MESSAGE_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final freemarker.template.Configuration freemarkerConfiguration;

    public FreemarkerConfig(freemarker.template.Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @PostConstruct
    public void configureFreemarker() throws TemplateModelException {
        freemarkerConfiguration.setSharedVariable("formatDate", new MessageDateFormatMethod());
    }

    private static class MessageDateFormatMethod implements TemplateMethodModelEx {

        @Override
        public Object exec(List arguments) throws TemplateModelException {
            if (arguments == null || arguments.isEmpty() || arguments.get(0) == null) {
                return "";
            }

            Object value = unwrap(arguments.get(0));

            if (value instanceof LocalDateTime localDateTime) {
                return localDateTime.format(MESSAGE_DATE_FORMAT);
            }

            String text = String.valueOf(value);

            if (text.isBlank()) {
                return "";
            }

            try {
                return LocalDateTime.parse(text).format(MESSAGE_DATE_FORMAT);
            } catch (DateTimeParseException ex) {
                return text;
            }
        }

        private Object unwrap(Object value) throws TemplateModelException {
            if (value instanceof AdapterTemplateModel adapterTemplateModel) {
                return adapterTemplateModel.getAdaptedObject(Object.class);
            }

            if (value instanceof TemplateScalarModel scalarModel) {
                return scalarModel.getAsString();
            }

            return value;
        }
    }
}