package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartTM {
    private Integer productCode;
    private String productDescription;
    private Double unitPrice;
    private Integer quantity;
    private Double total;

    @Override
    public boolean equals(Object object){
        if(object==this) return true;
        if(object==null || object.getClass()!=this.getClass()) return false;
        CartTM cartTM = (CartTM) object;
        return Objects.equals(cartTM.productCode, this.productCode);
    }

    @Override
    public int hashCode(){
        return this.productCode;
    }
}
