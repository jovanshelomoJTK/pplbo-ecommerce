CREATE SEQUENCE payment_seq;

CREATE TYPE payment_method_enum AS ENUM (
    'CREDIT_DEBIT_CARDS',
    'DIGITAL_WALLETS',
    'BANK_TRANSFERS'
);

CREATE TABLE IF NOT EXISTS payment (
    id BIGINT PRIMARY KEY DEFAULT nextval('payment_seq'),
    customer_id INT NOT NULL,
    order_id INT NOT NULL,
    total INT NOT NULL,
    status VARCHAR NOT NULL,
    payment_method payment_method_enum NOT NULL
);
