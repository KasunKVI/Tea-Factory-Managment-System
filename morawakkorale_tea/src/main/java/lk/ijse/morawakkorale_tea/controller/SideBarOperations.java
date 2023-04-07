package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SideBarOperations {


    public static void showMenuBar(Pane menuBarPanel){

        menuBarPanel.setDisable(false );
        fadeMenuBar(menuBarPanel,0,1,+88);
        menuBarPanel.setVisible(true);

    }

    public static void hideMenuBar(Pane menuBarPanel){

        fadeMenuBar(menuBarPanel,1,0,-88);
        menuBarPanel.setDisable(true);

    }

    public static void fadeMenuBar(Pane pane,int from,int to,int x) {

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane);
        fadeTransition1.setFromValue(from);
        fadeTransition1.setToValue(to);
        fadeTransition1.play();

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane);
        translateTransition1.setByX(x);
        translateTransition1.play();
    }
}
