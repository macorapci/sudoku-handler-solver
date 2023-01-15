package com.sudokuhandler.solver.advices.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SudokuHandlerException extends RuntimeException {
    private String message;
}
