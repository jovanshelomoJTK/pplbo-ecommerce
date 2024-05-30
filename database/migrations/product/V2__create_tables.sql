-- Create table Brand
CREATE TABLE IF NOT EXISTS Brand (
    brandId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    brandName VARCHAR(255) NOT NULL,
    logo VARCHAR(255)
);

-- Create table Category
CREATE TABLE IF NOT EXISTS Category (
    categoryId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    categoryName VARCHAR(255) NOT NULL
);

-- Create table Product
CREATE TABLE IF NOT EXISTS Product (
    productId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    productName VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL,
    categoryId INT NOT NULL,
    brandId INT NOT NULL,
    productDesc VARCHAR(255) NOT NULL,
    productImage VARCHAR(255) NOT NULL
);

-- Create ENUM type for recommendationReview
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'recommendation_review_enum') THEN
        CREATE TYPE recommendation_review_enum AS ENUM ('yes', 'no');
    END IF;
END $$;

-- Create table Review
CREATE TABLE IF NOT EXISTS Review (
    reviewId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    productId INT NOT NULL,
    review VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    recommendationReview recommendation_review_enum NOT NULL,
    reviewMedia VARCHAR(255) NOT NULL,
    reviewDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert some initial data into the Review table
INSERT INTO Review (productId, review, rating, recommendationReview, reviewMedia) 
    VALUES (2, 'Produk tidak sesuai', 2, 'no', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWh-LLGqPQSkbQFDJ87T7JhmzsgEL675Dn4Q&s');
