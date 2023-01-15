package com.sudokuhandler.solver.models;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;

@Data
@Builder
public class ActionDto {
    LinkedList<EliminateMoveDto> eliminateList;
    int row;
    int column;
    int value;
}
