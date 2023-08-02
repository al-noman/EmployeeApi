package com.example.employeeapi.boundary

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employees")
class EmployeeController {
    @GetMapping
    fun sayHello(): String {
        return "Hello World"
    }
}