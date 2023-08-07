package com.example.employeeapi.messaging.configurations

import com.example.employeeapi.messaging.EmployeeEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import kotlin.text.Charsets.UTF_8

class EmployeeDeserializer : Deserializer<EmployeeEvent> {
    private val objectMapper = ObjectMapper().apply {
        this.registerModule(JavaTimeModule())
        this.registerKotlinModule()
    }
    override fun deserialize(topic: String?, data: ByteArray?): EmployeeEvent {
        return objectMapper.readValue(
            String(
                data ?: throw SerializationException("Error when deserializing byte[] to EmployeeEntity"), UTF_8
            ), EmployeeEvent::class.java
        )
    }
    override fun close() {}
}