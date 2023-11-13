package com.example.workerserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkerServerApplication

fun main(args: Array<String>) {
    runApplication<WorkerServerApplication>(*args)
}
