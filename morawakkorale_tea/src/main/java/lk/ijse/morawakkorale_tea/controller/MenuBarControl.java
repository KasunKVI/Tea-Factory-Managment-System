package lk.ijse.morawakkorale_tea.controller;


import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface MenuBarControl {
    public void hideMenuBar(MouseEvent mouseEvent);
    public void showMenuBar(MouseEvent mouseEvent);
    public void exitSystem(MouseEvent mouseEvent);
    public void openSettingForm(MouseEvent mouseEvent) throws IOException;
    public void logOut(ActionEvent actionEvent) throws IOException;
}
