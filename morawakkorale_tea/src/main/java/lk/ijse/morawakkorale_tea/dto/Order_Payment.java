package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Order_Payment {

    private String order_id;
    private LocalDate date;
    private Integer total;
    private String customer;
}
