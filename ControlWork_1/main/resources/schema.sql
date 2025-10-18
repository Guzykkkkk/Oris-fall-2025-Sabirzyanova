CREATE TABLE IF NOT EXISTS users (
                                               id SERIAL PRIMARY KEY,
                                               name VARCHAR(255) NOT NULL,
                                               email VARCHAR(255) UNIQUE NOT NULL,
                                               password VARCHAR(255) NOT NULL,
                                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
          );

CREATE TABLE IF NOT EXISTS tasks (
                                                      id SERIAL PRIMARY KEY,
                                                      title VARCHAR(255) NOT NULL,
                                                      description TEXT,
                                                      status VARCHAR(50) DEFAULT 'pending',
                                                      user_email VARCHAR(255) NOT NULL,
                                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                      updated_at TIMESTAMP,
                                                      FOREIGN KEY (user_email) REFERENCES users(email) ON DELETE CASCADE
                 )