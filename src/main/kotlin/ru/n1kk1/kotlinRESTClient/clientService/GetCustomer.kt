package ru.n1kk1.kotlinRESTClient.clientService

import org.json.JSONObject
import org.json.XML
import ru.n1kk1.kotlinRESTClient.TimeCode
import java.net.URL
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class GetCustomer : Runnable {

    private val timeCode: TimeCode = TimeCode()
    private val reqUrl = "http://192.168.0.83:8081/customers"
    private var response: String = ""

    override fun run() {
        try {
            val correlationId: Int = getCorrelationId()
            getCustomer(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(GetCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    private fun getCustomer(correlationId: Int) {
        Logger.getAnonymousLogger().info("GET@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        response = timeCode.timeIt { URL("$reqUrl/$correlationId").readText() } + "\n" + response
        Logger.getAnonymousLogger().info("EXIT GET@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
        println("Response time for correlationId = $correlationId is ${TimeCode.responseTime} ms")
        val json = JSONObject(response)
        responseXml = XML.toString(json) + "\n" + responseXml
    }

    companion object {
        @JvmStatic
        private var correlationId: Int = 1
        var responseXml = ""

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
            responseXml = ""
        }
    }

}