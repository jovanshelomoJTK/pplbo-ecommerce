-- V2__create_promotion_and_product_tables.sql
-- Create the table for storing promotion information
CREATE TABLE promotion (
    id_promotion BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    discount_percentage DOUBLE
);

-- Create the join table for promotions and products
CREATE TABLE promotion_products (
    promotion_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (promotion_id, product_id),
    FOREIGN KEY (promotion_id) REFERENCES promotion(id_promotion) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id_product) ON DELETE CASCADE
);