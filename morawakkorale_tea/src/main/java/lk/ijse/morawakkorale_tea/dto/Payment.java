package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Payment {

    private String id;
    private Integer rate;
    private String type;
    private Integer value;
    private String description;

}
