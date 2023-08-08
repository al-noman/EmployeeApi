package com.example.employeeapi.testutil

import com.example.employeeapi.entity.EmployeeDto
import com.example.employeeapi.entity.EmployeeEntity
import java.time.LocalDate
import java.util.UUID

object EmployeeFixture {
    fun createEmployeeEntity(id: UUID? = EMPLOYEE_ID): EmployeeEntity {
        val employeeEntity = EmployeeEntity()
        employeeEntity.id = id
        employeeEntity.fullName = FULL_NAME
        employeeEntity.email = EMAIL
        employeeEntity.birthDay = BIRTH_DAY
        employeeEntity.hobbies = HOBBIES
        return employeeEntity
    }

    fun createEmployeeDto(id: UUID? = EMPLOYEE_ID, email: String = EMAIL): EmployeeDto {
        return EmployeeDto(
            id = id,
            email = email,
            fullName = FULL_NAME,
            birthDay = BIRTH_DAY,
            hobbies = HOBBIES
        )
    }

    const val FULL_NAME = "Abdullah Al Noman"
    const val EMAIL = "alnoman.cse@gmail.com"
    const val UPDATED_EMAIL = "updated.email@gmail.com"

    var EMPLOYEE_ID: UUID = UUID.randomUUID()
    val BIRTH_DAY: LocalDate = LocalDate.of(1970, 1, 1)
    val HOBBIES = listOf("Football", "Cricket")
}