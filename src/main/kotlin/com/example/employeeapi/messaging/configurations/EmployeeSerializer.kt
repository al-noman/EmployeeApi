package com.example.employeeapi.messaging.configurations

import com.example.employeeapi.messaging.EmployeeEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer

class EmployeeSerializer : Serializer<EmployeeEvent> {
    private val objectMapper = ObjectMapper().apply {
        this.registerModule(JavaTimeModule())
        this.registerKotlinModule()
    }

    override fun serialize(topic: String?, data: EmployeeEvent?): ByteArray {
        return objectMapper.writeValueAsBytes(
            data ?: throw SerializationException("Error when serializing EmployeeEntity to ByteArray[]")
        )
    }

    override fun close() {}
}