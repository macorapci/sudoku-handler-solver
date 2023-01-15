package com.sudokuhandler.solver.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EliminateMoveDto {
    private List<EliminateDto> eliminateDtoList;
    private SudokuAlgorithmType sudokuAlgorithmType;
    private int row;
    private int column;
}
