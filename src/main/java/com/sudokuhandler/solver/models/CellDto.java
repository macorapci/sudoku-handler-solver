package com.sudokuhandler.solver.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CellDto {
    private int value;
    private List<Integer> possibleList;
}
