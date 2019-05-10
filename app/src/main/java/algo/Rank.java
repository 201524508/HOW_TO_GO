package algo;

/**
 * Created by YUUUUU on 2017-06-11.
 */

public class Rank {
    private int[] value;
    private int[] rankWay;
    private int size;

    public Rank(int n){
        size = n;
        value = new int[n];
        rankWay = new int[n];
    }

    public void setRank(int[] t, int[] k){
        for(int i = 0; i < size; i++){
            value[i] = t[i];
            rankWay[i] = k[i];
        }
    }

    public int getSize(){
        return size;
    }

    public int[] getValue(){
        return value;
    }

    public int[] getRankWay(){
        return rankWay;
    }

    public void printRank(){
        for(int i = 0; i < size; i++){
            System.out.println(i + "순위");
            System.out.println("value : " + value[i] + ", way index : " + rankWay[i]);
        }
    }
}
