DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS todos CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP,
    completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_id ON todos(user_id);

SELECT constraint_name
FROM information_schema.table_constraints
WHERE table_name = 'todos' AND constraint_type = 'FOREIGN KEY';

ALTER TABLE todos DROP CONSTRAINT IF EXISTS fk_todos_user_id;

ALTER TABLE todos ADD FOREIGN KEY (user_id)
REFERENCES users;

