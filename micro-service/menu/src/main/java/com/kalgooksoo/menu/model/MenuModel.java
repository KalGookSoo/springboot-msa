package com.kalgooksoo.menu.model;

import com.kalgooksoo.menu.domain.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Relation(itemRelation = "menu", collectionRelation = "menus")
public class MenuModel extends EntityModel<MenuModel> {

    private String id;
    private String name;
    private String url;
    private String parentId;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static MenuModel of(Menu menu) {
        MenuModel model = new MenuModel();
        model.id = menu.getId();
        model.name = menu.getName();
        model.url = menu.getUrl();
        model.parentId = menu.getParentId();
        model.createdBy = menu.getCreatedBy();
        model.createdAt = menu.getCreatedAt();
        model.modifiedAt = menu.getModifiedAt();
        return model;
    }

}
