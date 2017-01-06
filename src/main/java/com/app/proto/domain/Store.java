package com.app.proto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StoreBranch> branches = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StoreUser> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Store description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Store logoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Set<StoreBranch> getBranches() {
        return branches;
    }

    public Store branches(Set<StoreBranch> storeBranches) {
        this.branches = storeBranches;
        return this;
    }

    public Store addBranches(StoreBranch storeBranch) {
        branches.add(storeBranch);
        storeBranch.setStore(this);
        return this;
    }

    public Store removeBranches(StoreBranch storeBranch) {
        branches.remove(storeBranch);
        storeBranch.setStore(null);
        return this;
    }

    public void setBranches(Set<StoreBranch> storeBranches) {
        this.branches = storeBranches;
    }

    public Set<StoreUser> getUsers() {
        return users;
    }

    public Store users(Set<StoreUser> storeUsers) {
        this.users = storeUsers;
        return this;
    }

    public Store addUsers(StoreUser storeUser) {
        users.add(storeUser);
        storeUser.setStore(this);
        return this;
    }

    public Store removeUsers(StoreUser storeUser) {
        users.remove(storeUser);
        storeUser.setStore(null);
        return this;
    }

    public void setUsers(Set<StoreUser> storeUsers) {
        this.users = storeUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Store store = (Store) o;
        if (store.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Store{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", logoUrl='" + logoUrl + "'" +
            '}';
    }
}
