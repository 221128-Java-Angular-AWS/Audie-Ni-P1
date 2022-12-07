-- Initial setup of TABLE: tickets

DROP TABLE IF EXISTS tickets;

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
INSERT INTO tickets (user_id, amount, description) VALUES (6, 4567.89, 'I might have also spent money.');

SELECT * FROM tickets;