package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import com.sudokuhandler.solver.services.SudokuService;
import com.sudokuhandler.solver.util.SudokuTestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NakedPairsRowAlgorithmTest {
    @Autowired
    private SudokuService sudokuService;

    @Autowired
    private NakedPairsRowAlgorithm nakedPairsRowAlgorithm;

    @Test
    void test() {
        var cellTable = this.sudokuService.sudokuTableToCellTable(SudokuTestConstant.SUDOKU_TABLE_EXAMPLE_NAKED_PAIR_ROW);
        var eliminateList = this.nakedPairsRowAlgorithm.eliminate(cellTable, 8, 0);
        Assertions.assertTrue(eliminateList.isPresent());
        var expected = List.of(
                EliminateDto.builder().row(8).column(2).eliminatedValue(2).build(),
                EliminateDto.builder().row(8).column(7).eliminatedValue(2).build(),
                EliminateDto.builder().row(8).column(7).eliminatedValue(8).build(),
                EliminateDto.builder().row(8).column(8).eliminatedValue(8).build()
        );

        Assertions.assertEquals(SudokuAlgorithmType.NAKED_PAIRS_ROW, eliminateList.get().getSudokuAlgorithmType());
        Assertions.assertEquals(expected, eliminateList.get().getEliminateDtoList());
    }
}
