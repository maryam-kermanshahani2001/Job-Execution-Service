package org.ce.cc.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ce.cc.core.entity.Upload;
import org.ce.cc.gateway.service.DatabaseService;
import org.ce.cc.gateway.service.RabbitMQSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/execute")
@Tag(name = "Execute File Controller")
public class JobExecutionController {
    private final DatabaseService databaseService;
    private final RabbitMQSenderService rabbitMQSenderService;

    @PostMapping
    @Operation(summary = "Endpoint for Execute a file")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public String execute(@RequestBody long uploadId) {
        Upload uploadDb = databaseService.getUploadDatabaseById(uploadId);
        if (uploadDb == null) {
            System.out.println("JobExecController: UploadId not found.");
            return "JobExecController: UploadId not found";
        }

        int enable = uploadDb.getEnable();
        if (enable == 0) {
            //TODO send a unique id for execute file
            rabbitMQSenderService.send(uploadId);
            return "JobExecController: message sent to rabbitmq";
        } else {
            // TODO forbid executing how to show it to user
//            System.out.println("For executing you have to set enable = 0");
            return "JobExecController: For executing you have to set enable = 0";
        }
    }
}
