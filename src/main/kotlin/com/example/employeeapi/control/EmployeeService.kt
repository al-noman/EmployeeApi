package com.example.employeeapi.control

import com.example.employeeapi.entity.EmployeeEntity
import com.example.employeeapi.entity.EmployeeRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {
    fun getAll(): List<EmployeeEntity> = employeeRepository.findAll()

    fun getOneById(id: UUID) = employeeRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("$id")

    fun create(employee: EmployeeEntity) = employeeRepository.save(employee)

    fun updateById(id: UUID, employee: EmployeeEntity): EmployeeEntity {
        return employeeRepository.findByIdOrNull(id)?.let {
            updateProperties(it, employee)
            employeeRepository.save(it)
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
            employeeRepository.delete(it)
        } ?: throw EntityNotFoundException("$id")
    }
}
