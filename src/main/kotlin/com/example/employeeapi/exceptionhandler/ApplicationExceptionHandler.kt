package com.example.employeeapi.exceptionhandler

import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDate

@ControllerAdvice
class ApplicationExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity.badRequest().body(
            ExceptionResponse(
                date = LocalDate.now(),
                message = "JSON parse error: Cannot deserialize value",
                details = ex.message?: "Http message not readable"
            )
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(LocalDate.now(), ex.message?: "Entity not found", request.getDescription(false))
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleUniqueEmailConstraintViolationException(ex: DataIntegrityViolationException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(LocalDate.now(), ex.mostSpecificCause.message?: "Provided email address already exist", request.getDescription(false))
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllOtherException(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(LocalDate.now(), ex.message?: "Internal server error", request.getDescription(false))
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}