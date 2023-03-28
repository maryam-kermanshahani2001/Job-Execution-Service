package org.ce.cc.executor.service;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.ce.cc.executor.invoker.dto.CodeXExecutionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// ...

@Service
@RequiredArgsConstructor
public class MailgunService {
    private final MailgunMessagesApi mailgunMessagesApi;
    private final String domain_name = "sandbox4af31400583f4507bef084bd0e2c0aab.mailgun.org";


//    public MessageResponse sendSimpleMessage(ResponseEntity<String> codexResponse, String receiverEmail) {
    public MessageResponse sendSimpleMessage(CodeXExecutionResponseDto codexResponse, String receiverEmail) {

        Message message = Message.builder()
//                .from("Cloud Computing Project<maryam.kermanshahani6079@sandbox4af31400583f4507bef084bd0e2c0aab.mailgun.org>")
                .from("maryam.kermanshahani6079@gmail.com")
                .to(receiverEmail)
                .subject("Job Execution Service")
                .text(codexResponse.toString())
                .build();

        return mailgunMessagesApi.sendMessage(domain_name, message);
    }
}
