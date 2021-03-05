package io.chege.blog.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.chege.blog.user.User;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
    @Column(name = "user_type")
    private String userType;
    private String authority;
    private Boolean status;

    public Authority() {
    }

    public Authority(String userType, String authority, Boolean status) {
        this.userType = userType;
        this.authority = authority;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @PrePersist
    public void onCreate(){
        this.id = java.util.UUID.randomUUID().toString();
    }
}
