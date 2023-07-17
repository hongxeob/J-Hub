package com.project.jhub.user.domain;

import com.project.jhub.comment.domain.Comment;
import com.project.jhub.global.domain.BaseEntity;
import com.project.jhub.post.domain.Post;
import com.project.jhub.user.dto.request.UserUpdateRequest;
import com.project.jhub.user.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    private String introduction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Builder
    public User(String username, String nickname, String introduction, UserRole userRole, List<Post> posts, List<Comment> comments) {
        this.username = username;
        this.nickname = nickname;
        this.introduction = introduction;
        this.userRole = userRole;
        this.posts = posts;
        this.comments = comments;
    }

    public void updateUser(UserUpdateRequest updateRequest) {
        this.nickname = updateRequest.getNickname();
        this.introduction = updateRequest.getIntroduction();
        this.userRole = updateRequest.getUserRole();
    }

    public UserResponse toDto() {
        return UserResponse.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .introduction(introduction)
                .userRole(userRole)
                .build();
    }
}
