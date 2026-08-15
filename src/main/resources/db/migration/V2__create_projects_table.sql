CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(1000),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);