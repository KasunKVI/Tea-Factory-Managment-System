package lk.ijse.morawakkorale_tea.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Transporter {

    private Integer id;
    private String name;
    private String contact;
    private String route;
    private String address;

}
