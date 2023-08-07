package com.example.employeeapi.messaging

import com.example.employeeapi.entity.EmployeeDto

data class EmployeeEvent(
    val eventType: EventType,
    val payload: EmployeeDto
)

enum class EventType {
    CREATE, UPDATE, DELETE
}
