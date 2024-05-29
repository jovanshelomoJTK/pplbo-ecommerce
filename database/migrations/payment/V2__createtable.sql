CREATE TYPE payment_method_enum AS ENUM (
    'Credit/Debit Cards',
    'Digital Wallets',
    'Bank Transfers'
);

CREATE TABLE IF NOT EXISTS payment (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customer_id INT NOT NULL,
    order_id INT NOT NULL,
    total INT NOT NULL,
    status VARCHAR NOT NULL,
    paymentMethod payment_method_enum NOT NULL
);
