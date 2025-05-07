package knu.knu2025scdpteam06backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${moki.api.url}"
    String mokiApiUrl;

    @Bean
    public ReactorResourceFactory resourceFactory( {
        ReactorResourceFactory factory = new ReactorResourceFactory(;
        factory.setUseGlobalResources(false;
        return factory;
    }

    @Bean
    public WebClient webClient({

        return WebClient.builder(
                .baseUrl(mokiApiUrl
                .build(;
    }
}
