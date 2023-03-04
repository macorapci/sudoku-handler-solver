package com.sudokuhandler.solver.advices;

import com.sudokuhandler.solver.advices.exceptions.SudokuHandlerException;
import com.sudokuhandler.solver.advices.exceptions.SudokuUnknownErrorException;
import com.sudokuhandler.solver.services.LocalizationService;
import com.sudokuhandler.solver.utils.SolverUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class SolverAdvice {
    private static final String DEFAULT_SUDOKU_HANDLER_ERROR_MESSAGE = "Sudoku Handler Exception.";
    private static final String DEFAULT_ERROR_MESSAGE = "Unknown exception occurred.";

    private final LocalizationService localizationService;

    @ExceptionHandler(SudokuHandlerException.class)
    public ErrorResponse handleException(SudokuHandlerException exception) {
        String errorClass = exception.getClass().getSimpleName();
        String translatedErrorMessage = this.localizationService.getLocalText(errorClass, DEFAULT_SUDOKU_HANDLER_ERROR_MESSAGE);
        log.error("{} errorClass: {}", DEFAULT_SUDOKU_HANDLER_ERROR_MESSAGE, errorClass);
        return ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, translatedErrorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception exception) {
        String errorCode = SolverUtils.createErrorCode();
        String translatedErrorMessage = this.localizationService.getLocalText(SudokuUnknownErrorException.class.getSimpleName(),
                DEFAULT_ERROR_MESSAGE, errorCode);
        log.error("{}", DEFAULT_ERROR_MESSAGE, exception);
        return ErrorResponse.create(new SudokuUnknownErrorException(), HttpStatus.INTERNAL_SERVER_ERROR, translatedErrorMessage);
    }
}
