package ru.kopenkin.curmon.service

import java.io.InputStream

/**
 * Created by Yevgeniy on 24.01.2017.
 */
interface Parser {
    fun parse(input: InputStream): List<CentralBankResultParser.ValuteCursOnDate>
}