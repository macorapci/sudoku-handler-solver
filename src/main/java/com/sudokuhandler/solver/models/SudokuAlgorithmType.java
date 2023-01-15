package com.sudokuhandler.solver.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SudokuAlgorithmType {
    SCAN_UNIQUE_POSSIBLE_ROW(1, 1),
    SCAN_UNIQUE_POSSIBLE_COLUMN(2, 1),
    SCAN_UNIQUE_POSSIBLE_BLOCK(3, 1),
    NAKED_PAIRS_ROW(4, 2),
    NAKED_PAIRS_COLUMN(5, 2),
    NAKED_PAIRS_BLOCK(6, 2);

    private final int id;
    private final int weight;
}
