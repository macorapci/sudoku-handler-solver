package com.sudokuhandler.solver.services;

import com.sudokuhandler.solver.constants.SudokuConstant;
import com.sudokuhandler.solver.models.CellDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SudokuSelectorManager implements SudokuSelectorService {
    @Override
    public int[] getExcludedRow(int[][] sudokuTable, int row, int column) {
        int[] rowArray = new int[SudokuConstant.TABLE_LINE_COUNT - 1];
        int x = 0;
        for (int k = 0; k< SudokuConstant.TABLE_LINE_COUNT; k++) {
            if (column == k) {
                continue;
            }
            rowArray[x] = sudokuTable[row][k];
            x++;
        }
        return rowArray;
    }

    @Override
    public int[] getExcludedColumn(int[][] sudokuTable, int row, int column) {
        int[] columnArray = new int[SudokuConstant.TABLE_LINE_COUNT - 1];
        int x = 0;
        for (int k=0; k<SudokuConstant.TABLE_LINE_COUNT; k++) {
            if (k == row) {
                continue;
            }
            columnArray[x] = sudokuTable[k][column];
            x++;
        }
        return columnArray;
    }

    @Override
    public int[] getExcludedBlock(int[][] sudokuTable, int row, int column) {
        int[] block = new int[SudokuConstant.TABLE_LINE_COUNT - 1];
        int blockRowIndex = (row / 3) * 3;
        int blockColumnIndex = (column / 3) * 3;
        int x = 0;
        for (int k = blockRowIndex; k<blockRowIndex + 3; k++) {
            for (int j=blockColumnIndex; j<blockColumnIndex+3; j++) {
                if (k == row && j == column) {
                    continue;
                }
                int value = sudokuTable[k][j];
                block[x] = value;
                x++;
            }
        }
        return block;
    }

    @Override
    public CellDto[] getRow(CellDto[][] cellTable, int row) {
        CellDto[] rowArray = new CellDto[9];
        for (int k=0; k<SudokuConstant.TABLE_LINE_COUNT; k++) {
            rowArray[k] = cellTable[row][k];
        }
        return rowArray;
    }

    @Override
    public CellDto[] getColumn(CellDto[][] cellTable, int column) {
        CellDto[] columnArray = new CellDto[9];
        for (int k=0; k<SudokuConstant.TABLE_LINE_COUNT; k++) {
            columnArray[k] = cellTable[k][column];
        }
        return columnArray;
    }

    @Override
    public CellDto[][] getExcludedBlock(CellDto[][] sudokuTable, int row, int column) {
        CellDto[][] block = new CellDto[SudokuConstant.BLOCK_LINE_COUNT][SudokuConstant.BLOCK_LINE_COUNT];
        int blockRowIndex = (row / 3) * 3;
        int blockColumnIndex = (column / 3) * 3;
        for (int k = blockRowIndex; k<blockRowIndex + 3; k++) {
            for (int j=blockColumnIndex; j<blockColumnIndex+3; j++) {
                block[k-blockRowIndex][j-blockColumnIndex] = sudokuTable[k][j];
            }
        }
        return block;
    }

    @Override
    public CellDto[] getListExcludedRow(CellDto[][] cellTable, int row, int column) {
        CellDto[] rowArray = new CellDto[SudokuConstant.TABLE_LINE_COUNT - 1];
        for (int k=0, j=0; k< SudokuConstant.TABLE_LINE_COUNT; k++) {
            if (k == column) {
                continue;
            }

            rowArray[j] = cellTable[row][k];
            j++;
        }

        return rowArray;
    }

    @Override
    public CellDto[] getListExcludedColumn(CellDto[][] cellTable, int row, int column) {
        CellDto[] columnArray = new CellDto[SudokuConstant.TABLE_LINE_COUNT - 1];
        for (int k=0, j=0; k< SudokuConstant.TABLE_LINE_COUNT; k++) {
            if (k == row) {
                continue;
            }

            columnArray[j] = cellTable[k][column];
            j++;
        }

        return columnArray;
    }

    @Override
    public CellDto[] getListExcludedBlock(CellDto[][] cellTable, int row, int column) {
        CellDto[] blockArray = new CellDto[SudokuConstant.TABLE_LINE_COUNT - 1];
        int blockRowIndex = (row / 3) * 3;
        int blockColumnIndex = (column / 3) * 3;
        int x = 0;
        for (int k = blockRowIndex; k<blockRowIndex+3; k++) {
            for (int j=blockColumnIndex; j<blockColumnIndex+3; j++) {
                if (k == row && j == column) {
                    continue;
                }
                blockArray[x] = cellTable[k][j];
                x++;
            }
        }
        return blockArray;
    }
}
