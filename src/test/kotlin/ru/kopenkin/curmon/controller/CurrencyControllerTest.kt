package ru.kopenkin.curmon.controller

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by Yevgeniy on 18.01.2017.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@WebAppConfiguration
class CurrencyControllerTest {

    @Autowired
    lateinit var context: WebApplicationContext

    lateinit var mockMvc: MockMvc

    private val CORRECT_SYMBOL = "USD"
    private val WRONG_SYMBOL = "abc"

    @Before
    fun before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun returns200TestIfSymbolFound() {
        mockMvc.perform(get("/$CORRECT_SYMBOL"))
                .andExpect(status().`is`(200))
    }

    @Test
    fun returns404IfSymbolNotFound() {
        mockMvc.perform(get("/$WRONG_SYMBOL"))
                .andExpect(status().`is`(404));
    }
}