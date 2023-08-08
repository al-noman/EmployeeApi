package com.example.employeeapi.integrationtest

import com.example.employeeapi.entity.EmployeeDto
import com.example.employeeapi.exceptionhandler.ExceptionResponse
import com.example.employeeapi.testutil.EmployeeFixture.BIRTH_DAY
import com.example.employeeapi.testutil.EmployeeFixture.EMAIL
import com.example.employeeapi.testutil.EmployeeFixture.EMPLOYEE_ID
import com.example.employeeapi.testutil.EmployeeFixture.FULL_NAME
import com.example.employeeapi.testutil.EmployeeFixture.HOBBIES
import com.example.employeeapi.testutil.EmployeeFixture.UPDATED_EMAIL
import com.example.employeeapi.testutil.EmployeeFixture.createEmployeeDto
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = [ "listeners=PLAINTEXT://localhost:29092", "port=29092" ])
class EmployeeIntegrationTest {

    @LocalServerPort
    private val port: Int = 0

    @Test
    fun `should create employee and then retrieve and update and delete`() {
        `should create employee`()
        `should return 409 conflict when trying to create employee with same email address`()
        `should retrieve employee by employee id`()
        `should update employee by employee id`()
        `should delete by employee id`()
    }

    fun `should create employee`() {
        given()
            .body(createEmployeeDto(null))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .post("http://localhost:$port/employees")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(EmployeeDto::class.java)
            .also {
                EMPLOYEE_ID = it.id!!
                assertEmployee(it)
            }
    }

    private fun `should return 409 conflict when trying to create employee with same email address`() {
        given()
            .body(createEmployeeDto(null))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .post("http://localhost:$port/employees")
            .then()
            .statusCode(HttpStatus.CONFLICT.value())
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(ExceptionResponse::class.java)
    }

    fun `should retrieve employee by employee id`() {
        given()
            .get("http://localhost:$port/employees/$EMPLOYEE_ID")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(EmployeeDto::class.java)
            .also(::assertEmployee)
    }

    fun `should update employee by employee id`() {
        given()
            .body(createEmployeeDto(email = UPDATED_EMAIL))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .put("http://localhost:$port/employees/$EMPLOYEE_ID")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(EmployeeDto::class.java)
            .also { assertEmployee(it, UPDATED_EMAIL) }
    }

    fun `should delete by employee id`() {
        given()
            .delete("http://localhost:$port/employees/$EMPLOYEE_ID")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
    }

    private fun assertEmployee(employeeDto: EmployeeDto, email: String = EMAIL) {
        assertThat(employeeDto.id).isEqualTo(EMPLOYEE_ID)
        assertThat(employeeDto.fullName).isEqualTo(FULL_NAME)
        assertThat(employeeDto.email).isEqualTo(email)
        assertThat(employeeDto.birthDay).isEqualTo(BIRTH_DAY)
        assertThat(employeeDto.hobbies).isEqualTo(HOBBIES)
    }
}