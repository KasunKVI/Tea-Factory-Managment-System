package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Product {

    private String id;
    private LocalDate made_date;
    private Integer qty_on_hand;
    private String type;

}
