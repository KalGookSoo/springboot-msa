package com.kalgooksoo.menu.config;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.service.MenuService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.stream.IntStream;

@Configuration
public class MenuApplicationRunner implements CommandLineRunner {

    private final MenuService menuService;

    public MenuApplicationRunner(@Lazy MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void run(String... args) {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
            Menu savedParent = menuService.create(createMenuCommand1);
            CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedParent.getId(), "anonymous");
            Menu savedChild = menuService.create(createMenuCommand2);
            CreateMenuCommand createMenuCommand3 = new CreateMenuCommand("하위하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedChild.getId(), "anonymous");
            menuService.create(createMenuCommand3);
        });
    }
}
