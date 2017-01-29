package ru.kopenkin.curmon.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.kopenkin.curmon.service.CurrencyService.Result
import java.io.InputStream
import java.util.*

/**
 * Created by Yevgeniy on 24.01.2017.
 */
@Component
open class CentralBankCurrencyService
    @Autowired constructor(val endpoint: Endpoint, val parser: Parser): CurrencyService {

    override fun retrieveCursFor(code: String, date: Date): Result {
        val valuteStream: InputStream;
        try {
            valuteStream = endpoint.getCursOnDate(date)
        } catch(e: Exception) {
            return Result.Error(e.message)
        }

        val res: CentralBankResultParser.ValuteCursOnDate?
        try {
            res = parser.parse(valuteStream).find{ it.txtCode == code }
        } catch(e: Exception) {
            return Result.Error(e.message)
        }
        return if (res == null)
            Result.NotFound(code)
        else
            Result.Success(res.txtCode, date, res.curs / res.nom )
    }
}