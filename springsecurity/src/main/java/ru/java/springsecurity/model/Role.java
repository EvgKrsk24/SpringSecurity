package ru.java.springsecurity.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

// роли приложения
public enum Role {
    USER(Set.of(Permission.DEVELOPERS_READ)),
    ADMIN(Set.of(Permission.DEVELOPERS_READ,Permission.DEVELOPERS_WRITE));

    private final Set<Permission> pemissions; // сопоставление ролей и разрешений

    <E> Role(Set<Permission> pemissions) { // конструктор сопоставления ролей
        this.pemissions=pemissions;
    }

    public Set<Permission> getPemissions() { // геттер мопоставления ролей
        return pemissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){ // связка Spring security с разрешениями в ПО
        return getPemissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
