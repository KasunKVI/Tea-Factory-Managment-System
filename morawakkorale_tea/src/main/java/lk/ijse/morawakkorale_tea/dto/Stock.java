package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Stock {

    private String stock_id;
    private String date;
    private Integer value;
    private Integer transporter_id;

}
