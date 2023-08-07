package com.example.employeeapi.messaging

import com.example.employeeapi.control.EmployeeConverter
import com.example.employeeapi.entity.EmployeeEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class Producer(
    @Value("\${kafka.topics.employee}")
    private val topic: String,
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val converter: EmployeeConverter
) {
    private val logger = LoggerFactory.getLogger(Producer::class.java)

    fun sendMessage(eventType: EventType, employeeEntity: EmployeeEntity) {
        val message = prepareMessage(eventType, employeeEntity)
        logger.info("Sending kafka message: $message")
        kafkaTemplate.send(message)
    }

    private fun prepareMessage(
        eventType: EventType,
        employeeEntity: EmployeeEntity
    ): Message<EmployeeEvent> {
        val employeeEvent = EmployeeEvent(eventType, converter.toDto(employeeEntity))
        return MessageBuilder.withPayload(employeeEvent)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader("X-Custom-Header", "Custom header here")
            .build()
    }
}