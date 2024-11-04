
CREATE TABLE lessons (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         content TEXT,
                         "order" INT NOT NULL,
                         created_time TIMESTAMP NOT NULL,
                         updated_time TIMESTAMP NOT NULL
);

INSERT INTO lessons (name, description, content, "order", created_time, updated_time) VALUES
                                                                                          ('Lecture for if-else conditions', 'Lecture covering the basics of if-else conditions', 'Lecture content for if-else', 1, '2024-10-21 10:00:00', '2024-10-21 10:00:00'),
                                                                                          ('Practice for if-else conditions', 'Hands-on practice for if-else conditions', 'Practice content for if-else', 2, '2024-10-21 10:05:00', '2024-10-21 10:05:00');
