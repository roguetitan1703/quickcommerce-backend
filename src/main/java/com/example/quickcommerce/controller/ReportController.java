
package com.example.quickcommerce.controller;

import com.example.quickcommerce.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/reports")
    public ResponseEntity<?> getReports(@RequestParam String reportType, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(reportService.generateReport(reportType, startDate, endDate));
    }
}
