package org.ce.cc.executor.invoker;

import lombok.RequiredArgsConstructor;
import org.ce.cc.executor.invoker.dto.CodeXExecutionRequestDto;
import org.ce.cc.executor.invoker.dto.CodeXExecutionResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author import org.ce.cc.gateway. Ahmady
 * @since 3/20/2023
 */
@Component
@RequiredArgsConstructor
public class CodeXInvoker {

    private static final String CODE_X_URL = "https://api.codex.jaagrav.in";
    private final RestTemplate restTemplate;

    public CodeXExecutionResponseDto executeCode(CodeXExecutionRequestDto requestDto) throws UnsupportedEncodingException {
        Map<String, String> queryParams = Map.of("code", requestDto.code(),
                "language", requestDto.language(), "input", requestDto.input());
        ResponseEntity<CodeXExecutionResponseDto> response = restTemplate.postForEntity(CODE_X_URL, getUrlEncodedEntity(queryParams), CodeXExecutionResponseDto.class);
        return response.getBody();
    }


    private HttpEntity<String> getUrlEncodedEntity(Map<String, String> fBodies) throws UnsupportedEncodingException {
        Set<String> bodyKeys = fBodies.keySet();
        List<String> paramList = new ArrayList<>();
        for (String key : bodyKeys) {
            paramList.add(key + "=" + URLEncoder.encode(fBodies.get(key), StandardCharsets.UTF_8.toString()));
        }

        String formBody;
        StringBuilder body = new StringBuilder();
        paramList.forEach(param -> {
            if (body.length() == 0) {
                body.append(param);
            } else {
                body.append("&").append(param);
            }
        });
        formBody = body.toString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(body.length());
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(formBody, httpHeaders);
    }

}
