package com.sudokuhandler.solver.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpringUtils {
    public static Optional<Locale> getCurrentLocale() {
        Optional<HttpServletRequest> requestOptional = SpringUtils.getCurrentHttpRequest();
        if (requestOptional.isEmpty()) {
            return Optional.empty();
        }

        HttpServletRequest request = requestOptional.get();
        if (request.getHeader(HttpHeaders.ACCEPT_LANGUAGE) == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(request.getLocale());
    }

    private static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }
}
