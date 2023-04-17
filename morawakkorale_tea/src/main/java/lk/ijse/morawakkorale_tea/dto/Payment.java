package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Payment {

    private Integer id;
    private Integer rate;
    private String type;
    private Integer value;
    private String description;
    private Integer supp_id;
    private Integer trp_id;

}
