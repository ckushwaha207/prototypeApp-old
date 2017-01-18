package com.app.proto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A MenuCategory.
 */
@Entity
@Table(name = "menu_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MenuCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MenuItem> items = new HashSet<>();

    @ManyToOne
    private Menu menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MenuCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MenuItem> getItems() {
        return items;
    }

    public MenuCategory items(Set<MenuItem> menuItems) {
        this.items = menuItems;
        return this;
    }

    public MenuCategory addItems(MenuItem menuItem) {
        items.add(menuItem);
        menuItem.setCategory(this);
        return this;
    }

    public MenuCategory removeItems(MenuItem menuItem) {
        items.remove(menuItem);
        menuItem.setCategory(null);
        return this;
    }

    public void setItems(Set<MenuItem> menuItems) {
        this.items = menuItems;
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuCategory menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuCategory menuCategory = (MenuCategory) o;
        if (menuCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, menuCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuCategory{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
