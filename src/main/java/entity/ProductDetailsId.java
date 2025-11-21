package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductDetailsId implements Serializable {

    @Column(name = "product_code")
    private Integer prodCode;

    @Column(name = "supplier_id")
    private Integer supId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null||getClass()!=o.getClass()) return false;
        ProductDetailsId productDetailsId = (ProductDetailsId) o;
        return Objects.equals(prodCode, productDetailsId.prodCode) && Objects.equals(supId, productDetailsId.supId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodCode, supId);
    }
}
