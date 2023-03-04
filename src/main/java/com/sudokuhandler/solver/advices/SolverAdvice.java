package com.sudokuhandler.solver.advices;

import com.sudokuhandler.solver.advices.exceptions.SudokuHandlerException;
import com.sudokuhandler.solver.advices.exceptions.SudokuUnknownErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SolverAdvice {
    @ExceptionHandler(SudokuHandlerException.class)
    public ErrorResponse handleException(SudokuHandlerException ex) {
        log.error("Sudoku Handler Exception.", ex);
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        log.error("Unknown exception occurred.", ex);
        return ErrorResponse.create(new SudokuUnknownErrorException(), HttpStatus.INTERNAL_SERVER_ERROR, "An exception occurred.");
    }
}
