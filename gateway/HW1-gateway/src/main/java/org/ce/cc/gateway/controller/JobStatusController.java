package org.ce.cc.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.ce.cc.gateway.dto.JobExecutionReportRequestDto;
import org.ce.cc.gateway.dto.JobExecutionReportResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ce.cc.gateway.service.DatabaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maryam Kermanshahani
 * @since 07.03.23
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/status")
@Tag(name = "Status Controller")
public class JobStatusController {
    private final DatabaseService databaseService;

    @PostMapping
    @Operation(summary = "Get report")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = JobExecutionReportResponseDto.class)))
    public JobExecutionReportResponseDto report(@RequestBody JobExecutionReportRequestDto requestDto) {
        System.out.println("StatusController: status " + requestDto.email() + "requested");
        if( databaseService.getResultsByEmail(requestDto.email()) == null)
            return null;
        return new JobExecutionReportResponseDto(databaseService.getResultsByEmail(requestDto.email()));
//        return new JobExecutionReportResponseDto(List.of(new Result()));
    }
}
