package com.example.employeeapi.entity

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmployeeDto(
    var id: UUID?,
    val email: String,
    val fullName: String,
    val birthDay: LocalDate,
    val hobbies: List<String>
)
