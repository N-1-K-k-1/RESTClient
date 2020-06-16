package ru.n1kk1.kotlinRESTClient.clientService

import khttp.delete
import khttp.request
import ru.n1kk1.kotlinRESTClient.TimeCode
import java.net.URL
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class All4Requests : Runnable {
    private val timeCode: TimeCode = TimeCode()
    private val reqUrl = "http://192.168.0.83:8081/customers"
    private var responseInt = 0
    private val updatedInputStream = "<customer>" +
            "<firstName>updatedFirstName</firstName>" +
            "<lastName>updatedLastName</lastName>" +
            "<salary>0</salary>" +
            "<city>updatedCity</city>" +
            "<country>updatedCountry</country>" +
            "<email>updatedEmail</email>" +
            "</customer>"

    override fun run() {
        try {
            val correlationId: Int = getCorrelationId()
            all4Requests(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(GetCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    private fun all4Requests(correlationId: Int) {
        val inputStream = "<customer>" +
                "<id>${correlationId.toLong()}</id>" +
                "<firstName>Alex</firstName>" +
                "<lastName>Mayfield</lastName>" +
                "<salary>3000</salary>" +
                "<city>San-Francisco</city>" +
                "<country>USA</country>" +
                "<email>AlexMfld123@gmail.com</email>" +
                "</customer>"
        Logger.getAnonymousLogger().info("POST GET PUT DELETE@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        responseInt = timeCode.timeIt {
            request(
                    method = "POST",
                    url = reqUrl,
                    headers = mapOf("Content-Type" to "application/xml"),
                    data = inputStream
            )
            URL("$reqUrl/$correlationId").readText()
            request(
                    method = "PUT",
                    url = "$reqUrl/$correlationId",
                    headers = mapOf("Content-Type" to "application/xml"),
                    data = updatedInputStream
            )
            delete("$reqUrl/$correlationId").statusCode
        }
       if (responseInt == 200)
            response += "Content deleted successfully\n"
        else
            response += "Something went wrong\n"
        Logger.getAnonymousLogger().info("POST GET PUT DELETE@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
        println("Response time for correlationId = $correlationId is ${TimeCode.responseTime} ms")
    }

    companion object {
        @JvmStatic
        private var correlationId: Int = 1
        var response: String = ""

        @Synchronized private fun getCorrelationId(): Int {
            return correlationId++
        }

        fun unsetCorrelationId() {
            correlationId = 1
        }

        fun getCorrId(): Int {
            return correlationId
        }

        fun unsetResponse() {
            response = ""
        }
    }
}