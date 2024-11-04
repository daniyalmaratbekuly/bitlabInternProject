
CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         created_time TIMESTAMP NOT NULL,
                         updated_time TIMESTAMP NOT NULL
);
INSERT INTO courses (name, description, created_time, updated_time) VALUES
                                                                        ('Java Developer', 'Course for learning Java programming', '2024-10-21 10:00:00', '2024-10-21 10:00:00'),
                                                                        ('Python Developer', 'Course for learning Python programming', '2024-10-21 10:10:00', '2024-10-21 10:10:00');