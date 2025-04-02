-- Sample data initialization script for PostgreSQL database
-- This script will be automatically executed when the application starts if spring.jpa.hibernate.ddl-auto=create or create-drop

-- Clear existing data
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE products CASCADE;
TRUNCATE TABLE inventory CASCADE;
TRUNCATE TABLE cart_items CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE order_items CASCADE;
TRUNCATE TABLE categories CASCADE;

-- Insert sample users
INSERT INTO users (id, username, password, email, first_name, last_name, created_at, role)
VALUES 
(1, 'admin', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KzCM.', 'admin@example.com', 'Admin', 'User', CURRENT_TIMESTAMP, 'ADMIN'),
(2, 'user1', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KzCM.', 'user1@example.com', 'John', 'Doe', CURRENT_TIMESTAMP, 'USER'),
(3, 'user2', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KzCM.', 'user2@example.com', 'Jane', 'Smith', CURRENT_TIMESTAMP, 'USER');

-- Reset sequence
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

-- Insert product categories
INSERT INTO categories (id, name, description, image_url, created_at)
VALUES
(1, 'Dairy', 'Dairy products including milk, cheese, yogurt', '/images/categories/dairy.svg', CURRENT_TIMESTAMP),
(2, 'Fruits & Vegetables', 'Fresh fruits and vegetables', '/images/categories/fruits-vegetables.svg', CURRENT_TIMESTAMP),
(3, 'Bakery', 'Fresh bread and bakery items', '/images/categories/bakery.svg', CURRENT_TIMESTAMP),
(4, 'Snacks', 'Chips, biscuits, and other snack items', '/images/categories/snacks.svg', CURRENT_TIMESTAMP),
(5, 'Beverages', 'Drinks including tea, coffee, juices', '/images/categories/beverages.svg', CURRENT_TIMESTAMP),
(6, 'Household', 'Cleaning supplies and household items', '/images/categories/household.svg', CURRENT_TIMESTAMP),
(7, 'Personal Care', 'Personal hygiene and beauty products', '/images/categories/personal-care.svg', CURRENT_TIMESTAMP),
(8, 'Frozen Foods', 'Frozen meals and ingredients', '/images/categories/frozen-foods.svg', CURRENT_TIMESTAMP);

-- Reset sequence
SELECT SETVAL('categories_id_seq', (SELECT MAX(id) FROM categories));

-- Insert products
INSERT INTO products (id, name, description, price, discount_percent, category_id, image_url, created_at, is_active)
VALUES 
-- Dairy Products
(1, 'Fresh Milk 1L', 'Farm fresh cow milk', 3.99, 0, 1, '/images/products/milk.svg', CURRENT_TIMESTAMP, true),
(2, 'Cottage Cheese 200g', 'Creamy cottage cheese', 4.49, 5, 1, '/images/products/cottage-cheese.svg', CURRENT_TIMESTAMP, true),
(3, 'Cheese 250g', 'Aged cheddar cheese', 5.99, 0, 1, '/images/products/cheese.svg', CURRENT_TIMESTAMP, true),
(4, 'Curd 500g', 'Fresh dairy curd', 3.49, 0, 1, '/images/products/curd.svg', CURRENT_TIMESTAMP, true),
(5, 'Butter 100g', 'Salted butter', 2.99, 0, 1, '/images/products/butter.svg', CURRENT_TIMESTAMP, true),
(6, 'Greek Yogurt 400g', 'Creamy Greek yogurt', 4.49, 15, 1, '/images/products/yogurt.svg', CURRENT_TIMESTAMP, true),
(7, 'Almond Milk 1L', 'Unsweetened almond milk', 3.99, 0, 1, '/images/products/almond-milk.svg', CURRENT_TIMESTAMP, true),

-- Fruits & Vegetables
(8, 'Organic Bananas (6 pcs)', 'Organic bananas from Ecuador', 2.99, 10, 2, '/images/products/banana.jpg', CURRENT_TIMESTAMP, true),
(9, 'Avocado (2 pcs)', 'Ripe avocados', 3.59, 0, 2, '/images/products/avocado.jpg', CURRENT_TIMESTAMP, true),
(10, 'Organic Spinach 250g', 'Fresh organic spinach leaves', 3.29, 0, 2, '/images/products/spinach.jpg', CURRENT_TIMESTAMP, true),
(11, 'Tomatoes 500g', 'Farm fresh tomatoes', 2.49, 0, 2, '/images/products/tomatoes.svg', CURRENT_TIMESTAMP, true),
(12, 'Carrots 500g', 'Fresh orange carrots', 1.99, 0, 2, '/images/products/carrots.svg', CURRENT_TIMESTAMP, true),
(13, 'Apples 1kg', 'Sweet red apples', 4.99, 5, 2, '/images/products/apples.svg', CURRENT_TIMESTAMP, true),

-- Bakery
(14, 'Sourdough Bread', 'Freshly baked sourdough bread', 4.49, 0, 3, '/images/products/bread.svg', CURRENT_TIMESTAMP, true),
(15, 'Whole Wheat Bread', 'Nutritious whole wheat bread', 3.99, 0, 3, '/images/products/wheat-bread.svg', CURRENT_TIMESTAMP, true),
(16, 'Croissants (4 pcs)', 'Butter croissants', 5.99, 0, 3, '/images/products/croissants.svg', CURRENT_TIMESTAMP, true),
(17, 'Muffins (6 pcs)', 'Assorted muffins', 6.99, 10, 3, '/images/products/muffins.svg', CURRENT_TIMESTAMP, true),

-- Snacks
(18, 'Potato Chips 150g', 'Crunchy salted potato chips', 2.99, 0, 4, '/images/products/chips.svg', CURRENT_TIMESTAMP, true),
(19, 'Cookies 300g', 'Chocolate chip cookies', 3.49, 0, 4, '/images/products/cookies.svg', CURRENT_TIMESTAMP, true),
(20, 'Biscuits 250g', 'Assorted biscuits', 2.79, 0, 4, '/images/products/biscuits.svg', CURRENT_TIMESTAMP, true),
(21, 'Candy 200g', 'Mixed fruit candies', 2.49, 0, 4, '/images/products/candy.svg', CURRENT_TIMESTAMP, true),
(22, 'Popcorn 100g', 'Microwave popcorn', 1.99, 0, 4, '/images/products/popcorn.svg', CURRENT_TIMESTAMP, true),
(23, 'Granola Bars (6 pcs)', 'Oats and honey granola bars', 4.29, 5, 4, '/images/products/granola-bars.svg', CURRENT_TIMESTAMP, true),

-- Beverages
(24, 'Orange Juice 1L', 'Freshly squeezed orange juice', 3.49, 0, 5, '/images/products/juice.svg', CURRENT_TIMESTAMP, true),
(25, 'Soda 500ml', 'Refreshing cola', 1.79, 0, 5, '/images/products/soda.svg', CURRENT_TIMESTAMP, true),
(26, 'Tea 50 bags', 'Black tea bags', 3.99, 0, 5, '/images/products/tea.svg', CURRENT_TIMESTAMP, true),
(27, 'Coffee 250g', 'Ground coffee beans', 6.99, 10, 5, '/images/products/coffee.svg', CURRENT_TIMESTAMP, true),
(28, 'Mineral Water 1L', 'Natural spring water', 1.29, 0, 5, '/images/products/water.svg', CURRENT_TIMESTAMP, true),
(29, 'Energy Drink 250ml', 'Caffeinated energy drink', 2.49, 0, 5, '/images/products/energy-drink.svg', CURRENT_TIMESTAMP, true),

-- Household
(30, 'Laundry Detergent 2L', 'Concentrated laundry detergent', 7.99, 15, 6, '/images/products/detergent.svg', CURRENT_TIMESTAMP, true),
(31, 'Dishwasher Tablets (30 pcs)', 'Powerful dishwasher tablets', 8.99, 0, 6, '/images/products/dishwasher.svg', CURRENT_TIMESTAMP, true),
(32, 'Cleaning Cloth (3 pcs)', 'Microfiber cleaning cloths', 4.99, 0, 6, '/images/products/cleaning-cloth.svg', CURRENT_TIMESTAMP, true),
(33, 'Mop', 'Microfiber floor mop', 12.99, 10, 6, '/images/products/mop.svg', CURRENT_TIMESTAMP, true),
(34, 'Broom', 'Indoor sweeping broom', 9.99, 0, 6, '/images/products/broom.svg', CURRENT_TIMESTAMP, true),
(35, 'Toilet Paper 6 Rolls', 'Soft toilet paper rolls', 5.99, 0, 6, '/images/products/toilet-paper.svg', CURRENT_TIMESTAMP, true),

-- Personal Care
(36, 'Shampoo 500ml', 'Nourishing hair shampoo', 6.49, 0, 7, '/images/products/shampoo.svg', CURRENT_TIMESTAMP, true),
(37, 'Conditioner 500ml', 'Moisturizing hair conditioner', 6.49, 0, 7, '/images/products/conditioner.svg', CURRENT_TIMESTAMP, true),
(38, 'Soap 4 bars', 'Moisturizing bath soap', 3.99, 5, 7, '/images/products/soap.svg', CURRENT_TIMESTAMP, true),
(39, 'Toothpaste 100g', 'Mint fresh toothpaste', 2.99, 0, 7, '/images/products/toothpaste.svg', CURRENT_TIMESTAMP, true),
(40, 'Deodorant 50ml', 'Long-lasting deodorant', 3.79, 0, 7, '/images/products/deodorant.svg', CURRENT_TIMESTAMP, true),
(41, 'Body Lotion 250ml', 'Moisturizing body lotion', 5.49, 10, 7, '/images/products/body-lotion.svg', CURRENT_TIMESTAMP, true),

-- Frozen Foods
(42, 'Ice Cream 500ml', 'Vanilla ice cream', 4.99, 0, 8, '/images/products/ice-cream.svg', CURRENT_TIMESTAMP, true),
(43, 'Frozen Vegetables 500g', 'Mixed frozen vegetables', 3.49, 0, 8, '/images/products/frozen-vegetables.svg', CURRENT_TIMESTAMP, true),
(44, 'Frozen Pizza', 'Cheese and tomato pizza', 5.99, 10, 8, '/images/products/frozen-pizza.svg', CURRENT_TIMESTAMP, true),
(45, 'Frozen Meals 400g', 'Ready-to-heat pasta meal', 4.79, 0, 8, '/images/products/frozen-meals.svg', CURRENT_TIMESTAMP, true),
(46, 'Frozen Fish Fillets 300g', 'Cod fish fillets', 7.99, 5, 8, '/images/products/frozen-fish.svg', CURRENT_TIMESTAMP, true);

-- Reset sequence
SELECT SETVAL('products_id_seq', (SELECT MAX(id) FROM products));

-- Insert inventory data
INSERT INTO inventory (id, product_id, current_stock, min_stock_level, last_updated)
VALUES 
-- Dairy
(1, 1, 50, 10, CURRENT_TIMESTAMP),  -- Milk
(2, 2, 35, 8, CURRENT_TIMESTAMP),   -- Cottage Cheese
(3, 3, 40, 10, CURRENT_TIMESTAMP),  -- Cheese
(4, 4, 30, 8, CURRENT_TIMESTAMP),   -- Curd
(5, 5, 45, 12, CURRENT_TIMESTAMP),  -- Butter
(6, 6, 40, 10, CURRENT_TIMESTAMP),  -- Yogurt
(7, 7, 30, 10, CURRENT_TIMESTAMP),  -- Almond Milk

-- Fruits & Vegetables
(8, 8, 45, 15, CURRENT_TIMESTAMP),  -- Bananas
(9, 9, 20, 5, CURRENT_TIMESTAMP),   -- Avocado
(10, 10, 25, 8, CURRENT_TIMESTAMP), -- Spinach
(11, 11, 40, 10, CURRENT_TIMESTAMP), -- Tomatoes
(12, 12, 35, 10, CURRENT_TIMESTAMP), -- Carrots
(13, 13, 30, 10, CURRENT_TIMESTAMP), -- Apples

-- Bakery
(14, 14, 25, 8, CURRENT_TIMESTAMP),  -- Sourdough Bread
(15, 15, 30, 10, CURRENT_TIMESTAMP), -- Whole Wheat Bread
(16, 16, 20, 5, CURRENT_TIMESTAMP),  -- Croissants
(17, 17, 25, 8, CURRENT_TIMESTAMP),  -- Muffins

-- Snacks
(18, 18, 60, 20, CURRENT_TIMESTAMP), -- Potato Chips
(19, 19, 45, 15, CURRENT_TIMESTAMP), -- Cookies
(20, 20, 40, 12, CURRENT_TIMESTAMP), -- Biscuits
(21, 21, 50, 15, CURRENT_TIMESTAMP), -- Candy
(22, 22, 35, 10, CURRENT_TIMESTAMP), -- Popcorn
(23, 23, 42, 15, CURRENT_TIMESTAMP), -- Granola Bars

-- Beverages
(24, 24, 35, 10, CURRENT_TIMESTAMP), -- Orange Juice
(25, 25, 60, 20, CURRENT_TIMESTAMP), -- Soda
(26, 26, 40, 15, CURRENT_TIMESTAMP), -- Tea
(27, 27, 30, 10, CURRENT_TIMESTAMP), -- Coffee
(28, 28, 70, 25, CURRENT_TIMESTAMP), -- Water
(29, 29, 45, 15, CURRENT_TIMESTAMP), -- Energy Drink

-- Household
(30, 30, 25, 8, CURRENT_TIMESTAMP),  -- Detergent
(31, 31, 30, 10, CURRENT_TIMESTAMP), -- Dishwasher
(32, 32, 40, 12, CURRENT_TIMESTAMP), -- Cleaning Cloth
(33, 33, 15, 5, CURRENT_TIMESTAMP),  -- Mop
(34, 34, 18, 6, CURRENT_TIMESTAMP),  -- Broom
(35, 35, 55, 15, CURRENT_TIMESTAMP), -- Toilet Paper

-- Personal Care
(36, 36, 35, 10, CURRENT_TIMESTAMP), -- Shampoo
(37, 37, 30, 10, CURRENT_TIMESTAMP), -- Conditioner
(38, 38, 40, 15, CURRENT_TIMESTAMP), -- Soap
(39, 39, 45, 15, CURRENT_TIMESTAMP), -- Toothpaste
(40, 40, 38, 12, CURRENT_TIMESTAMP), -- Deodorant
(41, 41, 32, 10, CURRENT_TIMESTAMP), -- Body Lotion

-- Frozen Foods
(42, 42, 25, 8, CURRENT_TIMESTAMP),  -- Ice Cream
(43, 43, 30, 10, CURRENT_TIMESTAMP), -- Frozen Vegetables
(44, 44, 28, 10, CURRENT_TIMESTAMP), -- Frozen Pizza
(45, 45, 32, 12, CURRENT_TIMESTAMP), -- Frozen Meals
(46, 46, 20, 8, CURRENT_TIMESTAMP);  -- Frozen Fish

-- Reset sequence
SELECT SETVAL('inventory_id_seq', (SELECT MAX(id) FROM inventory)); 