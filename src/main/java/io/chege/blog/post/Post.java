package io.chege.blog.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.chege.blog.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="posts")
@DynamicUpdate
public class Post {

    @Id
    private String id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userId;
    private Boolean status;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Post() {
    }

    public Post(String title, String content, User user, Boolean status) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate(){
        this.id = java.util.UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", user_id='" + userId + '\'' +
                ", status=" + status +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
