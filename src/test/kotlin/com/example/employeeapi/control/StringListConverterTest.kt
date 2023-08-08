package com.example.employeeapi.control

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringListConverterTest() {
    private val stringListConverter = StringListConverter()

    @Test
    fun `should convert list items to a concatenated string`() {
        val list = listOf("a", "b", "c")
        val result = stringListConverter.convertToDatabaseColumn(list)
        assertThat(result).isEqualTo("a;b;c")
    }

    @Test
    fun `should convert semicolon separated string to a list of string`() {
        val string = "a;b;c"
        val result = stringListConverter.convertToEntityAttribute(string)
        assertThat(result).containsExactlyElementsOf(listOf("a", "b", "c"))
    }
}