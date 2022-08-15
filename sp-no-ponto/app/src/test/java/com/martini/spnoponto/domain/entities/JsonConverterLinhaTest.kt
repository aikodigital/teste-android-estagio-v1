package com.martini.spnoponto.domain.entities

import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import org.junit.Assert
import org.junit.Test

class JsonConverterLinhaTest {
    private val json = JsonConverterLinha()

    private val linha = Linha(
        200,
        true,
        "first",
        1,
        1,
        "primary",
        "secondary"
    )

    @Test
    fun shouldReturnExpectedString() {
        val expected = javaClass.getResource("/expected.json")!!.readText()

        val actual = json.toString(linha)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnExpectedLine() {
        val text = javaClass.getResource("/expected.json")!!.readText()
        val actual = json.fromString(text)

        Assert.assertEquals(linha, actual)
    }
}