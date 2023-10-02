package dev.axgr

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import java.io.InputStream

@SpringBootApplication
class App {

  @Value("classpath:files/sample.txt")
  lateinit var resource: Resource

  companion object {
    private val log = LoggerFactory.getLogger(App::class.java)
  }

  @Bean
  fun run() = CommandLineRunner {
    log.info("From injected resource")
    read(resource.inputStream)

    log.info("From ClassPathResource")
    read(ClassPathResource("files/sample.txt").inputStream)

    log.info("From Java Class")
    read(javaClass.getResourceAsStream("/files/sample.txt"))

//    Will break when running from a jar
    log.info("File: ${resource.file}")
  }

  fun read(stream: InputStream?) {
    stream?.bufferedReader().use {
      log.info(it?.readText())
    }
  }

}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
