package ru.kopenkin.curmon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kopenkin.curmon.service.CurrencyService
import ru.kopenkin.curmon.service.CurrencyService.Result
import javax.servlet.http.HttpServletResponse

/**
 * Created by Yevgeniy on 18.01.2017.
 */
@RestController
class CurrencyController {

    @Autowired
    lateinit var service: CurrencyService

    @RequestMapping("/{code}")
    fun retrieve(@PathVariable code: String, response: HttpServletResponse) {
        val res = service.retrieveCursFor(code)
        when (res) {
            is Result.Error -> response.sendError(500, res.reason)
            is Result.NotFound -> response.sendError(404, "Code ${code} was not found")
            is Result.Success -> {
                response.status = 200
                response.writer.write(res.toString())
            }
        }
    }
}