package ru.kopenkin.curmon.service

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.*
import ru.kopenkin.curmon.service.CurrencyService.Result
import java.io.InputStream
import java.util.*

/**
 * Created by Yevgeniy on 22.01.2017.
 */
open class CurrencyServiceTest {

    val endpoint = Mockito.mock(Endpoint::class.java)
    val parser = Mockito.mock(Parser::class.java)

    val validStream = Mockito.mock(InputStream::class.java)
    val invalidStream = Mockito.mock(InputStream::class.java)

    val service: CurrencyService = CentralBankCurrencyService(endpoint, parser)

    val usd = CentralBankResultParser.ValuteCursOnDate("Доллар США", 1, 30.toDouble(), 840, "USD")
    val euro = CentralBankResultParser.ValuteCursOnDate("Евро", 1, 40.toDouble(), 978, "EUR")
    val tenge = CentralBankResultParser.ValuteCursOnDate("Казахстанский тенге", 100, 18.toDouble(), 398, "KZT")

    val correctParseResult = listOf<CentralBankResultParser.ValuteCursOnDate>(euro, tenge, usd)
    val notFoundCode = "ololo"
    val date = Date()

    @Before
    fun before() {
        Mockito.`when`(endpoint.getCursOnDate(date)).thenReturn(validStream)
        Mockito.`when`(parser.parse(validStream)).thenReturn(correctParseResult)
        Mockito.`when`(parser.parse(invalidStream)).thenThrow(RuntimeException("Parser error"))
    }

    @Test
    fun shouldReturnNotFound() {
        assertTrue(service.retrieveCursFor(notFoundCode,date) is Result.NotFound)
    }

    @Test
    fun shouldReturnSuccess() {
        assertEquals(Result.Success("USD", date, 30.toDouble()), service.retrieveCursFor("USD", date))
    }

    @Test
    fun shouldReturnErrorIfParserFails() {
        Mockito.`when`(endpoint.getCursOnDate(date)).thenReturn(invalidStream)
        assertTrue(service.retrieveCursFor("anything", date) is Result.Error)
        assertEquals(Result.Error("Parser error"), service.retrieveCursFor("anything", date))
    }
}