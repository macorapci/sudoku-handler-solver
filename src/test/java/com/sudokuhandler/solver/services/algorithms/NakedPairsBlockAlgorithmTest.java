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
public class NakedPairsBlockAlgorithmTest {
    @Autowired
    private SudokuService sudokuService;

    @Autowired
    private NakedPairsBlockAlgorithm nakedPairsBlockAlgorithm;

    @Test
    void test() {
        var cellTable = this.sudokuService.sudokuTableToCellTable(SudokuTestConstant.SUDOKU_TABLE_EXAMPLE_NAKED_PAIR_BLOCK);
        var eliminateList = this.nakedPairsBlockAlgorithm.eliminate(cellTable, 0, 1);
        Assertions.assertTrue(eliminateList.isPresent());
        var expected = List.of(
                EliminateDto.builder().row(0).column(0).eliminatedValue(7).build(),
                EliminateDto.builder().row(1).column(2).eliminatedValue(3).build(),
                EliminateDto.builder().row(1).column(2).eliminatedValue(7).build(),
                EliminateDto.builder().row(2).column(1).eliminatedValue(7).build(),
                EliminateDto.builder().row(2).column(2).eliminatedValue(7).build()
        );

        Assertions.assertEquals(SudokuAlgorithmType.NAKED_PAIRS_BLOCK, eliminateList.get().getSudokuAlgorithmType());
        Assertions.assertEquals(expected, eliminateList.get().getEliminateDtoList());
    }
}
