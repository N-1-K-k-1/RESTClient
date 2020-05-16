package ru.n1kk1.kotlinRESTClient

class TimeCode {
    fun <T> timeIt(block: () -> T): T {
        responseTime = 0
        val start = System.currentTimeMillis()
        val r = block()
        val end = System.currentTimeMillis()
        responseTime = (end - start)
        responseTimeArr.add("Request by ${Thread.currentThread().name} took $responseTime ms\n")
        responseTimeArrLong.add(responseTime)
        if (responseTime < responseMin)
            responseMin = responseTime
        if (responseTime > responseMax)
            responseMax = responseTime

        return r
    }

    companion object {
        @JvmStatic
        var responseTime: Long = 0
        var responseTimeArr: ArrayList<String> = ArrayList()
        var responseTimeArrLong: ArrayList<Long> = ArrayList()
        var responseMin: Long = 99999
        var responseMax: Long = 0

        fun unsetCompanion() {
            responseTime = 0
            responseTimeArr.clear()
            responseTimeArrLong.clear()
            responseMin = 99999
            responseMax = 0
        }
    }
}