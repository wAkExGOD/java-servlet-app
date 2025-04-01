# Project Setup Guide

Follow these steps to properly configure the database schema and tables for your project.

---

## 🚀 Database
1\. **Ensure you have MySQL installed and running.**
2\. **Create the schema `user_database`:**
   ```sql
   CREATE SCHEMA user_database;
   ```
3\. **Create the table `user`:**
   ```sql
   CREATE TABLE user_database.user (
     id INT NOT NULL AUTO_INCREMENT,
     password VARCHAR(100) NOT NULL,
     email VARCHAR(50) NOT NULL,
     login VARCHAR(50) NOT NULL,
     status VARCHAR(45) NULL DEFAULT 'PENDING',
     confirmation_token VARCHAR(45) NULL DEFAULT NULL,
     PRIMARY KEY (id),
     UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
     UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE,
     UNIQUE INDEX login_UNIQUE (login ASC) VISIBLE
   );
   ```
4\. **Create the table `user_avatars`:**
   ```sql
   CREATE TABLE user_database.user_avatars (
     id int PRIMARY KEY AUTO_INCREMENT,
     user_id int NOT NULL,
     file_name VARCHAR(255) NOT NULL,
     file_path VARCHAR(255) NOT NULL,
     file_type VARCHAR(50) NOT NULL,
     upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES user(id)
   );
   ```
5\. **Create the table `post`:**
   ```sql
   CREATE TABLE user_database.post (
     id INT AUTO_INCREMENT PRIMARY KEY,
     title VARCHAR(255) NOT NULL,
     content TEXT NOT NULL,
     author_id INT,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );
   ```
