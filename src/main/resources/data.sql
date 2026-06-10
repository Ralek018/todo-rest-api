INSERT INTO app_user (username, email, password, role, enabled)
VALUES ('admin', 'admin@mail.com', '$2a$10$gN9hx.d3J9w14ZEghQ2cQuP8JE8xndOagNMcUW7O4416v/zeF496u', 'ROLE_ADMIN', true)
ON CONFLICT (username) DO NOTHING;