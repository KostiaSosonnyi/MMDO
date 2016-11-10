package Simplex;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Константин on 10-Nov-16.
 */
public class UView extends BaseController implements Initializable {

    public static final String URL_FXML = "uview.fxml";

    private Controller parant;
    public Simplex getSimplex(){return null;}

    @FXML
    public Label x;
    @FXML
    public Label opt;
    @FXML
    public Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String o = "(";
        ArrayList<Double> unswer;
        parant = Main.getNavigation().getController();
        unswer = parant.getSimplex().getUnswer();
        for (int i = 0; i < unswer.size(); i++){
            o += unswer.get(i).toString();
            if(i != unswer.size() -1 )
                o += ", ";
        }
        o += ")";
        x.setText(o);
        opt.setText(parant.getSimplex().getzOpt() + "");
        back.setOnAction(event -> {
            parant.getSimplex().clear();
            Main.getNavigation().GoBack();
        });
    }

}
