package lk.ijse.morawakkorale_tea.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PlaceOrderTM {

    private String item_id;
    private String type;
    private Double unit_price;
    private Integer qty;
    private Double total;
}
