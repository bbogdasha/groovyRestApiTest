package com.bogdan.exception

import org.springframework.http.HttpStatus

trait ErrorHandler {

    def handleNotFoundProjectException(NotFoundProjectException exception) {
        List<String> details = new ArrayList<>()
        details.add(exception.getLocalizedMessage())
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.value(),
                details)
        response.status = 404

        respond error
    }

    def handleBadRequestProjectException(BadRequestProjectException exception) {
        List<String> details = new ArrayList<>()
        details.add(exception.getLocalizedMessage())
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                details)
        response.status = 400

        respond error
    }
}
