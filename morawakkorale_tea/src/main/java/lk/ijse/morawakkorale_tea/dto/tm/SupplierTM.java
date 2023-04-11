package lk.ijse.morawakkorale_tea.dto.tm;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SupplierTM {

    private Integer id;
    private String name;
    private String contact;
    private Date reg_date;
    private String address;
    private String status;
}
