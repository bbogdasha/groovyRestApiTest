package com.bogdan.exception

class ErrorResponse {

    private String message
    private int status
    private List<String> details

    ErrorResponse(String message, int status, List<String> details) {
        this.message = message
        this.status = status
        this.details = details
    }

    String getMessage() {
        return message
    }

    List<String> getDetails() {
        return details
    }

    int getStatus() {
        return status
    }
}
