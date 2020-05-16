package ru.n1kk1.kotlinRESTClient

import java.io.FileWriter
import java.io.IOException

class WriteCSV {
    private val csvHeaderForResponse = "Request type,Number of requests,Average response time (ms)"
    private val csvHeaderForThroughput = "File size (KB),Response time (ms),Throughput (KB/s)"

    fun writeNewCsvResponse(request: String, clientQuantity: Int) {
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter("TestResponse.csv")

            fileWriter.append(csvHeaderForResponse)
            fileWriter.append('\n')
            fileWriter.append(request)
            fileWriter.append(',')
            fileWriter.append(clientQuantity.toString())
            fileWriter.append(',')
            fileWriter.append((TimeCode.responseTimeArrLong.sum() / clientQuantity).toString())

            println("CSV written successfully!")
        } catch (e: Exception) {
            println("Error while writing CSV!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

    fun writeExistingCsvResponse (csvName: String?, request: String, clientQuantity: Int) {
        var fileWriter: FileWriter? = null
        println("$csvName is opened")
        try {
            fileWriter = FileWriter(csvName, true)

            fileWriter.append('\n')
            fileWriter.append(request)
            fileWriter.append(',')
            fileWriter.append(clientQuantity.toString())
            fileWriter.append(',')
            fileWriter.append((TimeCode.responseTimeArrLong.sum() / clientQuantity).toString())

            println("CSV written successfully!")
        } catch (e: Exception) {
            println("Error while writing CSV!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

    fun writeNewCsvThroughput (fileSize: Int, responseTime: Long) {
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter("TestThroughput.csv")

            fileWriter.append(csvHeaderForThroughput)
            fileWriter.append('\n')
            fileWriter.append((fileSize/1000).toString())
            fileWriter.append(',')
            fileWriter.append(responseTime.toString())
            fileWriter.append(',')
            fileWriter.append((((fileSize/1000).toFloat())/(responseTime.toFloat()/1000)).toString())

            println("CSV written successfully!")
        } catch (e: Exception) {
            println("Error while writing CSV!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

    fun writeExistingCsvThroughput (csvName: String?, fileSize: Int, responseTime: Long) {
        var fileWriter: FileWriter? = null
        println("$csvName is opened")
        try {
            fileWriter = FileWriter(csvName, true)

            fileWriter.append('\n')
            fileWriter.append((fileSize/1000).toString())
            fileWriter.append(',')
            fileWriter.append(responseTime.toString())
            fileWriter.append(',')
            fileWriter.append((((fileSize/1000).toFloat())/(responseTime.toFloat()/1000)).toString())

            println("CSV written successfully!")
        } catch (e: Exception) {
            println("Error while writing CSV!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }
}