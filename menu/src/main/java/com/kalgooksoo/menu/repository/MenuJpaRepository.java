package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuJpaRepository implements MenuRepository {

    private final EntityManager em;

    @Override
    public Menu save(Menu menu) {
        Assert.notNull(menu, "menu는 null이 될 수 없습니다");
        if (menu.getId() == null) {
            em.persist(menu);
        } else {
            em.merge(menu);
        }
        return menu;
    }

    @Override
    public Optional<Menu> findById(String id) {
        Assert.notNull(id, "id는 null이 될 수 없습니다");
        return Optional.ofNullable(em.find(Menu.class, id));
    }

    @Override
    public List<Menu> findAll() {
        return em.createQuery("select menu from Menu menu", Menu.class).getResultList();
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "id는 null이 될 수 없습니다");
        Menu menu = em.find(Menu.class, id);
        if (menu != null) {
            em.remove(menu);
        } else {
            throw new NoSuchElementException("메뉴를 찾을 수 없습니다.");
        }
    }
}