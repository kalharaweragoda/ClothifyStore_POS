package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetail {

    private Supplier supplier;

    private Product product;

    private Double unitPrice;

    private Integer qtySupplied;
}
