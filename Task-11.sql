CREATE DATABASE feedbackdb;

USE feedbackdb;

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    book_name VARCHAR(100) NOT NULL,
    feedback TEXT NOT NULL
);

