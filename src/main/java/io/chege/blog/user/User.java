package io.chege.blog.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.chege.blog.SpringContext;
import io.chege.blog.auth.PasswordConfig;
import io.chege.blog.authority.Authority;
import io.chege.blog.authority.AuthorityRepository;
import io.chege.blog.post.Post;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    private String id;
    private String name;

    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Value("true")
    private Boolean status;
    @Value("user")
    private String type;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    public User(String name, String email, String password, Boolean status, String type) {
        this.name = name;
        this.email = email;
        this.password = new PasswordConfig().passwordEncoder().encode(password);
        this.status = status;
        this.type = type;

    }

    public User() {
    }

    @PrePersist
    protected void onCreate() {
        this.id = java.util.UUID.randomUUID().toString();
        if (this.type == null) {
            this.type = "user";
        }

        switch (this.type){
            case "user":
                Authority aUser = new Authority("user", "ROLE_USER", true);
                this.authorities = new HashSet<>(Arrays.asList(aUser));
                aUser.setUser(this);
                break;
            case "admin":
                Authority aAdmin = new Authority("admin", "ROLE_ADMIN", true);
                this.authorities = new HashSet<>(Arrays.asList(aAdmin));
                aAdmin.setUser(this);
                break;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new PasswordConfig().passwordEncoder().encode(password);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
}
