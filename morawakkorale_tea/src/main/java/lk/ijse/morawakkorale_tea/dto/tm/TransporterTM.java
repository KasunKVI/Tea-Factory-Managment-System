package lk.ijse.morawakkorale_tea.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TransporterTM {

    private Integer id;
    private String name;
    private String contact;
    private String route;
    private String address;

}
