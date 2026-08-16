CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,

    title VARCHAR(150) NOT NULL,

    description TEXT,

    status VARCHAR(30) NOT NULL,

    priority VARCHAR(30) NOT NULL,

    project_id BIGINT NOT NULL,

    assignee_id UUID,

    due_date DATE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_tasks_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id),

    CONSTRAINT fk_tasks_assignee
        FOREIGN KEY (assignee_id)
        REFERENCES users(id)
);