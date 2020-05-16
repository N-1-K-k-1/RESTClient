package ru.n1kk1.kotlinRESTClient.clientService

import khttp.delete
import ru.n1kk1.kotlinRESTClient.TimeCode
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class DeleteCustomer : Runnable {

    private val timeCode: TimeCode = TimeCode()
    private val reqUrl = "http://192.168.0.34:8081/customers"
    private var responseInt = 0

    override fun run() {
        try {
            val correlationId: Int = getCorrelationId()
            deleteCustomer(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(GetCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    private fun deleteCustomer(correlationId: Int) {
        Logger.getAnonymousLogger().info("DELETE@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        responseInt = timeCode.timeIt { delete("$reqUrl/$correlationId") }.statusCode
        Logger.getAnonymousLogger().info("EXIT DELETE@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
        println("Response time for correlationId = $correlationId is ${TimeCode.responseTime} ms")
        if (responseInt == 200)
            response += "Content deleted successfully\n"
        else
            response += "Something went wrong\n"
    }

    companion object {
        @JvmStatic
        private var correlationId: Int = 1
        var response: String = ""

        @Synchronized
        private fun getCorrelationId(): Int {
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