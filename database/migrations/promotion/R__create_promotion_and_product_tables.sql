-- Create the table for storing promotion information
CREATE TABLE IF NOT EXISTS promotion (
    id_promotion BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    discount_percentage DOUBLE PRECISION,
    CHECK (status IN ('INACTIVE', 'ACTIVE', 'EXPIRED')),
    CHECK (type IN ('DISCOUNT', 'FREESHIPPING'))
);

-- Create the join table for promotions and products
CREATE TABLE IF NOT EXISTS promotion_products (
    promotion_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (promotion_id, product_id),
    FOREIGN KEY (promotion_id) REFERENCES promotion(id_promotion) ON DELETE CASCADE
);