CREATE TABLE IF NOT EXISTS app_user (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS todo (
    todo_id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    target_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    user_id BIGINT REFERENCES app_user(user_id)
);