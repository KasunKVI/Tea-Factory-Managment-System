package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TransporterBill {

    private Integer tp_id;
    private String route;
    private Integer value;
    private Double payment;

}
