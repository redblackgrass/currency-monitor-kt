package ru.kopenkin.curmon.service

import org.springframework.stereotype.Component
import java.io.InputStream
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants

/**
 * Created by Yevgeniy on 21.01.2017.
 */
@Component
open class CentralBankResultParser: Parser {

    data class ValuteCursOnDate(
        var name: String = "",
        var nom: Int = 0,
        var curs: Double = 0.toDouble(),
        var numCode: Int = 0,
        var txtCode: String = "")

    private val factory = XMLInputFactory.newFactory()

    override fun parse(input: InputStream): List<ValuteCursOnDate> {
        val reader = factory.createXMLEventReader(input)
        val result = mutableListOf<ValuteCursOnDate>()
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.eventType == XMLStreamConstants.START_ELEMENT) {
                val element = event.asStartElement()
                if (element.name.localPart == "ValuteCursOnDate") {
                    result.add(parseValuteCursOnDate(reader))
                }
            }
        }
        return result
    }

    private fun parseValuteCursOnDate(reader: XMLEventReader): ValuteCursOnDate {
        val res = ValuteCursOnDate()
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val name = event.asStartElement().name.localPart
                val content = reader.nextEvent().asCharacters()
                when (name) {
                    "Vname" -> res.name = content.toString()
                    "Vnom" -> res.nom = content.data.toInt()
                    "Vcurs" -> res.curs = content.data.toDouble()
                    "Vcode" -> res.numCode = content.data.toInt()
                    "VchCode" -> res.txtCode = content.toString()
                }
            }
            if (event.isEndElement && event.asEndElement().name.localPart == "ValuteCursOnDate") {
                break
            }
        }
        return res
    }
}