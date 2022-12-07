-- Generate table 'users' for testing

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY,
	email VARCHAR(200) NOT NULL UNIQUE,
	"password" VARCHAR(200) NOT NULL,
	manager BOOLEAN DEFAULT FALSE
);

INSERT INTO users (email, "password", manager) VALUES ('james@gmail.com', 'password1', TRUE);
INSERT INTO users (email, "password") VALUES ('mary@gmail.com', 'password2');
INSERT INTO users (email, "password") VALUES ('robert@gmail.com', 'password3');
INSERT INTO users (email, "password", manager) VALUES ('patricia@gmail.com', 'password4', TRUE);
INSERT INTO users (email, "password") VALUES ('john@gmail.com', 'password5');
INSERT INTO users (email, "password") VALUES ('jennifer@gmail.com', 'password6');

SELECT * FROM users;