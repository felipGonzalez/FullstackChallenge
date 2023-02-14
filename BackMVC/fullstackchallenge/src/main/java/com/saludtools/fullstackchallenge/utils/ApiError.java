package com.saludtools.fullstackchallenge.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiError {
    private int status;
    private String message;
}