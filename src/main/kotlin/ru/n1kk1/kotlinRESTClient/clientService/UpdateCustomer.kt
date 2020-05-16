package ru.n1kk1.kotlinRESTClient.clientService

import khttp.request
import ru.n1kk1.kotlinRESTClient.TimeCode
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class UpdateCustomer : Runnable {

    private val timeCode: TimeCode = TimeCode()
    private val reqUrl = "http://192.168.0.34:8081/customers"
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
            updateCustomer(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(GetCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    private fun updateCustomer(correlationId: Int) {
        Logger.getAnonymousLogger().info("PUT@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        response = timeCode.timeIt {
            request(
                    method = "PUT",
                    url = "$reqUrl/$correlationId",
                    headers = mapOf("Content-Type" to "application/xml"),
                    data = updatedInputStream
            )
        }.text + "\n" + response
        Logger.getAnonymousLogger().info("EXIT PUT@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
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