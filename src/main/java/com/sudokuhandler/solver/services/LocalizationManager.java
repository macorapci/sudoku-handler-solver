package com.sudokuhandler.solver.services;

import com.sudokuhandler.solver.utils.SpringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocalizationManager implements LocalizationService {
    private final MessageSource messageSource;

    public String getLocalText(String messageKey, String defaultMessage, Object... args) {
        Locale locale = this.getLocale();
        String localMessage = messageSource.getMessage(messageKey, args, null, locale);
        if (localMessage != null) {
            return localMessage;
        }

        log.error("Message could not translated! messageKey: {}, args: {}, defaultMessage: {}",
                messageKey, args, defaultMessage);
        return defaultMessage;
    }

    private Locale getLocale() {
        return SpringUtils.getCurrentLocale()
                .orElse(Locale.ENGLISH);
    }
}
