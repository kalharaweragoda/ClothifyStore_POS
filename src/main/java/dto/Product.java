package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private Integer code;
    private String description;
    private String category;
    private String size;
    private Double unitPrice;
    private Integer quantityInStock;
    private Integer supplierId;
    private Set<ProductDetail> productDetails = new HashSet<>();
    private List<OrderDetail> orderDetailsList = new ArrayList<>();
}
