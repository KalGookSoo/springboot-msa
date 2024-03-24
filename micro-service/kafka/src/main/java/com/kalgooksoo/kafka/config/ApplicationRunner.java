package com.kalgooksoo.kafka.config;

import com.kalgooksoo.core.oas.OpenApiDocsWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Configuration
public class ApplicationRunner implements CommandLineRunner {

    private final String applicationName;

    private final String rootUri;

    public ApplicationRunner(
            @Value("${spring.application.name}") String applicationName,
            @Value("${server.address:127.0.0.1}") String domain,
            @Value("${server.port}") int port
    ) {
        this.applicationName = applicationName;
        this.rootUri = String.format("http://%s:%d", domain, port);
    }

    @Override
    public void run(String... args) {
        OpenApiDocsWriter openApiDocsWriter = new OpenApiDocsWriter(rootUri, applicationName);
        openApiDocsWriter.write();
    }

}
