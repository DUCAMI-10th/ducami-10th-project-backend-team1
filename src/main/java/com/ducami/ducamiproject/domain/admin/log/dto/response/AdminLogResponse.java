package com.ducami.ducamiproject.domain.admin.log.dto.response;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminLogResponse {
    private Long id;
    private AdminAction action;
    private String details;
    private LocalDateTime actionTime;
    private String actorName;
}
