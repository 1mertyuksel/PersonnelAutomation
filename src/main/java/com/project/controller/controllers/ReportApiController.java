package com.project.controller.controllers;

import com.project.service.services.ReportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Raporlama ile ilgili API endpoint'leri.
 * Şirket verilerini CSV formatında dışarı aktarmak için kullanılır.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportApiController {

    @Autowired
    private ReportExportService reportExportService;

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportAllAsCsv() {
        byte[] data = reportExportService.exportAllAsCsv();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"raporlar.csv\"");

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}


