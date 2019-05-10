package algo;

/**
 * Created by YUUUUU on 2017-06-05.
 */

public class Spot {
    private int label = -1;
    private String name;
    private Spot next;
    private int costCar;
    private int costTaxi;
    private int time;

    public Spot(){
        label = -1;
    }

    /* 레이블, 이름 */
    public Spot(int l, String n){
        label = l;
        name = n;
        next = null;
        costCar = 0;
        costTaxi = 0;
        time = 0;
    }

    /* 레이블, 이름, 자동차 비용, 택시 비용, 자동차 시간 */
    public Spot(int l, String n, int c1, int c2, int t){
        label = l;
        name = n;
        costCar = c1;
        costTaxi = c2;
        time = t;
        next = null;
    }

    /* 레이블, 이름, 대중교통 시간 */
    public Spot(int l, String n, int t){
        label = l;
        name = n;
        costCar = 0;
        costTaxi = 0;
        time = t;
        next = null;
    }

    public void setLabel(int l){
        label = l;
    }
    public void setName(String n) {
        name = n;
    }
    public void setNext(Spot p) {
        next = p;
    }
    public void setCostCar(int c) {
        costCar = c;
    }
    public void setCostTaxi(int c) { costTaxi = c; }
    public void setTime(int t) {
        time = t;
    }
    public int getLabel() {
        return label;
    }
    public String getName() {
        return name;
    }
    public Spot getNext() {
        return next;
    }
    public int getCostCar() {
        return costCar;
    }
    public int getCostTaxi() {
        return costTaxi;
    }
    public int getTime() {
        return time;
    }
}