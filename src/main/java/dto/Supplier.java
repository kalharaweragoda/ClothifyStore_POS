package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Supplier {
    private Integer id;
    private String name;
    private String company;
    private String email;
    private String phoneNo;
    private Set<ProductDetail> productDetails = new HashSet<>();
}
