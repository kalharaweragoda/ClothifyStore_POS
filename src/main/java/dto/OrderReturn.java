package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturn {
    private Integer id;
    private Integer orderId;
    private String date;
    private Integer employeeId;
    private String employeeName;
    private Integer customerId;
    private Double total;
    private String paymentType;
}
