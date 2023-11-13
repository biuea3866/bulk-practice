package com.example.apiserver.application

import com.example.apiserver.domain.RequestBulkService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RequestBulkApplication(
    private val requestBulkService: RequestBulkService
) {
    @Transactional
    fun requestBulk(applicantIds: List<Int>): Long {
        return requestBulkService.execute(applicantIds)
    }
}