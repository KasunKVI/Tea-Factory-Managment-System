package lk.ijse.morawakkorale_tea.dto.tm;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ProductTM {

    private String id;
    private String made_date;
    private String stock_id;
    private Integer leaf_value;
    private String type;
    private Integer quantity;

}
