package ru.kopenkin.curmon

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class CurrencyMonitorKtApplication

fun main(args: Array<String>) {
    SpringApplication.run(CurrencyMonitorKtApplication::class.java, *args)
}

