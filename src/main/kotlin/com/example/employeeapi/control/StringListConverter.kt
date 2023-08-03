package com.example.employeeapi.control

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringListConverter: AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(attribute: List<String>): String {
        return attribute.joinToString(separator = SPLIT_CHAR)
    }

    override fun convertToEntityAttribute(dbData: String): List<String> {
        return if (dbData.isBlank()) emptyList() else dbData.split(SPLIT_CHAR)
    }

    companion object {
        private const val SPLIT_CHAR: String = ";"
    }
}
