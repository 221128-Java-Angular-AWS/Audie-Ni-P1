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

INSERT INTO users (email, "password", manager) VALUES ('james@gmail.com', 'password1', TRUE);
INSERT INTO users (email, "password") VALUES ('mary@gmail.com', 'password2');
INSERT INTO users (email, "password") VALUES ('robert@gmail.com', 'password3');
INSERT INTO users (email, "password", manager) VALUES ('patricia@gmail.com', 'password4', TRUE);
INSERT INTO users (email, "password") VALUES ('john@gmail.com', 'password5');
INSERT INTO users (email, "password") VALUES ('jennifer@gmail.com', 'password6');


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

INSERT INTO tickets (user_id, amount, description) VALUES (2, 1234.56, 'I spent some money.');
INSERT INTO tickets (user_id, amount, description) VALUES (3, 2345.67, 'I also spent some money.');
INSERT INTO tickets (user_id, amount, description) VALUES (5, 3456.78, 'I might have also spent money.');
INSERT INTO tickets (user_id, amount, description) VALUES (6, 4567.89, 'I might have spent too much money.');


/*
 * Show all current tables
 */

SELECT * FROM users;
SELECT * FROM tickets;




