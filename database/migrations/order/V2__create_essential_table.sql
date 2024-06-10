-- Create the shippings table if it doesn't exist
CREATE TABLE IF NOT EXISTS shippings (
    shipping_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shipping_name VARCHAR(255) NOT NULL,
    shipping_price DOUBLE NOT NULL,
    shipping_status VARCHAR(255) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL
);

-- Create the customers table if it doesn't exist
CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

-- Create the orders table if it doesn't exist
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    total_price DOUBLE NOT NULL,
    shipping_id BIGINT,
    customer_id BIGINT,
    payment_id BIGINT,
    CONSTRAINT fk_shipping
        FOREIGN KEY (shipping_id) 
        REFERENCES shippings (shipping_id)
        ON DELETE SET NULL,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id) 
        REFERENCES customers (customer_id)
        ON DELETE SET NULL,
    CONSTRAINT unique_customer_id UNIQUE (customer_id)
);

-- Create the order_line_items table if it doesn't exist
CREATE TABLE IF NOT EXISTS order_line_items (
    order_line_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    product_id INT NOT NULL,
    order_id BIGINT,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id) 
        REFERENCES orders (order_id)
        ON DELETE CASCADE
);

