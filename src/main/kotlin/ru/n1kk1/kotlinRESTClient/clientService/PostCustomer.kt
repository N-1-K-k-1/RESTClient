package ru.n1kk1.kotlinRESTClient.clientService

import khttp.request
import ru.n1kk1.kotlinRESTClient.TimeCode
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class PostCustomer : Runnable {

    private val timeCode: TimeCode = TimeCode()
    private val reqUrl = "http://192.168.0.83:8081/customers"

    override fun run() {
        try {
            val correlationId: Int = getCorrelationId()
            postCustomer(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(GetCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    private fun postCustomer(correlationId: Int) {
        val inputStream = "<customer>" +
                "<id>${correlationId.toLong()}</id>" +
                "<firstName>Alex</firstName>" +
                "<lastName>Mayfield</lastName>" +
                "<salary>3000</salary>" +
                "<city>San-Francisco</city>" +
                "<country>USA</country>" +
                "<email>AlexMfld123@gmail.com</email>" +
                "</customer>"
        Logger.getAnonymousLogger().info("POST@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        response = timeCode.timeIt {
            request(
                    method = "POST",
                    url = reqUrl,
                    headers = mapOf("Content-Type" to "application/xml"),
                    data = inputStream
            )
        }.text + "\n" + response
        Logger.getAnonymousLogger().info("EXIT POST@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
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