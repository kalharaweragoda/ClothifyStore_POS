package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "productdetail")
public class ProductDetailEntity {

    @EmbeddedId
    private ProductDetailsId id;

    @ManyToOne()
    @MapsId("supId")
    @JoinColumn(name = "supplier_id")
    @ToString.Exclude
    private SupplierEntity supplierEntity;

    @ManyToOne()
    @MapsId("prodCode")
    @JoinColumn(name = "product_code")
    @ToString.Exclude
    private ProductEntity productEntity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "quantity_supplied", nullable = false)
    private Integer qtySupplied;


    public ProductDetailEntity(SupplierEntity supplierEntity, ProductEntity productEntity, Double unitPrice, Integer qtySupplied) {
        if (productEntity == null || supplierEntity == null) {
            throw new IllegalArgumentException("ProductEntity and SupplierEntity cannot be null");
        }
        this.supplierEntity = supplierEntity;
        this.productEntity = productEntity;
        this.unitPrice = unitPrice;
        this.qtySupplied = qtySupplied;

        // ✅ Ensure the composite key is initialized
        this.id = new ProductDetailsId(productEntity.getCode(), supplierEntity.getId());
    }

}
