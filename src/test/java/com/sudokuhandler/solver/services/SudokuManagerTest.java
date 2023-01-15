package com.sudokuhandler.solver.services;

import com.sudokuhandler.solver.advices.exceptions.SudokuTableNotValidException;
import com.sudokuhandler.solver.util.SudokuTestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SudokuManagerTest {
    @Autowired
    private SudokuService sudokuService;

    @Test
    public void test_sudokuTableToCellTable_with_nonPossibleRow() {
        Assertions.assertThrows(SudokuTableNotValidException.class,
                () -> this.sudokuService.sudokuTableToCellTable(SudokuTestConstant.SUDOKU_TABLE_EXAMPLE_WRONG_ROW));
    }

    @Test
    public void test_sudokuTableToCellTable_with_nonPossibleColumn() {
        Assertions.assertThrows(SudokuTableNotValidException.class,
                () -> this.sudokuService.sudokuTableToCellTable(SudokuTestConstant.SUDOKU_TABLE_EXAMPLE_WRONG_COLUMN));
    }

    @Test
    public void test_sudokuTableToCellTable_with_nonPossibleBlock() {
        Assertions.assertThrows(SudokuTableNotValidException.class,
                () -> this.sudokuService.sudokuTableToCellTable(SudokuTestConstant.SUDOKU_TABLE_EXAMPLE_WRONG_BLOCK));
    }
}
