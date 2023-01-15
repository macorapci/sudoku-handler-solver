package com.sudokuhandler.solver.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class EliminateDto {
    @Max(9)
    @Min(1)
    private int eliminatedValue;
    private int row;
    private int column;
}
