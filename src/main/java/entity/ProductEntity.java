package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"productDetails","orderDetails"})
@EqualsAndHashCode(exclude = "orderDetails")
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, length = 10)
    private String category;

    @Column(nullable = false, length = 10)
    private String size;

    @Column(nullable = false, name = "unit_price")
    private Double unitPrice;

    @Column(nullable = false, name = "quantity")
    private Integer quantityInStock;

    @Column(nullable = false, name = "supplier_id")
    private Integer supplierId;

    @OneToMany(mappedBy = "productEntity", cascade = {CascadeType.MERGE})
    private Set<ProductDetailEntity> productDetails =  new HashSet<>();

    @OneToMany(mappedBy = "productEntity", cascade = {CascadeType.MERGE})
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

}
