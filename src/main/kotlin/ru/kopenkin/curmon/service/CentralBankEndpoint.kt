package ru.kopenkin.curmon.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Created by Yevgeniy on 21.01.2017.
 */
@Component
open class CentralBankEndpoint: Endpoint {

    @Value("\${centralbank.endpoint}")
    private lateinit var endpoint: String

    override fun getCursOnDate(date: Date): InputStream {
        val conn = CbSoapUtils.connection()
        val request = CbSoapUtils.cursOnDateXml(date)
        val result = conn.call(request, endpoint)
        return documentAsStream(result.soapBody.extractContentAsDocument())
    }

    private fun documentAsStream(document: Document): InputStream {
        val transformer = TransformerFactory.newInstance().newTransformer()
        val out = ByteArrayOutputStream()
        transformer.transform(DOMSource(document), StreamResult(out))
        return ByteArrayInputStream(out.toByteArray())
    }
}