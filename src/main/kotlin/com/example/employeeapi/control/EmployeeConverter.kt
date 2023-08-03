package com.example.employeeapi.control

import com.example.employeeapi.entity.EmployeeDto
import com.example.employeeapi.entity.EmployeeEntity
import org.springframework.stereotype.Component

@Component
class EmployeeConverter {
    fun toDto(employeeEntity: EmployeeEntity): EmployeeDto {
        return EmployeeDto(
            id = employeeEntity.id,
            email = employeeEntity.email,
            fullName = employeeEntity.fullName,
            birthDay = employeeEntity.birthDay,
            hobbies = employeeEntity.hobbies
        )
    }

    fun toDtoList(employeeEntities: List<EmployeeEntity>): List<EmployeeDto> {
        return employeeEntities.map { toDto(it) }.toList()
    }

    fun toEntity(employeeDto: EmployeeDto): EmployeeEntity {
        val employeeEntity = EmployeeEntity()
        employeeDto.id?.let { employeeEntity.id = it }
        employeeEntity.email = employeeDto.email
        employeeEntity.fullName = employeeDto.fullName
        employeeEntity.birthDay = employeeDto.birthDay
        employeeEntity.hobbies = employeeDto.hobbies
        return employeeEntity
    }
}