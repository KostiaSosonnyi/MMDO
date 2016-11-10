package Simplex;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {

    private static Navigation navigation;

    public static Navigation getNavigation() {
        return navigation;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        navigation = new Navigation(primaryStage);

        primaryStage.setTitle("MMDO LAB1");
        primaryStage.show();

        navigation.load(View.URL_FXML).Show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

class Simplex {

    private ArrayList<ArrayList<Double>> arg;
    private ArrayList<Double> b;
    private ArrayList<Double> z;
    private ArrayList<Integer> eq;
    private ArrayList<Double> unswer;
    private boolean zb;
    private Double zOpt;

    public Double getzOpt() {
        return zOpt;
    }

    public ArrayList<Double> getUnswer() {
        return unswer;
    }

    public void setEq(ArrayList<Integer> eq) {
        this.eq = eq;
    }

    public void setB(ArrayList<TextField> b, int n) {
        for (int i = 0; i < n; i++)
            this.b.add(Double.parseDouble(b.get(i).getText()));
    }

    public void setZ(ArrayList<TextField> z, boolean zb, int n) {
        for (int i = 0; i < n; i++)
            this.z.add(Double.parseDouble(z.get(i).getText()));
        this.zb = zb;
    }

    public void setArg(ArrayList<ArrayList<TextField>> arg, Integer n, Integer m) {
        for (int i = 0; i < m; i++) {
            this.arg.add(new ArrayList<>());
            for (int j = 0; j < n; j++)
                this.arg.get(i).add(Double.parseDouble(arg.get(i).get(j).getText()));
        }
    }

    public Simplex() {
        arg = new ArrayList<>();
        b = new ArrayList<>();
        z = new ArrayList<>();
        unswer = new ArrayList<>();
    }

    public boolean simplex(){
        ArrayList<Integer> p = normalForm();
        return sTab(p);
    }

    public void clear() {
        arg.clear();
        b.clear();
        z.clear();
        eq.clear();
        unswer.clear();
    }

    public void print() {
//        for (int i = 0; i < z.size(); i++) {
//            if (i != 0) System.out.print(" + ");
//            System.out.print(z.get(i));
//        }
//        System.out.print(" -> ");
//        if (zb) System.out.print("max");
//        else System.out.print("min");
//        System.out.print("\n");
//
//        for (int i = 0; i < arg.size(); i++) {
//            for (int j = 0; j < arg.get(i).size(); j++) {
//                if (j != 0) System.out.print(" + ");
//                System.out.print(arg.get(i).get(j));
//            }
//            switch (eq.get(i)) {
//                case 1:
//                    System.out.print(" ≥ ");
//                    break;
//                case 2:
//                    System.out.print(" ≤ ");
//                    break;
//                default:
//                    System.out.print(" = ");
//            }
//            System.out.print(b.get(i) + "\n");
//        }
        System.out.print("(");
        for (Double u : unswer) {
            System.out.print(u + ", ");
        }
        System.out.print(")");
        System.out.print("\nZmax = " + zOpt);
    }

    private ArrayList<Integer> normalForm() {
        ArrayList<Integer> p = new ArrayList<>();
        for (int i = 0; i < eq.size(); i++)
            if (eq.get(i) != 0) {
                for (int j = 0; j < arg.size(); j++) {
                    if (j == i) {
                        if(eq.get(i) == 2)
                            arg.get(i).add(new Double(1));
                        else
                            arg.get(i).add(new Double(-1));
                    } else
                        arg.get(j).add(new Double(0));
                    boolean bool = false;
                    for (Integer k: p) {
                        if(k == arg.get(j).size() - 1)
                            bool = true;
                    }
                    if(!bool)
                        p.add(arg.get(j).size() - 1);
                }
                z.add(new Double(0));
                eq.set(i, 0);
            }
        return p;
    }

    private boolean sTab(ArrayList<Integer> p) {
        ArrayList<ArrayList<Double>> a = new ArrayList<>();
        for (int i = 0; i < arg.size(); i++) {
            a.add(new ArrayList<>());
            for (int j = 0; j < arg.get(i).size() + 1; j++)
                if (j == 0) a.get(i).add(b.get(i));
                else a.get(i).add(arg.get(i).get(j - 1));
        }

        a.add(new ArrayList<>());
        for (int i = 0; i <= z.size(); i++) {
            double c = 0;
            for(int j = 0; j < p.size(); j++) {
                c += z.get(p.get(j)) * a.get(j).get(i);
            }
            if(i != 0)
                a.get(a.size() - 1).add(c - z.get(i - 1));
            else a.get(a.size() - 1).add(c);
        }

        double b = 0;
        int x = 0, y = 0;
        for (int i = 1; i < a.get(a.size() - 1).size(); i++) {
            if (i == 1) {
                b = a.get(a.size() - 1).get(i);
                y = 1;
            }
            if (zb) {
                if (a.get(a.size() - 1).get(i) < 0) {
                    boolean bool = false;
                    for (int j = 0; j < a.size() - 1; j++)
                        if (a.get(j).get(i) >= 0) bool = true;
                    if (bool) {
                        if (a.get(a.size() - 1).get(i) < b) {
                            b = a.get(a.size() - 1).get(i);
                            y = i;
                        }
                    } else {
                        System.out.print("No solutions");
                        return false;
                    }
                }
            } else if (a.get(a.size() - 1).get(i) > 0) {
                boolean bool = false;
                for (int j = 0; j < a.size() - 1; j++)
                    if (a.get(j).get(i) <= 0) bool = true;
                if (bool) {
                    if (a.get(a.size() - 1).get(i) > b) {
                        b = a.get(a.size() - 1).get(i);
                        y = i;
                    }
                } else {
                    System.out.print("No solutions");
                    return false;
                }
            }
        }
        if ((zb && b >= 0) || (!zb && b <= 0)) {
            for(ArrayList<Double> i : a) {
                for (Double d : i) {
                    System.out.print(d + " ");
                }
                System.out.print("\n");
            }
            System.out.print("Completed");
            boolean bool = false;
            for (int i = 0; i < z.size() - p.size(); i++) {
                for (int j = 0; j < p.size(); j++)
                    if (i == p.get(j)) {
                        unswer.add(a.get(0).get(i));
                        bool = true;
                        break;
                    }
                if (!bool) {
                    unswer.add(new Double(0));
                    bool = false;
                }
            }
            Double opt = new Double(0);
            for (int i = 0; i < a.get(a.size() - 1).size(); i++)
                if(zb) {
                    if (opt < a.get(a.size() - 1).get(i))
                        opt = a.get(a.size() - 1).get(i);
                }else{
                    if (opt > a.get(a.size() - 1).get(i))
                        opt = a.get(a.size() - 1).get(i);
                }
            zOpt = opt;
            return true;
        }
        double o = -1;
        for (int i = 0; i < a.size() - 1; i++)
            if (a.get(i).get(y) > 0)
                if (o < 0 || (a.get(i).get(0) / a.get(i).get(y)) < o) {
                    o = a.get(i).get(0) / a.get(i).get(y);
                    x = i;
                }
        ArrayList<Integer> pNew = new ArrayList<>();
        for(int i = 0; i < p.size(); i++)
            if(i == x)
                pNew.add(y - 1);
            else pNew.add(p.get(i));
        return sTab(pNew, a, x, y);
    }

    private boolean sTab(ArrayList<Integer> p, ArrayList<ArrayList<Double>> aold, int xold, int yold) {
        ArrayList<ArrayList<Double>> a = new ArrayList<>();
        for(int i = 0; i < aold.size() - 1; i++){
            a.add(new ArrayList<>());
            for (int j = 0; j < aold.get(i).size(); j++){
                if(i == xold)
                    a.get(i).add(aold.get(i).get(j) / aold.get(xold).get(yold));
                else
                    a.get(i).add(aold.get(i).get(j) - aold.get(i).get(yold) * aold.get(xold).get(j) / aold.get(xold).get(yold));
            }
        }

        a.add(new ArrayList<>());
        for (int i = 0; i <= z.size(); i++) {
            double c = 0;
            for(int j = 0; j < p.size(); j++) {
                c += z.get(p.get(j)) * a.get(j).get(i);
            }
            if(i != 0)
                a.get(a.size() - 1).add(c - z.get(i - 1));
            else a.get(a.size() - 1).add(c);
        }

        double b = 0;
        int x = 0, y = 0;
        for (int i = 1; i < a.get(a.size() - 1).size(); i++) {
            if (i == 1) {
                b = a.get(a.size() - 1).get(i);
                y = 1;
            }
            if (zb) {
                if (a.get(a.size() - 1).get(i) < 0) {
                    boolean bool = false;
                    for (int j = 0; j < a.size() - 1; j++)
                        if (a.get(j).get(i) >= 0) bool = true;
                    if (bool) {
                        if (a.get(a.size() - 1).get(i) < b) {
                            b = a.get(a.size() - 1).get(i);
                            y = i;
                        }
                    } else {
                        System.out.print("No solutions");
                        return false;
                    }
                }
            } else if (a.get(a.size() - 1).get(i) > 0) {
                boolean bool = false;
                for (int j = 0; j < a.size() - 1; j++)
                    if (a.get(j).get(i) <= 0) bool = true;
                if (bool) {
                    if (a.get(a.size() - 1).get(i) > b) {
                        b = a.get(a.size() - 1).get(i);
                        y = i;
                    }
                } else {
                    System.out.print("No solutions");
                    return false;
                }
            }
        }
        if ((zb && b >= 0) || (!zb && b <= 0)) {
            for(ArrayList<Double> i : a) {
                for (Double d : i) {
                    System.out.print(d + " ");
                }
                System.out.print("\n");
            }
            System.out.print("Completed");
            boolean bool = false;
            for(int i = 0; i < z.size() - p.size(); i++) {
                for (int j = 0; j < p.size(); j++)
                    if (i == p.get(j)) {
                        unswer.add(a.get(i-1).get(0));
                        bool = true;
                        break;
                    }
                if(!bool){
                    unswer.add(new Double(0));
                    bool = false;
                }
            }
            Double opt = new Double(0);
            for(int i = 0; i < a.get(a.size() - 1).size(); i++)
                if(zb) {
                    if (opt < a.get(a.size() - 1).get(i))
                        opt = a.get(a.size() - 1).get(i);
                }else{
                    if (opt > a.get(a.size() - 1).get(i))
                    opt = a.get(a.size() - 1).get(i);
                }
            zOpt = opt ;
            return true;
        }
        double o = -1;
        for (int i = 0; i < a.size() - 1; i++)
            if (a.get(i).get(y) > 0)
                if (o < 0 || (a.get(i).get(0) / a.get(i).get(y)) < o) {
                    o = a.get(i).get(0) / a.get(i).get(y);
                    x = i;
                }
        ArrayList<Integer> pNew = new ArrayList<>();
        for(int i = 0; i < p.size(); i++)
            if(i == x)
                pNew.add(y -1);
            else pNew.add(p.get(i));
        return sTab(pNew, a, x, y);
    }
}