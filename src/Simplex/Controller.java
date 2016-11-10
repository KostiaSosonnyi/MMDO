package Simplex;

import javafx.scene.Node;

public interface Controller {
    Node getView();
    void setView(Node view);
    public Simplex getSimplex();
    void Show();
}