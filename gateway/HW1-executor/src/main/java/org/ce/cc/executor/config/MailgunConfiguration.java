package org.ce.cc.executor.config;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailgunConfiguration {
    private final String private_api_key = "lablab";

    @Bean
    public MailgunMessagesApi mailgunMessagesApi() {
        return MailgunClient.config(private_api_key)
                .createApi(MailgunMessagesApi.class);
    }
}
