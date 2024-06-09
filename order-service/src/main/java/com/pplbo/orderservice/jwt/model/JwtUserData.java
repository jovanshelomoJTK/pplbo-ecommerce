package com.pplbo.orderservice.jwt.model;

public class JwtUserData {

    public enum Role {
        ADMIN, CUSTOMER
    }

    private String id;
    private String email;
    private Role role;
    private String name;

    public JwtUserData() {
    }

    public JwtUserData(String id, String email, Role role, String name) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JwtUserData{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
