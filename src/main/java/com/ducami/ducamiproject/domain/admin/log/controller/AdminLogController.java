package com.ducami.ducamiproject.domain.admin.log.controller;

import com.ducami.ducamiproject.domain.admin.log.dto.response.AdminLogResponse;
import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.global.data.ApiResponse;
import com.ducami.ducamiproject.global.data.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/log")
@RequiredArgsConstructor
public class AdminLogController {
    private final AdminLogService adminLogService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<PageResponse<AdminLogResponse>>> getAllLogs(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="12") int size
    ) {
        Page<AdminLogResponse> pages = adminLogService.findAll(page, size);
        return ApiResponse.ok(PageResponse.from(pages));
    }
}
