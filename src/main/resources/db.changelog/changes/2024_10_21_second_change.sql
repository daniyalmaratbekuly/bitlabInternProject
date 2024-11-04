
CREATE TABLE chapters (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          "order" INT NOT NULL,
                          created_time TIMESTAMP NOT NULL,
                          updated_time TIMESTAMP NOT NULL
);

INSERT INTO chapters (name, description, "order", created_time, updated_time) VALUES
    ('if-else conditions', 'Chapter about if-else conditions', 1, '2024-10-21 10:00:00', '2024-10-21 10:00:00');