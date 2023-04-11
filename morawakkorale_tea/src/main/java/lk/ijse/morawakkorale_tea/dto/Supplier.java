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

    private Integer id;
    private String name;
    private String contact;
    private Date reg_date;
    private String address;
    private String status;

}
