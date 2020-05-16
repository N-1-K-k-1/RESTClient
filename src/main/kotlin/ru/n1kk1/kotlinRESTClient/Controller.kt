package ru.n1kk1.kotlinRESTClient

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.stage.Window
import org.springframework.stereotype.Component
import ru.n1kk1.kotlinRESTClient.clientService.*
import java.io.File
import java.util.*

@Component
class Controller {
    @FXML
    lateinit var postButton1: Button
    @FXML
    lateinit var getAllButton: Button
    @FXML
    lateinit var updateButton: Button
    @FXML
    lateinit var deleteButton: Button
    @FXML
    lateinit var all4Button: Button
    @FXML
    lateinit var unsetCorrIdButton: Button
    @FXML
    lateinit var throughputTestButton: Button
    @FXML
    lateinit var responseField: TextArea
    @FXML
    lateinit var enterIdField: TextField
    @FXML
    lateinit var enterIdLabel: Label
    @FXML
    lateinit var responseTimeText: Text
    @FXML
    lateinit var responseTime: Text
    @FXML
    lateinit var responseTimePerRequest: TextArea
    @FXML
    lateinit var minResponseText: Text
    @FXML
    lateinit var minResponse: Text
    @FXML
    lateinit var maxResponseText: Text
    @FXML
    lateinit var maxResponse: Text
    @FXML
    lateinit var correlationId: Text
    @FXML
    lateinit var fileSize: Text
    @FXML
    lateinit var throughputTest: Text
    @FXML
    lateinit var imageView: ImageView
    @FXML
    lateinit var responseThroughput: Text
    @FXML
    lateinit var image: Image
    @FXML
    lateinit var receivedImage: Text
    @FXML
    lateinit var chooseButton: Button
    @FXML
    lateinit var fileChooser: FileChooser
    @FXML
    lateinit var resetOpenedFile: Button
    @FXML
    lateinit var openedFile: Text

    private var action: Int = 0
    private var clientQuantity: Int = 0
    private var file: File? = null

    @FXML
    fun postClick1(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        action = 1
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        PostCustomer.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun getClick(event: ActionEvent) {
        responseTime.isVisible = true
        responseTimeText.isVisible = true
        minResponse.isVisible = true
        minResponseText.isVisible = true
        maxResponse.isVisible = true
        maxResponseText.isVisible = true
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        GetCustomer.unsetResponse()
        TimeCode.unsetCompanion()

        responseTimePerRequest.text = ""
        responseField.text = ""

        val thread = arrayOfNulls<Thread>(clientQuantity)

        for (i in 0 until clientQuantity) {
            thread[i] = Thread(GetCustomer(), "<Thread-$i>")
            thread[i]!!.start()
        }
        for (i in 0 until clientQuantity) {
            thread[i]?.join()
        }
        responseField.text = GetCustomer.responseXml
        for(i in 0 until clientQuantity) {
            responseTimePerRequest.text += TimeCode.responseTimeArr[i]
        }
        responseTimeText.isVisible = true
        responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/clientQuantity)}" + " ms"
        responseTime.fill = Color.ORANGE
        minResponseText.isVisible = true
        minResponse.text = TimeCode.responseMin.toString()
        minResponse.fill = Color.GREEN
        maxResponseText.isVisible = true
        maxResponse.text = TimeCode.responseMax.toString()
        maxResponse.fill = Color.RED
        correlationId.text = "CorrelationId is: ${GetCustomer.getCorrId()}"

