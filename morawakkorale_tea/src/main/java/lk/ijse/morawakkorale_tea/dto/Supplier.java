package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Supplier {

    private String id;
    private String name;
    private String contact;
    private LocalDate reg_date;
    private String address;

}
