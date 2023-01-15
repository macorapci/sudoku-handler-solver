package com.sudokuhandler.solver.controllers;

import com.sudokuhandler.solver.advices.exceptions.SudokuHandlerException;
import com.sudokuhandler.solver.models.ActionHintDto;
import com.sudokuhandler.solver.services.SudokuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/v1")
@Slf4j
@RequiredArgsConstructor
public class SudokuController {
    private final SudokuService sudokuService;

    @PostMapping("/solve")
    public ResponseEntity<ActionHintDto> solveSudoku(@RequestBody int[][] sudokuTable) throws SudokuHandlerException {
        log.info("Sudoku requested to solve! sudokuTable: {}", Arrays.deepToString(sudokuTable));
        var result = this.sudokuService.solveSudoku(sudokuTable);
        log.info("Sudoku resolved. result: {}, sudokuTable: {}", result, sudokuTable);
        return ResponseEntity.ok().body(result);
    }
}
