package com.example.employeeapi.boundary

import com.example.employeeapi.control.EmployeeConverter
import com.example.employeeapi.control.EmployeeService
import com.example.employeeapi.entity.EmployeeDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/employees")
class EmployeeController(
    val employeeService: EmployeeService,
    val employeeConverter: EmployeeConverter
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<EmployeeDto>> {
        val employeeEntities = employeeService.getAll()
        return ResponseEntity.ok(
            employeeConverter.toDtoList(employeeEntities)
        )
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID): ResponseEntity<EmployeeDto> {
        val employeeEntity = employeeService.getOneById(id)
        return ResponseEntity.ok(
            employeeConverter.toDto(employeeEntity)
        )
    }

    @PostMapping
    fun create(@RequestBody employeeDto: EmployeeDto): ResponseEntity<EmployeeDto> {
        val employeeEntity = employeeService.create(employeeConverter.toEntity(employeeDto))
        return ResponseEntity.status(HttpStatus.CREATED).body(
                employeeConverter.toDto(employeeEntity)
        )
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody employeeDto: EmployeeDto): ResponseEntity<EmployeeDto> {
        val employeeEntity = employeeService.updateById(id, employeeConverter.toEntity(employeeDto))
        return ResponseEntity.ok(
            employeeConverter.toDto(employeeEntity)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Unit> {
        employeeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
