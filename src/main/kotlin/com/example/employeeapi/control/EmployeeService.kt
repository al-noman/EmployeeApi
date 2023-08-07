package com.example.employeeapi.control

import com.example.employeeapi.entity.EmployeeEntity
import com.example.employeeapi.entity.EmployeeRepository
import com.example.employeeapi.messaging.EventType.CREATE
import com.example.employeeapi.messaging.EventType.DELETE
import com.example.employeeapi.messaging.EventType.UPDATE
import com.example.employeeapi.messaging.Producer
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeService(val employeeRepository: EmployeeRepository, val producer: Producer) {
    fun getAll(): List<EmployeeEntity> = employeeRepository.findAll()

    fun getOneById(id: UUID) = employeeRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("$id")

    fun create(employee: EmployeeEntity): EmployeeEntity {
        val persistedEmployee = employeeRepository.save(employee)
        producer.sendMessage(CREATE, persistedEmployee)
        return persistedEmployee
    }

    fun updateById(id: UUID, employee: EmployeeEntity): EmployeeEntity {
        return employeeRepository.findByIdOrNull(id)?.let {
            updateProperties(it, employee)
            val updatedEmployee = employeeRepository.save(it)
            producer.sendMessage(UPDATE, updatedEmployee)
            updatedEmployee
        } ?: throw EntityNotFoundException("$id")
    }

    private fun updateProperties(persistedEntity: EmployeeEntity, updatedEntity: EmployeeEntity) {
        persistedEntity.email = updatedEntity.email
        persistedEntity.fullName = updatedEntity.fullName
        persistedEntity.birthDay = updatedEntity.birthDay
        persistedEntity.hobbies = updatedEntity.hobbies
    }

    fun deleteById(id: UUID) {
        employeeRepository.findByIdOrNull(id)?.let {
            producer.sendMessage(DELETE, it)
            employeeRepository.delete(it)
        } ?: throw EntityNotFoundException("$id")
    }
}
