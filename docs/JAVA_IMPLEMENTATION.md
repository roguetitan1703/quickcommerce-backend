# QuickCommerce Java Implementation Documentation

## Core Java Implementation Details

### 1. Entity Classes

#### Product Entity

```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean isActive;
    private Integer discountPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters, setters, and other methods
}
```

#### Category Entity

```java
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    // Getters, setters, and other methods
}
```

#### Inventory Entity

```java
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer currentStock;
    private Integer minStock;
    private Integer maxStock;

    // Getters, setters, and other methods
}
```

### 2. Repository Layer Implementation

#### ProductRepository

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByIsActiveTrue();
}
```

#### CategoryRepository

```java
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
```

#### InventoryRepository

```java
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(Long productId);
    List<Inventory> findByCurrentStockLessThanMinStock();
}
```

### 3. Service Layer Implementation

#### ProductService

```java
@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByIsActiveTrue();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        // Set product properties from DTO
        return productRepository.save(product);
    }

    // Other service methods
}
```

#### InventoryService

```java
@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Inventory updateStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if (inventory.getCurrentStock() < quantity) {
            throw new InsufficientStockException("Not enough stock available");
        }

        inventory.setCurrentStock(inventory.getCurrentStock() - quantity);
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getLowStockProducts() {
        return inventoryRepository.findByCurrentStockLessThanMinStock();
    }
}
```

### 4. Controller Layer Implementation

#### ProductController

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }
}
```

### 5. Security Implementation

#### SecurityConfig

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/products/**").permitAll()
                .requestMatchers("/api/categories/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

### 6. Exception Handling

#### GlobalExceptionHandler

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
```

### 7. Data Transfer Objects (DTOs)

#### ProductDTO

```java
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Long categoryId;
    private Integer discountPercent;

    // Getters, setters, and validation annotations
}
```

### 8. Custom Exceptions

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
```

## Key Implementation Patterns

### 1. Repository Pattern

- Uses Spring Data JPA repositories
- Custom query methods for specific operations
- Optimized database access

### 2. Service Layer Pattern

- Business logic encapsulation
- Transaction management
- Data validation and transformation

### 3. Controller Pattern

- RESTful endpoint implementation
- Request/response handling
- Security annotations

### 4. DTO Pattern

- Data transfer objects for API contracts
- Validation annotations
- Mapping between DTOs and entities

### 5. Exception Handling Pattern

- Global exception handling
- Custom exceptions
- Standardized error responses

## Best Practices Implemented

1. **Layered Architecture**

   - Clear separation of concerns
   - Dependency injection
   - Interface-based design

2. **Transaction Management**

   - @Transactional annotations
   - Proper transaction boundaries
   - Rollback handling

3. **Security**

   - JWT authentication
   - Role-based authorization
   - Password encryption

4. **Validation**

   - Input validation
   - Business rule validation
   - Custom validation annotations

5. **Error Handling**

   - Custom exceptions
   - Global exception handling
   - Standardized error responses

6. **Performance Optimization**

   - Lazy loading
   - Optimized queries
   - Connection pooling

7. **Code Organization**
   - Package structure
   - Naming conventions
   - Documentation

## Testing Implementation

### Unit Tests

```java
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }
}
```

### Integration Tests

```java
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
```

## Future Improvements

1. **Caching Implementation**

   - Redis integration
   - Cache annotations
   - Cache invalidation

2. **Async Operations**

   - Async service methods
   - CompletableFuture usage
   - Background tasks

3. **Monitoring**

   - Actuator endpoints
   - Custom metrics
   - Health checks

4. **Documentation**
   - Swagger annotations
   - API documentation
   - Code documentation
