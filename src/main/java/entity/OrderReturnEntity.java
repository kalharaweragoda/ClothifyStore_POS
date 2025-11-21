package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderreturns")
public class OrderReturnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10, name = "order_id")
    private Integer orderId;

    @Column(nullable = false, length = 20)
    private String date;

    @Column(nullable = false, name = "employee_id")
    private Integer employeeId;

    @Column(nullable = false, name = "employee_name")
    private String employeeName;

    @Column(nullable = false, name = "customer_id")
    private Integer customerId;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false, name = "payment_type")
    private String paymentType;
}
