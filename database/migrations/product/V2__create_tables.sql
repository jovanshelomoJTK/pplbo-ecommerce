-- Create table Brand
CREATE TABLE IF NOT EXISTS Brand (
    brandId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    brandName VARCHAR(255) NOT NULL,
    logo VARCHAR(255)
);

-- Insert some initial data into the Brand table
INSERT INTO Brand (brandName, logo) VALUES ('Wardah', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7ltq6Ht0qPHRfDD97Xpz8ww2UmQDVGV0cGA&s');
INSERT INTO Brand (brandName, logo) VALUES ('Roma', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRh8oUz6VKvGww5yWZ8atq2yfmeChox1XCC7Q&s');

-- Create table Category
CREATE TABLE IF NOT EXISTS Category (
    categoryId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    categoryName VARCHAR(255) NOT NULL
);

-- Insert some initial data into the Category table
INSERT INTO Category (categoryName) VALUES ('Beauty & Care');
INSERT INTO Category (categoryName) VALUES ('Groceries');

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

-- Insert some initial data into the Product table
INSERT INTO Product (productName, price, stock, categoryId, brandId, productDesc, productImage) 
    VALUES ('Wardah Micellar Water', 30000, 75, 1, 1, 'Micellar pembersih dengan jutaan micelles pencerah yang membersihkan wajah dari make up, kotoran dan minyak berlebih dengan lembut.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQWNQYLb3QdcfRWCRbydQRMU9gBUK_-ZYAHw&s');
INSERT INTO Product (productName, price, stock, categoryId, brandId, productDesc, productImage) 
    VALUES ('Roma Malkist Kopyor', 7500, 100, 2, 2, 'Cemilan Enak dan Lezat Mengkonsumsi camilan alias ngemil sudah menjadi bagian dari gaya hidup masyarakat Indonesia.', 'https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full//93/MTA-2650529/roma_roma-malkist-kelapa-kopyor-252-gram_full02.jpg');

-- Create table Review
CREATE TABLE IF NOT EXISTS Review (
    reviewId BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    productId INT NOT NULL,
    review VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    recommendationReview VARCHAR(3) NOT NULL,
    reviewMedia VARCHAR(255) NOT NULL,
    reviewDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert some initial data into the Review table
INSERT INTO Review (productId, review, rating, recommendationReview, reviewMedia) 
    VALUES (2, 'Produk tidak sesuai', 2, 'yes', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWh-LLGqPQSkbQFDJ87T7JhmzsgEL675Dn4Q&s');