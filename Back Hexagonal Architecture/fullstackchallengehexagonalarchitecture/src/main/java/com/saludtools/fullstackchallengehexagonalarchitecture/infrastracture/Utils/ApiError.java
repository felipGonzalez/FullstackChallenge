package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiError {
    private int status;
    private String message;
}