        val csvWriter = WriteCSV()
        if (file != null) {
            csvWriter.writeExistingCsvResponse(file!!.name, "GET", clientQuantity)
        }
        else if (file == null) {
            csvWriter.writeNewCsvResponse("GET", clientQuantity)
        }
    }

    @FXML
    fun updateClick(event: ActionEvent) {
        responseTime.isVisible = true
        responseTimeText.isVisible = true
        minResponse.isVisible = true
        minResponseText.isVisible = true
        maxResponse.isVisible = true
        maxResponseText.isVisible = true
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        UpdateCustomer.unsetResponse()
        TimeCode.unsetCompanion()

        responseTimePerRequest.text = ""
        responseField.text = ""

        val thread = arrayOfNulls<Thread>(clientQuantity)

        for (i in 0 until clientQuantity) {
            thread[i] = Thread(UpdateCustomer(), "<Thread-$i>")
            thread[i]!!.start()
        }
        for (i in 0 until clientQuantity) {
            thread[i]?.join()
        }
        responseField.text = UpdateCustomer.response
        for(i in 0 until clientQuantity) {
            println("response time: ${TimeCode.responseTimeArr[i]}")
            responseTimePerRequest.text += TimeCode.responseTimeArr[i]
        }
        responseTimeText.isVisible = true
        responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/clientQuantity)}" + " ms"
        responseTime.fill = Color.ORANGE
        minResponseText.isVisible = true
        minResponse.text = TimeCode.responseMin.toString()
        minResponse.fill = Color.GREEN
        maxResponseText.isVisible = true
        maxResponse.text = TimeCode.responseMax.toString()
        maxResponse.fill = Color.RED
        correlationId.text = "CorrelationId is: ${UpdateCustomer.getCorrId()}"

        val csvWriter = WriteCSV()
        if (file != null) {
            csvWriter.writeExistingCsvResponse(file!!.name, "PUT", clientQuantity)
        }
        else if (file == null) {
            csvWriter.writeNewCsvResponse("PUT", clientQuantity)
        }
    }

    @FXML
    fun deleteClick(event: ActionEvent) {
        responseTime.isVisible = true
        responseTimeText.isVisible = true
        minResponse.isVisible = true
        minResponseText.isVisible = true
        maxResponse.isVisible = true
        maxResponseText.isVisible = true
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        DeleteCustomer.unsetResponse()
        TimeCode.unsetCompanion()

        responseTimePerRequest.text = ""
        responseField.text = ""

        val thread = arrayOfNulls<Thread>(clientQuantity)

        for (i in 0 until clientQuantity) {
            thread[i] = Thread(DeleteCustomer(), "<Thread-$i>")
            thread[i]!!.start()
        }
        for (i in 0 until clientQuantity) {
            thread[i]?.join()
        }
        responseField.text = DeleteCustomer.response
        for(i in 0 until clientQuantity) {
            responseTimePerRequest.text += TimeCode.responseTimeArr[i]
        }
        responseTimeText.isVisible = true
        responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/clientQuantity)}" + " ms"
        responseTime.fill = Color.ORANGE
        minResponseText.isVisible = true
        minResponse.text = TimeCode.responseMin.toString()
        minResponse.fill = Color.GREEN
        maxResponseText.isVisible = true
        maxResponse.text = TimeCode.responseMax.toString()
        maxResponse.fill = Color.RED
        correlationId.text = "CorrelationId is: ${DeleteCustomer.getCorrId()}"

        val csvWriter = WriteCSV()
        if (file != null) {
            csvWriter.writeExistingCsvResponse(file!!.name, "DELETE", clientQuantity)
        }
        else if (file == null) {
            csvWriter.writeNewCsvResponse("DELETE", clientQuantity)
        }
    }

    @FXML
    fun all4Click(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        action = 2
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        All4Requests.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun unsetCorrIdClick(event: ActionEvent) {
        PostCustomer.unsetCorrelationId()
        GetCustomer.unsetCorrelationId()
        UpdateCustomer.unsetCorrelationId()
        DeleteCustomer.unsetCorrelationId()
        All4Requests.unsetCorrelationId()
        correlationId.text = "CorrelationId is: ${All4Requests.getCorrId()}"
    }

    @FXML
    fun chooseClick(event: ActionEvent) {
        val ownerWindow: Window = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null)
        fileChooser = FileChooser()
        file = fileChooser.showOpenDialog(ownerWindow)
        openedFile.isVisible = true
        openedFile.text = "Opened file: ${file!!.name}"
    }

    @FXML
    fun resetOpenedFileClick(event: ActionEvent) {
        openedFile.isVisible = false
        openedFile.text = ""
        file = null
    }

    @FXML
    fun throughputTestClick(event: ActionEvent) {
        responseTime.isVisible = false
        responseTimeText.isVisible = false
        minResponse.isVisible = false
        minResponseText.isVisible = false
        maxResponse.isVisible = false
        maxResponseText.isVisible = false
        action = 3
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        enterIdLabel.text = "Enter file name:"
    }

    @FXML
    fun pressedEnter(event: ActionEvent) {
        when (action) {
            1 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                val thread = arrayOfNulls<Thread>(clientQuantity)

                for (i in 0 until clientQuantity) {
                    thread[i] = Thread(PostCustomer(), "<Thread-$i>")
                    thread[i]!!.start()
                }
                for (i in 0 until clientQuantity) {
                    thread[i]?.join()
                }
                for(i in 0 until clientQuantity) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseField.text = PostCustomer.response
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/clientQuantity)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString()
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString()
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${PostCustomer.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "POST", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("POST", clientQuantity)
                }

            }

            2 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                val thread = arrayOfNulls<Thread>(clientQuantity)

                for (i in 0 until clientQuantity) {
                    thread[i] = Thread(All4Requests(), "<Thread-$i>")
                    thread[i]!!.start()
                }
                for (i in 0 until clientQuantity) {
                    thread[i]?.join()
                }
                responseField.text = All4Requests.response
                for(i in 0 until clientQuantity) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/clientQuantity)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString()
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString()
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${All4Requests.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "ALL FOUR REQUESTS", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("ALL FOUR REQUESTS", clientQuantity)
                }

            }

            3 -> {
                val throughput = Throughput()
                fileSize.isVisible = true
                throughputTest.isVisible = true
                responseThroughput.isVisible = true
                receivedImage.isVisible = true

                val byteImage = throughput.testThroughput(enterIdField.text)
                image = Image(byteImage?.inputStream())
                imageView.image = image
                fileSize.text = "File size is: " + (byteImage?.size?.div(1000)).toString() + " Kbyte"
                responseThroughput.text = "Response time is: " + throughput.timeTest.toString() + " ms"
                throughputTest.text = "Throughput is: " + ((byteImage?.size?.div(1000))?.toFloat()?.div((throughput.timeTest.toFloat()/1000))) + " KB/s"

                val csvWriter = WriteCSV()
                if (file != null) {
                    byteImage?.size?.let { csvWriter.writeExistingCsvThroughput(file!!.name, it, throughput.timeTest) }
                }
                else if (file == null) {
                    byteImage?.size?.let { csvWriter.writeNewCsvThroughput(it, throughput.timeTest) }
                }
            }

            else -> {
                responseField.text = "Something went wrong"
                action = 0

                return
            }
        }

        enterIdField.opacity = 0.0
        enterIdLabel.opacity = 0.0
    }
}