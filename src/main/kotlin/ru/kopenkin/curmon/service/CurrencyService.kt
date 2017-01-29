package ru.kopenkin.curmon.service

import java.util.*

/**
 * Created by Yevgeniy on 24.01.2017.
 */
interface CurrencyService {

    interface Result {
        data class Success(val code: String, val date: Date, val result: Double): Result
        data class NotFound(val code: String): Result
        data class Error(val reason: String?): Result
    }

    fun retrieveCursFor(code: String, date: Date = Date()): Result
}