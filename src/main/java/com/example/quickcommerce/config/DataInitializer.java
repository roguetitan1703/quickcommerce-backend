package com.example.quickcommerce.config;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.model.User;
import com.example.quickcommerce.repository.ProductRepository;
import com.example.quickcommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {
        private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

        @Bean
        public CommandLineRunner initUsers(UserRepository userRepository) {
                return args -> {
                        // Check if we need to initialize users
                        long count = userRepository.count();
                        logger.info("DataInitializer: Current user count = " + count);

                        if (count == 0) {
                                logger.info("DataInitializer: Creating test users...");

                                // Create test users
                                List<User> users = new ArrayList<>();

                                // Admin user
                                User admin = new User();
                                admin.setUsername("admin@example.com");
                                admin.setPassword("admin123");
                                users.add(admin);

                                // Regular user
                                User user = new User();
                                user.setUsername("user@example.com");
                                user.setPassword("password");
                                users.add(user);

                                // Test user with simple credentials
                                User testUser = new User();
                                testUser.setUsername("test");
                                testUser.setPassword("test");
                                users.add(testUser);

                                try {
                                        List<User> savedUsers = userRepository.saveAll(users);
                                        logger.info("DataInitializer: Created {} test users", savedUsers.size());

                                        // Log the created users for testing purposes
                                        for (User savedUser : savedUsers) {
                                                logger.info("Created user: {}, id: {}",
                                                                savedUser.getUsername(),
                                                                savedUser.getUserId());
                                        }
                                } catch (Exception e) {
                                        logger.error("DataInitializer: Error creating test users", e);
                                }
                        } else {
                                logger.info("DataInitializer: Users already exist, skipping initialization");
                        }
                };
        }

        @Bean
        public CommandLineRunner initData(ProductRepository productRepository) {
                return args -> {
                        // Check if we need to initialize data
                        long count = productRepository.count();
                        logger.info("DataInitializer: Current product count = " + count);

                        if (count == 0) {
                                logger.info("DataInitializer: Initializing database with sample products...");

                                LocalDateTime now = LocalDateTime.now();

                                // Create products by category as requested
                                List<Product> products = new ArrayList<>();

                                // Dairy category
                                products.add(createProduct("Milk", "Fresh whole milk, pasteurized and homogenized",
                                                "Dairy", 55.00, 100, "/images/products/milk.svg", now));
                                products.add(createProduct("Cottage Cheese", "Fresh cottage cheese, rich in protein",
                                                "Dairy", 120.00, 50, "/images/products/cottage-cheese.svg", now));
                                products.add(createProduct("Cheese", "Processed cheese slices, perfect for sandwiches",
                                                "Dairy", 150.00, 60, "/images/products/cheese.svg", now));
                                products.add(createProduct("Curd", "Thick and creamy natural curd",
                                                "Dairy", 40.00, 80, "/images/products/curd.svg", now));
                                products.add(createProduct("Butter", "100% pure butter, salted",
                                                "Dairy", 90.00, 70, "/images/products/butter.svg", now));
                                products.add(createProduct("Yogurt", "Flavored yogurt with probiotics",
                                                "Dairy", 35.00, 90, "/images/products/yogurt.svg", now));

                                // Household category
                                products.add(createProduct("Detergent", "Stain-removing laundry detergent powder",
                                                "Household", 250.00, 120, "/images/products/detergent.svg", now));
                                products.add(createProduct("Dishwasher", "Powerful dishwashing liquid",
                                                "Household", 85.00, 100, "/images/products/dishwasher.svg", now));
                                products.add(createProduct("Cleaning Cloth", "Microfiber cleaning cloth set",
                                                "Household", 60.00, 150, "/images/products/cleaning-cloth.svg", now));
                                products.add(createProduct("Mop", "Easy-to-use floor cleaning mop",
                                                "Household", 180.00, 40, "/images/products/mop.svg", now));
                                products.add(createProduct("Broom", "Durable household broom for daily cleaning",
                                                "Household", 120.00, 50, "/images/products/broom.svg", now));
                                products.add(createProduct("Toilet Paper",
                                                "Soft and absorbent toilet paper rolls, pack of 6",
                                                "Household", 140.00, 200, "/images/products/toilet-paper.svg", now));

                                // Personal Care category
                                products.add(createProduct("Shampoo", "Nourishing shampoo for all hair types",
                                                "Personal Care", 120.00, 75, "/images/products/shampoo.svg", now));
                                products.add(createProduct("Conditioner", "Hair conditioner for smooth and silky hair",
                                                "Personal Care", 130.00, 65, "/images/products/conditioner.svg", now));
                                products.add(createProduct("Soap", "Moisturizing bath soap, pack of 3",
                                                "Personal Care", 70.00, 100, "/images/products/soap.svg", now));
                                products.add(createProduct("Toothpaste", "Cavity protection toothpaste",
                                                "Personal Care", 60.00, 120, "/images/products/toothpaste.svg", now));
                                products.add(createProduct("Deodorant", "Long-lasting deodorant spray",
                                                "Personal Care", 110.00, 80, "/images/products/deodorant.svg", now));
                                products.add(createProduct("Body Lotion", "Moisturizing body lotion for dry skin",
                                                "Personal Care", 180.00, 60, "/images/products/body-lotion.svg", now));

                                // Snacks category
                                products.add(createProduct("Chips", "Crispy potato chips, various flavors",
                                                "Snacks", 30.00, 150, "/images/products/chips.svg", now));
                                products.add(createProduct("Cookies", "Chocolate chip cookies, pack of 12",
                                                "Snacks", 60.00, 90, "/images/products/cookies.svg", now));
                                products.add(createProduct("Biscuits", "Cream-filled biscuits, family pack",
                                                "Snacks", 45.00, 100, "/images/products/biscuits.svg", now));
                                products.add(createProduct("Candy", "Assorted fruit-flavored candies",
                                                "Snacks", 25.00, 200, "/images/products/candy.svg", now));
                                products.add(createProduct("Popcorn", "Microwave popcorn, butter flavor",
                                                "Snacks", 50.00, 80, "/images/products/popcorn.svg", now));
                                products.add(createProduct("Granola Bars", "Healthy granola bars with nuts and honey",
                                                "Snacks", 70.00, 120, "/images/products/granola-bars.svg", now));

                                // Beverages category
                                products.add(createProduct("Juice", "100% natural fruit juice, no added sugar",
                                                "Beverages", 80.00, 100, "/images/products/juice.svg", now));
                                products.add(createProduct("Soda", "Carbonated soft drink, 2L bottle",
                                                "Beverages", 70.00, 120, "/images/products/soda.svg", now));
                                products.add(createProduct("Tea", "Premium black tea bags, pack of 50",
                                                "Beverages", 120.00, 80, "/images/products/tea.svg", now));
                                products.add(createProduct("Coffee", "Ground coffee, medium roast",
                                                "Beverages", 190.00, 60, "/images/products/coffee.svg", now));
                                products.add(createProduct("Water", "Mineral water, 1L bottle",
                                                "Beverages", 20.00, 200, "/images/products/water.svg", now));
                                products.add(createProduct("Energy Drink", "Caffeinated energy drink, 250ml",
                                                "Beverages", 45.00, 90, "/images/products/energy-drink.svg", now));

                                // Frozen Foods category
                                products.add(createProduct("Ice Cream", "Vanilla ice cream tub, 500ml",
                                                "Frozen Foods", 140.00, 40, "/images/products/ice-cream.svg", now));
                                products.add(createProduct("Frozen Vegetables", "Mixed vegetables, quick-frozen",
                                                "Frozen Foods", 90.00, 70, "/images/products/frozen-vegetables.svg",
                                                now));
                                products.add(createProduct("Frozen Pizza", "Ready-to-bake cheese pizza",
                                                "Frozen Foods", 220.00, 30, "/images/products/frozen-pizza.svg", now));
                                products.add(createProduct("Frozen Meals", "Microwave-ready frozen dinner",
                                                "Frozen Foods", 180.00, 35, "/images/products/frozen-meals.svg", now));
                                products.add(createProduct("Frozen Fish", "Fish fillets, individually packed",
                                                "Frozen Foods", 250.00, 25, "/images/products/frozen-fish.svg", now));

                                try {
                                        // Save all products to the database
                                        List<Product> savedProducts = productRepository.saveAll(products);
                                        logger.info("DataInitializer: Database initialized with "
                                                        + savedProducts.size() + " products.");

                                        // Verify products were saved
                                        long afterCount = productRepository.count();
                                        logger.info("DataInitializer: After initialization, product count = "
                                                        + afterCount);
                                } catch (Exception e) {
                                        logger.error("DataInitializer: Error saving products to database: "
                                                        + e.getMessage());
                                        e.printStackTrace();
                                }
                        } else {
                                logger.info("DataInitializer: Database already contains " + count
                                                + " products. Skipping initialization.");
                        }
                };
        }

        private Product createProduct(String name, String description, String category,
                        double price, int stock, String imageUrl,
                        LocalDateTime timestamp) {
                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setCategory(category);
                product.setPrice(price);
                product.setCurrentStock(stock);
                product.setImageUrl(imageUrl);
                product.setCreatedAt(timestamp);
                product.setUpdatedAt(timestamp);
                return product;
        }
}