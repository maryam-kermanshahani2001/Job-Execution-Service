package org.ce.cc.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ce.cc.core.entity.Upload;
import org.ce.cc.core.entity.enumeration.Language;
import org.ce.cc.gateway.service.DatabaseService;
import org.ce.cc.core.service.StorageServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@Slf4j
@RestController
@RequestMapping("/upload")
@Tag(name = "Upload File Controller")
@RequiredArgsConstructor
public class UploadFileController {

    private final StorageServiceImpl storageService;
    private final DatabaseService databaseService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Endpoint for upload a file")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = Boolean.class)))
//    public boolean upload(@RequestParam MultipartFile file, @RequestParam UploadDto uploadDto) {
    public Upload upload(@RequestParam MultipartFile file, @RequestParam String email,
                         @RequestParam(required = false) String input, @RequestParam Language language, @RequestParam int enable) {

//        System.out.println("Upload controller: file with name" + file.getName() + " uploaded");
        // TODO user chetori filesho beshnase?
        Upload uploadedData = databaseService.uploadInfoToDb(email, input, language, enable);
        long uploadId = uploadedData.getUploadId();
        System.out.println("Upload controller-database upload: file with uploadId " + uploadId + "enable " + enable + "lang " + language);
//        System.out.println("upload data result: " + );

        String uploadObjectResult = storageService.uploadFileToS3(file, uploadId);
        System.out.println("Upload controller-s3 upload result: " + uploadObjectResult);
        return uploadedData;
    }
}
