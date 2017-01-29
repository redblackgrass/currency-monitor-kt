package ru.kopenkin.curmon.service

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Yevgeniy on 22.01.2017.
 */
@RunWith(SpringRunner::class)
class CentralBankResultParserTest {

    @Value("classpath:test.xml")
    lateinit private var input: Resource

    val parser: CentralBankResultParser = CentralBankResultParser()

    @Test
    fun parseTest() {
        val first = CentralBankResultParser.ValuteCursOnDate("Австралийский доллар", 1, 45.0626, 36, "AUD")
        val res = parser.parse(input.inputStream)
        assertEquals(2, res.size)
        assertEquals(first, res.first())
    }
}