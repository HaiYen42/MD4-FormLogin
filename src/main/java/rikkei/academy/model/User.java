package rikkei.academy.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String avatar= "https://firebasestorage.googleapis.com/v0/b/haiyen-e3d6b.appspot.com/o/avatar.jpg?alt=media&token=7229b74c-6455-4c1e-9e9d-f553ce5f2d4e";
    private Set<Role> roleSet = new HashSet<>();

    public User() {
    }

    public User(int id, String name, String username, String email, String password, String avatar, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.roleSet = roleSet;
    }

    public User(String name, String username, String email, String password, Set<Role> roleSet) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleSet = roleSet;
    }

    public User(int id, String name, String avatar, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.roleSet = roleSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roleSet=" + roleSet +
                '}';
    }
}
