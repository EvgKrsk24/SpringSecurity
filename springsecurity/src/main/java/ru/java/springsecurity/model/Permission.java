package ru.java.springsecurity.model;

public enum Permission {   // разрешения
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write");


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
