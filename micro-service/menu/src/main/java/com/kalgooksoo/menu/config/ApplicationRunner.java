package com.kalgooksoo.menu.config;

import com.kalgooksoo.core.oas.OpenApiDocsWriter;
import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.service.MenuService;
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

    private final MenuService menuService;

    public ApplicationRunner(
            @Value("${spring.application.name}") String applicationName,
            @Value("${server.address:127.0.0.1}") String domain,
            @Value("${server.port}") int port,
            @Lazy MenuService menuService
    ) {
        this.applicationName = applicationName;
        this.rootUri = String.format("http://%s:%d", domain, port);
        this.menuService = menuService;
    }

    @Override
    public void run(String... args) {
        OpenApiDocsWriter openApiDocsWriter = new OpenApiDocsWriter(rootUri, applicationName);
        openApiDocsWriter.write();
        generateTestMenus();
    }

    private void generateTestMenus() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
            Menu savedParent = menuService.create(createMenuCommand1);
            CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedParent.getId());
            Menu savedChild = menuService.create(createMenuCommand2);
            CreateMenuCommand createMenuCommand3 = new CreateMenuCommand("하위하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedChild.getId());
            menuService.create(createMenuCommand3);
        });
    }
}
