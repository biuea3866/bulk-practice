package com.example.apiserver.presentation

import com.example.apiserver.application.RequestBulkApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BulkController(
    private val requestBulkApplication: RequestBulkApplication
) {
    @PostMapping(
        value = ["/request/bulk"],
        produces = ["application/json; charset=utf-8"]
    )
    @ResponseBody
    fun bulkRequest(@RequestBody body: RequestBulk): Long {
        return requestBulkApplication.requestBulk(body.applicantIds)
    }
}

data class RequestBulk(
    val applicantIds: List<Int>
)