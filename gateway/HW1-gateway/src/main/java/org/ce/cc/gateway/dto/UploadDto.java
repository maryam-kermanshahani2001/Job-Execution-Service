package org.ce.cc.gateway.dto;

import org.ce.cc.core.entity.enumeration.Language;
import org.springframework.web.multipart.MultipartFile;

public record UploadDto(MultipartFile file, long uploadId, String email, String input, Language language, int enable) {

}
