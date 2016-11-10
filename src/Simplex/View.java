package Simplex;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View extends BaseController implements Initializable {

    public static final String URL_FXML = "view.fxml";

    private Simplex simplex;

    public Simplex getSimplex() {
        return simplex;
    }

    @FXML
    public Button okbttn;

    @FXML
    public Label zlable;
    @FXML
    public Label text;
    @FXML
    public Label text1;

    @FXML
    public TextField a11;
    @FXML
    public TextField a12;
    @FXML
    public TextField a13;
    @FXML
    public TextField a14;
    @FXML
    public TextField a21;
    @FXML
    public TextField a22;
    @FXML
    public TextField a23;
    @FXML
    public TextField a24;
    @FXML
    public TextField a31;
    @FXML
    public TextField a32;
    @FXML
    public TextField a33;
    @FXML
    public TextField a34;
    @FXML
    public TextField a41;
    @FXML
    public TextField a42;
    @FXML
    public TextField a43;
    @FXML
    public TextField a44;
    @FXML
    public TextField b1;
    @FXML
    public TextField b2;
    @FXML
    public TextField b3;
    @FXML
    public TextField b4;
    @FXML
    public TextField z1;
    @FXML
    public TextField z2;
    @FXML
    public TextField z3;
    @FXML
    public TextField z4;
    @FXML
    public ComboBox<String> cmbbxn;
    @FXML
    public ComboBox<String> cmbbxm;
    @FXML
    public ComboBox<String> cmbbx;
    @FXML
    public ComboBox<String> cmbbx1;
    @FXML
    public ComboBox<String> cmbbx2;
    @FXML
    public ComboBox<String> cmbbx3;
    @FXML
    public ComboBox<String> cmbbx4;

    @FXML
    public GridPane gpane;

    private int ic = 0;

    private ArrayList<ArrayList<TextField>> arg = new ArrayList<>(4);
    private ArrayList<TextField> b = new ArrayList<>(4);
    private ArrayList<TextField> z = new ArrayList<>(4);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        simplex = new Simplex();

        gpane.setVisible(false);
        zlable.setVisible(false);
        okbttn.setVisible(false);
        text.setVisible(false);
        text1.setVisible(false);

        cmbbx.getItems().setAll("min", "max");
        cmbbxn.getItems().setAll("2", "3", "4");
        cmbbxm.getItems().setAll("2", "3", "4");
        cmbbx1.getItems().setAll("=", "≥", "≤");
        cmbbx2.getItems().setAll("=", "≥", "≤");
        cmbbx3.getItems().setAll("=", "≥", "≤");
        cmbbx4.getItems().setAll("=", "≥", "≤");
        cmbbx.setValue("max");
        cmbbxn.setValue("0");
        cmbbxm.setValue("0");
        cmbbx1.setValue("=");
        cmbbx2.setValue("=");
        cmbbx3.setValue("=");
        cmbbx4.setValue("=");

        arg.add(new ArrayList<>(4));
        arg.add(new ArrayList<>(4));
        arg.add(new ArrayList<>(4));
        arg.add(new ArrayList<>(4));
        arg.get(0).add(a11);
        arg.get(0).add(a12);
        arg.get(0).add(a13);
        arg.get(0).add(a14);
        arg.get(1).add(a21);
        arg.get(1).add(a22);
        arg.get(1).add(a23);
        arg.get(1).add(a24);
        arg.get(2).add(a31);
        arg.get(2).add(a32);
        arg.get(2).add(a33);
        arg.get(2).add(a34);
        arg.get(3).add(a41);
        arg.get(3).add(a42);
        arg.get(3).add(a43);
        arg.get(3).add(a44);

        b.add(b1);
        b.add(b2);
        b.add(b3);
        b.add(b4);

        z.add(z1);
        z.add(z2);
        z.add(z3);
        z.add(z4);

        cmbbxn.setOnAction(event -> {
            if(cmbbxm.getValue() != "0")
                cmbbx();
        });
        cmbbxm.setOnAction(event -> {
            if(cmbbxn.getValue() != "0")
                cmbbx();
        });

        okbttn.setOnAction(event -> {

            String e = "[-0-9]{1,5}";
            for (int i = 0; i < Integer.parseInt(cmbbxm.getValue()); i++) {
                for (int j = 0; j < Integer.parseInt(cmbbxn.getValue()); j++)
                    if (arg.get(i).get(j).getText().isEmpty() || !arg.get(i).get(j).getText().matches(e))
                        arg.get(i).get(j).setText("0");
                if (b.get(i).getText().isEmpty() || !b.get(i).getText().matches(e))
                    b.get(i).setText("0");
                if (z.get(i).getText().isEmpty() || !z.get(i).getText().matches(e))
                    z.get(i).setText("0");
            }
            boolean zb;
            switch (cmbbx.getValue()) {
                case "min":
                    zb = false;
                    break;
                default:
                    zb = true;
            }
            ArrayList<Integer> cint = new ArrayList<Integer>(4);
            cint.add(cmbbxToInt(cmbbx1));
            cint.add(cmbbxToInt(cmbbx2));
            cint.add(cmbbxToInt(cmbbx3));
            cint.add(cmbbxToInt(cmbbx4));

            simplex.setEq(cint);
            simplex.setArg(arg, Integer.parseInt(cmbbxn.getValue()), Integer.parseInt(cmbbxm.getValue()));
            simplex.setB(b, Integer.parseInt(cmbbxm.getValue()));
            simplex.setZ(z, zb, Integer.parseInt(cmbbxn.getValue()));


            if(simplex.simplex()) {
                Main.getNavigation().load(UView.URL_FXML).Show();
            }

        });
    }

    private void cmbbx(){
        zlable.setVisible(true);
        gpane.setVisible(true);
        okbttn.setVisible(true);
        text.setVisible(true);
        text1.setVisible(true);
        text.setText("j = 1 ÷ " + cmbbxn.getValue());

        for (ArrayList<TextField> a : arg)
            for (TextField anA : a)
                anA.setDisable(true);
        for (TextField anA : z)
            anA.setDisable(true);
        for (TextField anA : b)
            anA.setDisable(true);

        for(int i = 0; i < Integer.parseInt(cmbbxm.getValue()); i++) {
            for (int j = 0; j < Integer.parseInt(cmbbxn.getValue()); j++) {
                arg.get(i).get(j).setDisable(false);
                if(i == 0)
                z.get(j).setDisable(false);
            }
            b.get(i).setDisable(false);
        }

        switch(cmbbxm.getValue()){
            case"2":
                cmbbx3.setDisable(true);
                cmbbx4.setDisable(true);
                break;
            case"3":
                cmbbx3.setDisable(false);
                cmbbx4.setDisable(true);
                break;
            case"4":
                cmbbx3.setDisable(false);
                cmbbx4.setDisable(false);
                break;
            default:
                System.out.print("Error");
        }
    }
    private Integer cmbbxToInt(ComboBox<String> c){
        switch(c.getValue()){
            case"=":
                return 0;
            case"≥":
                return 1;
            case"≤":
                return 2;
        }
        return 0;
    }
}
