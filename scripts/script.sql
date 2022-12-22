/*
 * Drop tables for regeneration
 */

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS users;


/*
 * Generate table 'users' for testing
 */

CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY,
	email VARCHAR(200) NOT NULL UNIQUE,
	"password" VARCHAR(200) NOT NULL,
	manager BOOLEAN DEFAULT FALSE,
	session_id VARCHAR(200) DEFAULT '-1'
);

INSERT INTO users (email, "password", manager) VALUES ('james@gmail.com', 'password', TRUE);
INSERT INTO users (email, "password") VALUES ('mary@gmail.com', 'password');
INSERT INTO users (email, "password") VALUES ('robert@gmail.com', 'password');
INSERT INTO users (email, "password", manager) VALUES ('patricia@gmail.com', 'password', TRUE);
INSERT INTO users (email, "password") VALUES ('john@gmail.com', 'password');
INSERT INTO users (email, "password") VALUES ('jennifer@gmail.com', 'password');


/*
 * Generate table 'tickets' for testing
 */

CREATE TABLE IF NOT EXISTS tickets (
	id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	amount FLOAT8 NOT NULL,
	description VARCHAR(1000) NOT NULL,
	status VARCHAR(20) NOT NULL DEFAULT 'Pending',
	CONSTRAINT fk_tickets_users FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO tickets (user_id, amount, description) VALUES (2, 1234.56, 'Food');
INSERT INTO tickets (user_id, amount, description) VALUES (2, 2345.67, 'Travel');
INSERT INTO tickets (user_id, amount, description, status) VALUES (2, 3456.78, 'Hotel', 'Approved');
INSERT INTO tickets (user_id, amount, description, status) VALUES (2, 4567.89, 'More Food', 'Denied');
INSERT INTO tickets (user_id, amount, description) VALUES (3, 1234.56, 'Food');
INSERT INTO tickets (user_id, amount, description) VALUES (5, 2345.67, 'Travel');


/*
 * Show all current tables
 */

SELECT * FROM users;
SELECT * FROM tickets;




