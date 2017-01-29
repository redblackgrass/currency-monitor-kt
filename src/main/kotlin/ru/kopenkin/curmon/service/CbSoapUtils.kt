package ru.kopenkin.curmon.service

import java.text.SimpleDateFormat
import java.util.*
import javax.xml.soap.MessageFactory
import javax.xml.soap.SOAPConnectionFactory
import javax.xml.soap.SOAPConstants.SOAP_1_2_PROTOCOL
import javax.xml.soap.SOAPMessage

/**
 * Created by Yevgeniy on 21.01.2017.
 */
object CbSoapUtils {

    private val centralBankUri = "http://web.cbr.ru/"
    private val cursOnDatePart = "GetCursOnDateXML"
    private val onDate = "On_date"

    fun connection() = SOAPConnectionFactory.newInstance().createConnection()

    fun cursOnDateXml(date: Date): SOAPMessage {
        val message = MessageFactory.newInstance(SOAP_1_2_PROTOCOL).createMessage()
        val soapBody = message.soapPart.envelope.body
        val cursElement = soapBody.addChildElement(cursOnDatePart, null, centralBankUri)

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+6:00"))

        cursElement.addChildElement(onDate).addTextNode(sdf.format(date))
        message.saveChanges()

        return message
    }
}