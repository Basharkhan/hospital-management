package com.khan.hospital_management.controller;


import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BillDto>> createBill(@Valid @RequestBody BillRequest request) {
        BillDto bill = billService.createBill(request);

        ApiResponse<BillDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Bill created successfully",
                bill,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<BillDto>>> getAllBills(
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<BillDto> bills = billService.getAllBills(pageable);

        ApiResponse<Page<BillDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Bills retrieved successfully",
                bills,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BillDto>> getBillById(@PathVariable Long id) {
        BillDto bill = billService.getBillById(id);

        ApiResponse<BillDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Bill retrieved successfully",
                bill,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
