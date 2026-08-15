CREATE TABLE project_members (
    id BIGSERIAL PRIMARY KEY,

    project_id BIGINT NOT NULL,
    user_id UUID NOT NULL,

    role VARCHAR(30) NOT NULL,

    CONSTRAINT fk_project_member_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_project_member_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_project_member
        UNIQUE (project_id, user_id)
);