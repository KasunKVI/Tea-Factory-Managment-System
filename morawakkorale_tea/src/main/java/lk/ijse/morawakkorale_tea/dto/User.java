package lk.ijse.morawakkorale_tea.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class User {

    private String id;
    private String password;
    private  String contact;
    private String name;
    private String email;
    private String position;

}
