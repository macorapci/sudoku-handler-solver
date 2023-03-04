package com.sudokuhandler.solver.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SolverUtils {
    public static String createErrorCode() {
        return UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }
}
