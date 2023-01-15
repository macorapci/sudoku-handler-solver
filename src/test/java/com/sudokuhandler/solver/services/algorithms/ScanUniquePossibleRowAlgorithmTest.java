package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.CellDto;
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
public class ScanUniquePossibleRowAlgorithmTest {
    @Autowired
    private SudokuService sudokuService;

    @Autowired
    private ScanUniquePossibleRowAlgorithm scanUniquePossibleRowAlgorithm;

    @Test
    void testSingleRow() {
        var cellTable = sudokuService.sudokuTableToCellTable(SudokuTestConstant.SUDOKU_TABLE_EXAMPLE_SCAN_UNIQUE);
        var firstList = this.getEliminateRowList(cellTable, 0, 4);
        Assertions.assertEquals(List.of(2, 5, 6, 8), firstList);
        var secondList = this.getEliminateRowList(cellTable, 2, 8);
        Assertions.assertEquals(List.of(2, 4, 5, 8), secondList);
    }

    private List<Integer> getEliminateRowList(CellDto[][] cellTable, int row, int column) {
        var eliminateMoveDtoOptional = scanUniquePossibleRowAlgorithm.eliminate(cellTable, row, column);
        Assertions.assertTrue(eliminateMoveDtoOptional.isPresent());
        var eliminateMoveDto = eliminateMoveDtoOptional.get();
        Assertions.assertEquals(eliminateMoveDto.getSudokuAlgorithmType(), SudokuAlgorithmType.SCAN_UNIQUE_POSSIBLE_ROW);
        Assertions.assertEquals(eliminateMoveDto.getRow(), row);
        Assertions.assertEquals(eliminateMoveDto.getColumn(), column);
        return eliminateMoveDto.getEliminateDtoList()
                .stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();
    }
}
