package com.sudokuhandler.solver.services;

import com.sudokuhandler.solver.advices.exceptions.*;
import com.sudokuhandler.solver.constants.SudokuConstant;
import com.sudokuhandler.solver.models.ActionHintDto;
import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.services.algorithms.SudokuMultipleAlgorithmService;
import com.sudokuhandler.solver.services.hints.MultipleHintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class SudokuManager implements SudokuService {
    private final SudokuSelectorService selectorService;
    private final SudokuMultipleAlgorithmService sudokuMultipleAlgorithmService;
    private final MultipleHintService multipleHintService;
    private final SudokuBruteForceService sudokuBruteForceService;

    @Override
    public ActionHintDto solveSudoku(int[][] sudokuTable) {
        this.validateSudokuTable(sudokuTable);
        int[][] deepCopyOfSudokuTable = Arrays.stream(sudokuTable)
                .map(int[]::clone)
                .toArray(int[][]::new);

        boolean isBruteForceSolvable = this.sudokuBruteForceService.isBruteForceSolve(deepCopyOfSudokuTable);
        if (!isBruteForceSolvable) {
            log.error("Sudoku table could not solve with brute force! sudokuTable: {}", Arrays.deepToString(sudokuTable));
            throw new SudokuCouldNotSolveWithBruteForce();
        }

        var cellTable = this.sudokuTableToCellTable(sudokuTable);
        var actionDto = this.sudokuMultipleAlgorithmService.solveSudoku(cellTable);
        return this.multipleHintService.createActionHintDto(actionDto);
    }

    @Override
    public void validateSudokuTable(int[][] sudokuTable) throws SudokuHandlerException {
        var hasEmptyCell = Arrays.stream(sudokuTable)
                .flatMapToLong(ints -> Arrays.stream(ints).asLongStream())
                .anyMatch(l -> l == 0);

        if (!hasEmptyCell) {
            log.error("Sudoku is solved!");
            throw new SudokuHasNoSpaceException();
        }

        var allCellsEmpty = Arrays.stream(sudokuTable)
                .flatMapToLong(ints -> Arrays.stream(ints).asLongStream())
                .filter(l -> l != 0)
                .findAny()
                .isEmpty();

        if (allCellsEmpty) {
            log.error("Sudoku table's all cells are empty.");
            throw new SudokuTableAllCellsAreEmpty();
        }

        if (sudokuTable.length != SudokuConstant.TABLE_LINE_COUNT) {
            log.error("Sudoku table length is not {}!", SudokuConstant.TABLE_LINE_COUNT);
            throw new SudokuTableNotValidException();
        }

        for (int k = 0; k < sudokuTable.length; k++) {
            for (int j = 0; j < sudokuTable[k].length; j++) {
                if (sudokuTable[k].length != SudokuConstant.TABLE_LINE_COUNT) {
                    log.error("Sudoku table's {} row not have {} element.", k, SudokuConstant.TABLE_LINE_COUNT);
                    throw new SudokuTableNotValidException();
                }
                if (sudokuTable[k][j] < 0 || sudokuTable[k][j] > 9) {
                    log.error("Sudoku [{}][{}] cell's value is not valid.", k, j);
                    throw new SudokuTableNotValidException();
                }
            }
        }
    }

    @Override
    public CellDto[][] sudokuTableToCellTable(int[][] sudokuTable) {
        var cellTable = new CellDto[SudokuConstant.TABLE_LINE_COUNT][SudokuConstant.TABLE_LINE_COUNT];
        for (int k = 0; k < SudokuConstant.TABLE_LINE_COUNT; k++) {
            for (int j = 0; j < SudokuConstant.TABLE_LINE_COUNT; j++) {
                cellTable[k][j] = this.generateCellDto(sudokuTable, k, j);
            }
        }
        return cellTable;
    }

    private CellDto generateCellDto(int[][] sudokuTable,
                                    int row, int column) {
        var possibleList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Stream.of(this.selectorService.getExcludedRow(sudokuTable, row, column),
                        this.selectorService.getExcludedColumn(sudokuTable, row, column),
                        this.selectorService.getExcludedBlock(sudokuTable, row, column))
                .flatMapToInt(Arrays::stream)
                .distinct()
                .filter(value -> value != 0)
                .forEach(value -> possibleList.remove(Integer.valueOf(value)));

        var cellValue = sudokuTable[row][column];
        if (cellValue != 0 && !possibleList.contains(cellValue)) {
            log.error("Sudoku table is not valid. cellValue: {}, row: {}, column: {}, possibleList: {}",
                    cellValue, row, column, possibleList);
            throw new SudokuTableNotValidException();
        }

        if (sudokuTable[row][column] != 0) {
            return CellDto.builder()
                    .value(sudokuTable[row][column])
                    .build();
        }

        return CellDto.builder()
                .possibleList(possibleList)
                .build();
    }
}
