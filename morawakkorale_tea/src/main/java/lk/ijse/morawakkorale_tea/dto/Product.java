package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Product {

    private String id;
    private String name;
    private LocalDate made_date;
    private Integer qty_on_hand;
    private String type;

}
