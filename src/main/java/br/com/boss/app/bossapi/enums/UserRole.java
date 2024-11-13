package br.com.boss.app.bossapi.enums;

public enum UserRole {
    ADMIN,
    USER;

    public static UserRole fromString(String role) {
        return UserRole.valueOf(role.toUpperCase());
    }

    @Override
    public String toString() {
        return name();
    }
}
