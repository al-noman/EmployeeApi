package com.example.employeeapi.entity

import com.example.employeeapi.control.StringListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "EMPLOYEES")
class EmployeeEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(nullable = false)
    var id: UUID? = null

    var email: String = ""

    var fullName: String = ""

    var birthDay: LocalDate = LocalDate.EPOCH

    @Convert(converter = StringListConverter::class)
    var hobbies: List<String> = mutableListOf()
}
