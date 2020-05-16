package ru.n1kk1.kotlinRESTClient

import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class KotlinRESTClient : Application() {
    private lateinit var springContext: ConfigurableApplicationContext
    private lateinit var root: Parent

    override fun init() {
        springContext = runApplication<KotlinRESTClient>()
        root = FXMLLoader(javaClass.classLoader.getResource("sample.fxml"))
                .apply {
                    setControllerFactory { springContext.getBean(it) }
                }
                .load()
    }

    override fun start(primaryStage: Stage?) {
        primaryStage?.apply {
            title = "REST Client"
            scene = Scene(root)
        }?.show() ?: throw IllegalStateException("Failed to show primary stage")
    }

    override fun stop() {
        springContext.stop()
    }
}

fun main(args: Array<String>) {
    launch(KotlinRESTClient::class.java, *args)
}