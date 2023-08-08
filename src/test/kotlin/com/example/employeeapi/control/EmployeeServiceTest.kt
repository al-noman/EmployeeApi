package com.example.employeeapi.control

import com.example.employeeapi.entity.EmployeeRepository
import com.example.employeeapi.messaging.EventType
import com.example.employeeapi.messaging.Producer
import com.example.employeeapi.testutil.EmployeeFixture.EMPLOYEE_ID
import com.example.employeeapi.testutil.EmployeeFixture.createEmployeeEntity
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class EmployeeServiceTest {
    @Mock
    private lateinit var employeeRepository: EmployeeRepository
    @Mock
    private lateinit var producer: Producer
    @InjectMocks
    private lateinit var employeeService: EmployeeService

    @Test
    fun `should return all employees by delegating to repository`() {
        val employees = listOf(createEmployeeEntity())

        `when`(employeeRepository.findAll()).thenReturn(employees)

        val result = employeeService.getAll()

        assertThat(result).containsExactlyElementsOf(employees)
    }

    @Test
    fun `should find and return employee by Id`() {
        val employee = createEmployeeEntity(EMPLOYEE_ID)

        `when`(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employee))

        val result = employeeService.getOneById(EMPLOYEE_ID)

        assertThat(result).isEqualTo(employee)
    }

    @Test
    fun `should throw entity not found exception when get by id doesn't find entity`() {
        `when`(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty())

        assertThatThrownBy { employeeService.getOneById(EMPLOYEE_ID) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("$EMPLOYEE_ID")
    }

    @Test
    fun `should create new entity by delegating to repository and send create event to kafka producer`() {
        val employee = createEmployeeEntity()
        `when`(employeeRepository.save(employee)).thenReturn(employee)

        val result = employeeService.create(employee)

        assertThat(result).isEqualTo(employee)
        verify(employeeRepository).save(employee)
        verify(producer).sendMessage(EventType.CREATE, employee)
    }

    @Test
    fun `should update properties of employee and send update event to producer`() {
        val persistedEmployee = createEmployeeEntity()
        val updatedEmployee = createEmployeeEntity().apply { this.fullName = "Abdullah Junior" }

        `when`(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(persistedEmployee))
        `when`(employeeRepository.save(persistedEmployee)).thenReturn(updatedEmployee)

        val result = employeeService.updateById(EMPLOYEE_ID, updatedEmployee)

        assertThat(result).isEqualTo(updatedEmployee)
        verify(employeeRepository).save(persistedEmployee)
        verify(producer).sendMessage(EventType.UPDATE, updatedEmployee)
    }

    @Test
    fun `should delete by id and send delete event to producer`() {
        val employee = createEmployeeEntity()

        `when`(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employee))
        doNothing().`when`(employeeRepository).delete(employee)

        employeeService.deleteById(EMPLOYEE_ID)

        verify(employeeRepository).delete(employee)
        verify(producer).sendMessage(EventType.DELETE, employee)
    }
}