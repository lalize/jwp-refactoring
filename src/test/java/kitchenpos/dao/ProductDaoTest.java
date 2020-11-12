package kitchenpos.dao;

import static kitchenpos.fixture.ProductFixture.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    @DisplayName("상품을 저장할 수 있다.")
    @Test
    void save() {
        Product product = createProduct(null, "치킨", 0L);

        Product savedProduct = productDao.save(product);

        assertAll(
            () -> assertThat(savedProduct).isNotNull(),
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product.getName()),
            () -> assertThat(savedProduct.getPrice().longValue())
                .isEqualTo(product.getPrice().longValue())
        );
    }

    @DisplayName("상품 아이디로 상품을 조회할 수 있다.")
    @Test
    void findById() {
        Product product = productDao.save(createProduct(null, "치킨", 0L));

        Optional<Product> foundProduct = productDao.findById(product.getId());

        assertThat(foundProduct.get().getId()).isEqualTo(product.getId());
    }

    @DisplayName("상품 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        List<Product> savedProducts = Arrays.asList(
            productDao.save(createProduct(null, "치킨1", 10000L)),
            productDao.save(createProduct(null, "치킨2", 10000L)),
            productDao.save(createProduct(null, "치킨3", 10000L))
        );

        List<Product> allProducts = productDao.findAll();

        assertThat(allProducts).usingFieldByFieldElementComparator().containsAll(savedProducts);
    }
}
