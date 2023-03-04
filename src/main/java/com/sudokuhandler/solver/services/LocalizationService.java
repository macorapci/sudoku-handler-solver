package com.sudokuhandler.solver.services;

public interface LocalizationService {
    String getLocalText(String messageKey, String defaultMessage, Object... args);
}
