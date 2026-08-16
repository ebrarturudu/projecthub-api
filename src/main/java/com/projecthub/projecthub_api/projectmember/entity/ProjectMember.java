package com.projecthub.projecthub_api.projectmember.entity;

import com.projecthub.projecthub_api.User.entity.User;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "project_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_project_member",
                        columnNames = {"project_id", "user_id"}
                )
        }
)
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "project_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_project_member_project")
    )
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_project_member_user")
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectMemberRole role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectMemberRole getRole() {
        return role;
    }

    public void setRole(ProjectMemberRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
