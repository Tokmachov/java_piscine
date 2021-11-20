package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImplTest {
    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS = initExpectedFindAllProducts();

    private static final Product EXPECTED_FIND_BY_ID_PRODUCT1 = new Product(1, "Apple", 100);
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT2 = new Product(2, "cup", 200);
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT3 = new Product(3, "cheese", 300);

    private static final Product EXPECTED_UPDATED_PRODUCT = new Product(3L, "tea", 50);

    private static final Product EXPECTED_SAVED_PRODUCT1 = new Product(100L, "pear", 150);
    private static final Product EXPECTED_SAVED_PRODUCT2 = new Product(1L, "screen", 120);

    private EmbeddedDatabase db;
    private ProductRepository repository;

    @BeforeEach
    public void intit() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("schema.sql").addScript("data.sql").build();
        repository = new ProductRepositoryImpl(db);
    }

    @AfterEach
    public void deinit() {
        db.shutdown();
    }

    @Test
    public void findAllTest() {
        List<Product> actualFindAllProducts = repository.findAll();
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, actualFindAllProducts);
    }

    @Test
    public void findByIdTest() {
        Optional<Product> actualFindByIdProduct1 = repository.findById(1L);
        Assertions.assertTrue(actualFindByIdProduct1.isPresent());
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT1, actualFindByIdProduct1.get());

        Optional<Product> actualFindByIdProduct2 = repository.findById(2L);
        Assertions.assertTrue(actualFindByIdProduct2.isPresent());
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT2, actualFindByIdProduct2.get());

        Optional<Product> actualFindByIdProduct3 = repository.findById(3L);
        Assertions.assertTrue(actualFindByIdProduct3.isPresent());
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT3, actualFindByIdProduct3.get());

        Optional<Product> actualFindByIdProduct4 = repository.findById(100L);
        Assertions.assertFalse(actualFindByIdProduct4.isPresent());
    }

    @Test
    public void updateTest() {
        Optional<Product> product = repository.findById(EXPECTED_UPDATED_PRODUCT.getId());

        Assertions.assertTrue(product.isPresent());

        product.get().setName(EXPECTED_UPDATED_PRODUCT.getName());
        product.get().setPrice(EXPECTED_UPDATED_PRODUCT.getPrice());

        repository.update(product.get());

        Optional<Product> actualUpdatedProduct = repository.findById(EXPECTED_UPDATED_PRODUCT.getId());
        Assertions.assertTrue(actualUpdatedProduct.isPresent());
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, actualUpdatedProduct.get());
    }

    @Test
    public void saveTest() {
        repository.save(EXPECTED_SAVED_PRODUCT1);
        repository.save(EXPECTED_SAVED_PRODUCT2);

        Optional<Product> actualSavedProduct1 = repository.findById(EXPECTED_SAVED_PRODUCT1.getId());
        Optional<Product> actualSavedProduct2 = repository.findById(EXPECTED_SAVED_PRODUCT2.getId());

        Assertions.assertTrue(actualSavedProduct1.isPresent());
        Assertions.assertTrue(actualSavedProduct2.isPresent());

        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT1, actualSavedProduct1.get());
        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT2, actualSavedProduct2.get());
    }

    @Test
    public void deleteTest() {
        repository.delete(1L);
        repository.delete(2L);

        Optional<Product> p1 = repository.findById(1L);
        Optional<Product> p2 = repository.findById(2L);

        Assertions.assertFalse(p1.isPresent());
        Assertions.assertFalse(p2.isPresent());
    }

    private static List<Product> initExpectedFindAllProducts() {
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(1, "Apple", 100);
        Product p2 = new Product(2, "cup", 200);
        Product p3 = new Product(3, "cheese", 300);
        Product p4 = new Product(4, "car", 400);
        Product p5 = new Product(5, "wire", 100);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        return products;
    }
}
