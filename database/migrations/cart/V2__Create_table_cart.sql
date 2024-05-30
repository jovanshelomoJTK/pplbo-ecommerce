-- V2__Create_table_cart.sql
CREATE TABLE IF NOT EXISTS Cart (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

-- Insert some initial data into the Cart table
INSERT INTO Cart (product_id, user_id, quantity, price) VALUES ('Product-1', "User-1", 2, 100.0);
INSERT INTO Cart (product_id, user_id, quantity, price) VALUES ('Product-2', "User-2", 1, 200.0);
INSERT INTO Cart (product_id, user_id, quantity, price) VALUES ('Product-3', "User-3", 3, 50.0);
