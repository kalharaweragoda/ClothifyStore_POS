package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productDetails")
@Entity
@Table(name = "suppliers")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String company;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, name = "phone_number", length = 15)
    private String phoneNo;

    @OneToMany(mappedBy = "supplierEntity", cascade = {CascadeType.MERGE})
    private Set<ProductDetailEntity> productDetails = new HashSet<>();

}
