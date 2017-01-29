package ru.kopenkin.curmon.service

import java.io.InputStream
import java.util.*

/**
 * Created by Yevgeniy on 21.01.2017.
 */
interface Endpoint {
    fun getCursOnDate(date: Date): InputStream
}