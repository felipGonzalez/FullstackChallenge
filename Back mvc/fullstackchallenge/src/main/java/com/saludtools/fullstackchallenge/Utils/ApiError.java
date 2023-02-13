package com.saludtools.fullstackchallenge.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiError {
    private int status;
    private String message;
}