CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_id INT REFERENCES users(id),
    role_id INT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

-- Inserindo exemplos de dados
INSERT INTO users (username, password, enabled) VALUES 
('admin', '$2a$10$EixZaYVK1fsbw1Zfbx3OPe.F0ECpl.FiTUVXUw0B.t8sphqFP/Uem', true), -- Senha: password
('user', '$2a$10$EixZaYVK1fsbw1Zfbx3OPe.F0ECpl.FiTUVXUw0B.t8sphqFP/Uem', true); -- Senha: password

INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- Admin com ROLE_ADMIN
(2, 2); -- User com ROLE_USER
