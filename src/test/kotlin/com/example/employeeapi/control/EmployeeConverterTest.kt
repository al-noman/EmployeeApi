package com.example.employeeapi.control

import com.example.employeeapi.entity.EmployeeEntity
import com.example.employeeapi.testutil.EmployeeFixture.createEmployeeDto
import com.example.employeeapi.testutil.EmployeeFixture.createEmployeeEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class EmployeeConverterTest {
    private val employeeConverter = EmployeeConverter()

    @Test
    fun `should convert to employeeDto from employeeEntity`() {
        val employeeEntity = createEmployeeEntity()
        val employeeDto = createEmployeeDto()

        val result = employeeConverter.toDto(employeeEntity)

        assertThat(result).isEqualTo(employeeDto)
    }

    @Test
    fun `should convert to list of employeeDtos from list of employeeEntity`() {
        val anotherEmployeeId = UUID.randomUUID()
        val employeeEntities = listOf(createEmployeeEntity(), createEmployeeEntity(anotherEmployeeId))
        val employeeDtos = listOf(createEmployeeDto(), createEmployeeDto(anotherEmployeeId))

        val result = employeeConverter.toDtoList(employeeEntities)

        assertThat(result).isEqualTo(employeeDtos)
    }

    @Test
    fun `should convert to employeeEntity from employeeDto`() {
        val employeeDto = createEmployeeDto()
        val employeeEntity = createEmployeeEntity()

        val result = employeeConverter.toEntity(employeeDto)

        assertThat(result.javaClass).isEqualTo(EmployeeEntity::class.java)
        assertAll("employee",
            { assertEquals(employeeEntity.id, result.id) },
            { assertEquals(employeeEntity.fullName, result.fullName) },
            { assertEquals(employeeEntity.email, result.email) },
            { assertEquals(employeeEntity.birthDay, result.birthDay) },
            { assertEquals(employeeEntity.hobbies, result.hobbies) },
        )
    }
}