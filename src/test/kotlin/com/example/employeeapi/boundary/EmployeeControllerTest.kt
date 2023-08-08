package com.example.employeeapi.boundary

import com.example.employeeapi.control.EmployeeConverter
import com.example.employeeapi.control.EmployeeService
import com.example.employeeapi.testutil.EmployeeFixture.EMPLOYEE_ID
import com.example.employeeapi.testutil.EmployeeFixture.createEmployeeDto
import com.example.employeeapi.testutil.EmployeeFixture.createEmployeeEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class EmployeeControllerTest {
    @Mock
    private lateinit var employeeService: EmployeeService
    @Mock
    private lateinit var employeeConverter: EmployeeConverter
    @InjectMocks
    private lateinit var employeeController: EmployeeController

    @Test
    fun `should get all employees and respond with http status 200 ok`() {
        val employeeEntities = listOf(createEmployeeEntity())
        val employeeDtos = listOf(createEmployeeDto())

        `when`(employeeService.getAll()).thenReturn(employeeEntities)
        `when`(employeeConverter.toDtoList(employeeEntities)).thenReturn(employeeDtos)

        val response = employeeController.getAll()

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).containsExactlyElementsOf(employeeDtos)
    }

    @Test
    fun `should find employee by id and respond with http status 200 ok`() {
        val employeeEntity = createEmployeeEntity()
        val employeeDto = createEmployeeDto()

        `when`(employeeService.getOneById(EMPLOYEE_ID)).thenReturn(employeeEntity)
        `when`(employeeConverter.toDto(employeeEntity)).thenReturn(employeeDto)

        val response = employeeController.getOne(EMPLOYEE_ID)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(employeeDto)
    }

    @Test
    fun `should create employee and respond with 201 created`() {
        val employeeDto = createEmployeeDto()
        val employeeEntity = createEmployeeEntity()

        `when`(employeeConverter.toEntity(employeeDto)).thenReturn(employeeEntity)
        `when`(employeeService.create(employeeEntity)).thenReturn(employeeEntity)
        `when`(employeeConverter.toDto(employeeEntity)).thenReturn(employeeDto)

        val response = employeeController.create(employeeDto)

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body).isEqualTo(employeeDto)
    }

    @Test
    fun `should update employee by id and respond with 200 ok`() {
        val employeeDto = createEmployeeDto()
        val employeeEntity = createEmployeeEntity()

        `when`(employeeConverter.toEntity(employeeDto)).thenReturn(employeeEntity)
        `when`(employeeService.updateById(EMPLOYEE_ID, employeeEntity)).thenReturn(employeeEntity)
        `when`(employeeConverter.toDto(employeeEntity)).thenReturn(employeeDto)

        val response = employeeController.update(EMPLOYEE_ID, employeeDto)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(employeeDto)
    }

    @Test
    fun `should delete by id and respond with http status 204 no content`() {
        doNothing().`when`(employeeService).deleteById(EMPLOYEE_ID)

        val response = employeeController.delete(EMPLOYEE_ID)

        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        assertThat(response.body).isNull()
    }
}