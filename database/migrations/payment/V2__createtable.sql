-- Create the payment_method table
CREATE TABLE IF NOT EXISTS payment_method (
    id SERIAL PRIMARY KEY,
    method VARCHAR NOT NULL
);

-- Insert predefined payment methods
INSERT INTO payment_method (method) VALUES
('CREDIT_DEBIT_CARDS'),
('DIGITAL_WALLETS'),
('BANK_TRANSFERS');

-- Create the payment table
CREATE TABLE IF NOT EXISTS payment (
    kode_payment VARCHAR PRIMARY KEY,
    customer_id INT NOT NULL,
    order_id INT NOT NULL,
    status VARCHAR NOT NULL,
    payment_method_id INT NOT NULL,
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(id)
);