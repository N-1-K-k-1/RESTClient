package ru.n1kk1.kotlinRESTClient.clientService

import java.net.URL

class Throughput {
    private val reqUrl = "http://192.168.0.83:8081/"

    var timeTest: Long = 0

    fun testThroughput(fileName: String): ByteArray? {
        val start = System.currentTimeMillis()
        val response: ByteArray? = URL(reqUrl + fileName).readBytes()
        val end = System.currentTimeMillis()
        timeTest = (end - start)
        return response
    }
}