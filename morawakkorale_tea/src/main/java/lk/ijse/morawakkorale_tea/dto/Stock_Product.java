package lk.ijse.morawakkorale_tea.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Stock_Product {

    private String product_id;
    private String stock_id;
    private Integer g_l_Value;
}
