package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class OrderDetailsId implements Serializable {
    @Column(name = "product_code")
    private Integer prodCode;

    @Column(name = "order_id")
    private Integer orderId;

    public OrderDetailsId(){}

    public OrderDetailsId(Integer orderId, Integer prodCode){
        this.orderId=orderId;
        this.prodCode=prodCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null||getClass()!=o.getClass()) return false;
        OrderDetailsId orderDetailsId = (OrderDetailsId) o;
        return Objects.equals(prodCode, orderDetailsId.prodCode) && Objects.equals(orderId, orderDetailsId.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodCode, orderId);
    }

}

