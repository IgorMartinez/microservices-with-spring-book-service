CREATE TABLE books (
    id SERIAL,
    author TEXT NOT NULL,
    launch_date TIMESTAMP NOT NULL,
    price NUMERIC(65,2) NOT NULL,
    title TEXT NOT NULL,
    PRIMARY KEY (id)
);