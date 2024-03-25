package com.kalgooksoo.menu.config;

import com.kalgooksoo.core.oas.OpenApiDocsWriter;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Profile("!prod")
@Configuration
public class ApplicationRunner implements CommandLineRunner {

    private final String applicationName;

    private final String rootUri;

    private final MenuRepository menuRepository;

    public ApplicationRunner(
            @Value("${spring.application.name}") String applicationName,
            @Value("${server.address:127.0.0.1}") String domain,
            @Value("${server.port}") int port,
            @Lazy MenuRepository menuRepository
    ) {
        this.applicationName = applicationName;
        this.rootUri = String.format("http://%s:%d", domain, port);
        this.menuRepository = menuRepository;
    }

    @Override
    public void run(String... args) {
        OpenApiDocsWriter openApiDocsWriter = new OpenApiDocsWriter(rootUri, applicationName);
        openApiDocsWriter.write();
        generateTestMenus();
    }

    private void generateTestMenus() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            final String createdBy = "admin";
            Menu createdMenu1 = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
            Menu savedParent = menuRepository.save(createdMenu1);
            Menu createMenu2 = Menu.create("하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedParent.getId(), createdBy);
            Menu savedChild = menuRepository.save(createMenu2);
            Menu createMenu3 = Menu.create("하위하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedChild.getId(), createdBy);
            menuRepository.save(createMenu3);
        });
    }
}
