package algo;

import com.example.home.how_to_go.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by YUUUUU on 2017-06-05.
 */

public class Pathway {
    private Spot list[];
    private Spot way[][];
    private int numSpot;
    private int fresult;
    private String pathway;
    public int cnt;

    public void setPathway(int n){
        list = new Spot[n];
        if(n==3){
            fresult = 1;
        }
        else{
            fresult = fact(n-2);
        }
        numSpot = n;
        way = new Spot[fresult][numSpot];
        pathway = "";
    }

    public int fact(int n) {
        if (n <= 1)
            return n;
        else
            return fact(n-1) * n;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void perm(int[] ar, int size, int start){
        if(start == size-1){
            System.out.println("cnt : " + cnt);
            way[cnt][0] = new Spot(0, list[0].getName());
            for(int i = 1; i < numSpot - 1; i++){
                way[cnt][i] = new Spot(ar[i-1], list[ar[i-1]].getName());
            }
            way[cnt][numSpot-1] = new Spot(numSpot-1, list[numSpot-1].getName());
            setWaySpot(way, cnt);
            cnt = cnt + 1;
            return;
        }

        for(int i = start; i < size; i++){
            swap(ar, i, start);
            perm(ar, size, start + 1);
            swap(ar, i, start);
        }
    }

    public void insertSpot(Spot p, Spot q){
        int l1 = p.getLabel();
        int l2 = q.getLabel();

        if(list[l1] == null) {
            list[l1] = new Spot();
            list[l1] = p;
            list[l1].setNext(q);
        }
        else{
            Spot t = list[l1];
            Spot k = t;
            while(t.getNext() != null){
                if(t.getNext().getLabel() < l2){
                    t = t.getNext();
                }
                else{
                    break;
                }
            }
            Spot h = t.getNext();
            t.setNext(q);
            q.setNext(h);
            list[l1] = k;
        }

        if(l2 == numSpot -1){
            list[l2] = new Spot(q.getLabel(), q.getName());
        }
    }

    public void setWay(){
        if(numSpot == 3) {
            way[0] = new Spot[numSpot];
            way[0][0] = new Spot(0, list[0].getName());
            way[0][1] = new Spot(1, list[1].getName());
            way[0][numSpot - 1] = new Spot(numSpot - 1, list[numSpot - 1].getName());

            setWaySpot(way, 0);
        }
        else if(numSpot == 4){
            for(int i = 0; i < 2; i++){
                way[i] = new Spot[numSpot];
                way[i][0] = new Spot(0, list[0].getName());
                way[i][numSpot - 1] = new Spot(numSpot - 1, list[numSpot - 1].getName());
            }
            for(int i = 0; i < 2; i++){
                way[0][i+1] = new Spot(i+1, list[i+1].getName());
            }
            way[1][1] = new Spot(2, list[2].getName());
            way[1][2] = new Spot(1, list[1].getName());

            printWay();
            setWaySpot(way, 0);
            setWaySpot(way, 1);
        }
        else if(numSpot > 4){
            for(int i = 0; i < fresult; i++){
                way[i] = new Spot[numSpot];
                way[i][0] = new Spot(0, list[0].getName());
            }
            int sp[] = new int[numSpot - 2];

            for(int i = 0; i < numSpot - 2; i++){
                sp[i] = i + 1;
            }
            perm(sp, numSpot - 2, 0);
            printWay();
        }

    }

    public void setWaySpot(Spot[][] w, int j){
        for(int i = 0; i < numSpot-1; i++){
            Spot t1 = w[j][i];
            Spot t2 = w[j][i+1];
            int l1 = t1.getLabel();
            int l2 = t2.getLabel();

            if(l1 < l2){
                Spot k = list[l1];

                while(k.getNext() != null){
                    if(k.getNext().getLabel() == l2){
                        w[j][i+1].setCostCar(k.getNext().getCostCar());
                        w[j][i+1].setCostTaxi(k.getNext().getCostTaxi());
                        w[j][i+1].setTime(k.getNext().getTime());
                        break;
                    }
                    k = k.getNext();
                }
            }
            else if(l1 > l2){
                Spot k = list[l2];

                while(k.getNext() != null){
                    if(k.getNext().getLabel() == l1){
                        w[j][i+1].setCostCar(k.getNext().getCostCar());
                        w[j][i+1].setCostTaxi(k.getNext().getCostTaxi());
                        w[j][i+1].setTime(k.getNext().getTime());
                        break;
                    }
                    k = k.getNext();
                }
            }
        }

        System.out.println("check2!");
        for(int i = 0; i < numSpot; i++){
            System.out.print(w[j][i].getLabel() + ":" + w[j][i].getName() + " (" + w[j][i].getCostCar() + ","+w[j][i].getCostTaxi()+","+w[j][i].getTime()+") ");
        }
        System.out.println();
    }

    public void printWay(){
        if(fresult == 1){
            System.out.println("way" + 0 + " result");
            for(int j = 0; j < numSpot; j++){
                System.out.print(way[0][j].getLabel() +"/"+way[0][j].getName() + " ");
            }
            System.out.println();
        }
        else{
            for(int i = 0; i < fresult; i++){
                System.out.println("way" + i + " result");
                for(int j = 0; j < numSpot; j++){
                    System.out.print(way[i][j].getLabel() +"/"+way[i][j].getName() + " ");
                }
                System.out.println();
            }
        }

    }

    public String[][] chCarCost(){
        int[] totTaxi = new int[fresult];
        int[] rankway = new int[fresult];
        Rank rank;
        if(numSpot - 2 == 2){
            rank = new Rank(2);
        }
        else{
            rank = new Rank(3);
        }

        for(int i = 0; i < fresult; i++){
            totTaxi[i] = totTaxiCost(way[i]);
            rankway[i] = i;
        }

        for(int i = 0; i < fresult; i++){
            for(int j = i + 1; j < fresult; j++) {
                if (totTaxi[i] > totTaxi[j]) {
                    int tmp = totTaxi[i];
                    totTaxi[i] = totTaxi[j];
                    totTaxi[j] = tmp;
                    tmp = rankway[i];
                    rankway[i] = rankway[j];
                    rankway[j] = tmp;
                }
            }
        }

        System.out.println("cheapest way car");

        rank.setRank(totTaxi, rankway);
        rank.printRank();
        int tmp = rank.getSize();
        //int[] totTaxi2;
        rankway = rank.getRankWay();
        //totTaxi2 = rank.getValue();
        String resultPath[] = new String[tmp];
        String resultData[] = new String[tmp];

        String result[][] = new String[tmp][2];

        int[] totT = rank.getValue();
        int[] totT2 = new int[tmp];
        int[] totT3 = new int[tmp];

        for(int i = 0; i < tmp; i++){
            Spot[] w = way[rankway[i]];
            totT2[i] = Math.round(totTime(w) / 60);
            totT3[i] = totCarCost(w);
        }

        for(int i = 0; i < tmp; i++){
            Spot[] w = way[rankway[i]];
            resultPath[i] = setPathway(w);
            resultData[i] = "택시 " + totT[i] + "원 자가용 " + totT3[i] + "원 시간 " +totT2[i] + "분" ;
            System.out.println("result way " + i);
            System.out.println(resultPath[i] + ", " + resultData[i]);
            result[i][0] = resultPath[i];
            result[i][1] = resultData[i];
        }

        return result;
    }

    public String[][] shTimeC(){
        int[] totTime = new int[fresult];
        int[] rankway = new int[fresult];
        Rank rank;
        if(numSpot - 2 == 2){
            rank = new Rank(2);
        }
        else{
            rank = new Rank(3);
        }

        for(int i = 0; i < fresult; i++){
            totTime[i] = totTime(way[i]);
            rankway[i] = i;
        }

        for(int i = 0; i < fresult; i++){
            for(int j = i + 1; j < fresult; j++) {
                if (totTime[i] > totTime[j]) {
                    int tmp = totTime[i];
                    totTime[i] = totTime[j];
                    totTime[j] = tmp;
                    tmp = rankway[i];
                    rankway[i] = rankway[j];
                    rankway[j] = tmp;
                }
            }
        }

        System.out.println("shortest way Car");
        rank.setRank(totTime, rankway);
        rank.printRank();
        int tmp = rank.getSize();
        //int[] totTaxi2;
        rankway = rank.getRankWay();
        //totTaxi2 = rank.getValue();
        String resultPath[] = new String[tmp];
        String resultData[] = new String[tmp];

        String result[][] = new String[tmp][2];

        int[] totT = rank.getValue();
        int[] totT2 = new int[tmp];
        int[] totT3= new int[tmp];

        for(int i = 0; i < tmp; i++){
            totT[i] = Math.round(totT[i]/60);
            Spot[] w = way[rankway[i]];
            totT2[i] = totTaxiCost(w);
            totT3[i] = totCarCost(w);
        }

        for(int i = 0; i < tmp; i++){
            Spot[] w = way[rankway[i]];
            resultPath[i] = setPathway(w);
            resultData[i] = "택시 " + totT2[i] + "원 자가용" + totT3[i] + "원 시간" +totT[i] + "분" ;
            System.out.println("result way " + i);
            System.out.println(resultPath[i] + ", " + resultData[i]);
            result[i][0] = resultPath[i];
            result[i][1] = resultData[i];
        }

        return result;
    }

    public String[][] shTimeT(){
        int[] totTime = new int[fresult];
        int[] rankway = new int[fresult];
        Rank rank;
        if(numSpot - 2 == 2){
            rank = new Rank(2);
        }
        else{
            rank = new Rank(3);
        }

        for(int i = 0; i < fresult; i++){
            totTime[i] = totTime(way[i]);
            rankway[i] = i;
        }

        for(int i = 0; i < fresult; i++){
            for(int j = i + 1; j < fresult; j++) {
                if (totTime[i] > totTime[j]) {
                    int tmp = totTime[i];
                    totTime[i] = totTime[j];
                    totTime[j] = tmp;
                    tmp = rankway[i];
                    rankway[i] = rankway[j];
                    rankway[j] = tmp;
                }
            }
        }

        System.out.println("shortest way Transport");
        rank.setRank(totTime, rankway);
        rank.printRank();
        int tmp = rank.getSize();
        //int[] totTaxi2;
        rankway = rank.getRankWay();
        //totTaxi2 = rank.getValue();
        String resultPath[] = new String[tmp];
        String resultData[] = new String[tmp];

        String result[][] = new String[tmp][2];

        int[] totT = rank.getValue();
        int[] totT2 = new int[tmp];

        for(int i = 0; i < tmp; i++){
            totT[i] = Math.round(totT[i]/60);
            Spot[] w = way[rankway[i]];
            totT2[i] = totTaxiCost(w);
        }

        for(int i = 0; i < tmp; i++){
            Spot[] w = way[rankway[i]];
            resultPath[i] = setPathway(w);
            resultData[i] = totT[i] + "분" ;
            System.out.println("result way " + i);
            System.out.println(resultPath[i] + ", " + resultData[i]);
            result[i][0] = resultPath[i];
            result[i][1] = resultData[i];
        }

        return result;
    }

    public int totTaxiCost(Spot[] t){
        int result = 0;
        for(int i = 0; i < numSpot; i++){
            result = result + t[i].getCostTaxi();
        }
        return result;
    }

    public int totCarCost(Spot[] t){
        int result = 0;
        for(int i = 0; i < numSpot; i++){
            result = result + t[i].getCostCar();
        }
        return result;
    }

    public int totTime(Spot[] t){
        int result = 0;
        for(int i = 0; i < numSpot; i++){
            result = result + t[i].getTime();
        }
        return result;
    }

    /* 전체 경로 -> String으로 */
    public String setPathway(Spot[] w){
        String resultPath = w[0].getName();

        for(int i = 1; i < numSpot; i++){
            resultPath = resultPath +" - "+ w[i].getName();
        }

        return resultPath;
    }

    public String getPathway(){
        return pathway;
    }

    public void findCarFree(final String startName, final int startLabel, final String endName, final int endLabel, final String urlString){

        Thread thread = new Thread() {
            //자동차 최소비용
            int carFCost;
            int carFTaxi;
            int carFTime;

            String resultPath = "";
            @Override
            public void run() {
                try {
                    //LogManager.printLog("무료우선 Car : " + urlString);
                    URL url = new URL(urlString);

                    StringBuffer sb = new StringBuffer();

                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String strResult;

                    while ((strResult = reader.readLine()) != null) {
                        builder.append(strResult);
                    }

                    //LogManager.printLog("FREE result: " + builder.toString());
                    resultPath = builder.toString();

                    String stringTotalTime = "totalTime";
                    String stringTotalFare = "totalFare";
                    String stringTaxiFare = "taxiFare";
                    String stringIndex = "index";

                    String s1 = resultPath.substring((resultPath.indexOf(stringTotalTime)+stringTotalTime.length()+3),(resultPath.indexOf(stringTotalFare)-8));
                    carFTime = Integer.parseInt(s1);

                    String s2 = resultPath.substring((resultPath.indexOf(stringTotalFare)+stringTotalTime.length()+3),(resultPath.indexOf(stringTaxiFare)-8));
                    carFCost = Integer.parseInt(s2);

                    String s3 = resultPath.substring((resultPath.indexOf(stringTaxiFare)+stringTotalTime.length()+2),(resultPath.indexOf(stringIndex)-8));
                    carFTaxi = Integer.parseInt(s3);

                    LogManager.printLog("car 최소비용 " + startName + "-label : " + startLabel + "  " + endName + "-label : "
                            + endLabel + " time " + carFTime + " cost " + carFCost + " taxi " + carFTaxi);

                    /* Spot 만들기 */
                    Spot spot1 = new Spot(startLabel, startName);
                    Spot spot2 = new Spot(endLabel, endName, carFCost, carFTaxi, carFTime);

                    insertSpot(spot1, spot2);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        };
        thread.start();
    }

    //자동차 최단시간
    public void findCarShort(final String startName, final int startLabel, final String endName, final int endLabel, final String urlString) {
        Thread thread = new Thread() {
            //자동차 최소비용
            int carSCost;
            int carSTaxi;
            int carSTime;

            String resultPath = "";
            @Override
            public void run() {
                try {
                    //LogManager.printLog("최단시간 Car : " + urlString);
                    URL url = new URL(urlString);

                    StringBuffer sb = new StringBuffer();

                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String strResult;

                    while ((strResult = reader.readLine()) != null) {
                        builder.append(strResult);
                    }

                    // LogManager.printLog("FREE result: " + builder.toString());
                    resultPath = builder.toString();

                    String stringTotalTime = "totalTime";
                    String stringTotalFare = "totalFare";
                    String stringTaxiFare = "taxiFare";
                    String stringIndex = "index";

                    String s1 = resultPath.substring((resultPath.indexOf(stringTotalTime)+stringTotalTime.length()+3),(resultPath.indexOf(stringTotalFare)-8));
                    carSTime = Integer.parseInt(s1);

                    String s2 = resultPath.substring((resultPath.indexOf(stringTotalFare)+stringTotalTime.length()+3),(resultPath.indexOf(stringTaxiFare)-8));
                    carSCost = Integer.parseInt(s2);

                    String s3 = resultPath.substring((resultPath.indexOf(stringTaxiFare)+stringTotalTime.length()+2),(resultPath.indexOf(stringIndex)-8));
                    carSTaxi = Integer.parseInt(s3);

                    LogManager.printLog("car최단시간 " + startName + "-label : " + startLabel + "  " + endName + "-label : "
                            + endLabel + " time " + carSTime + " cost " + carSCost + " taxi " + carSTaxi);

                    /* Spot 만들기 */
                    Spot spot1 = new Spot(startLabel, startName);
                    Spot spot2 = new Spot(endLabel, endName, carSCost, carSTaxi, carSTime);

                    LogManager.printLog("Spot1 : " + spot1.getName());
                    LogManager.printLog("Spot2 : " + spot2.getName());

                    insertSpot(spot1, spot2);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    //대중교통 최단시간
    public void findBusShort(final String startName, final int startLabel, final String endName, final int endLabel, final String urlString) {
        Thread thread = new Thread() {
            //대중교통 최단시간
            int busSTime;

            String resultPath = "";
            @Override
            public void run() {
                try {
                    // LogManager.printLog("bus 최단시간" + urlString);
                    URL url = new URL(urlString);

                    StringBuffer sb = new StringBuffer();

                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String strResult;

                    while ((strResult = reader.readLine()) != null) {
                        builder.append(strResult);
                    }

                    LogManager.printLog("result: " + builder.toString());
                    resultPath = builder.toString();

                    String stringDuration = "duration";
                    String stringValue = "value";
                    String stringStatus = "status";
                    //String stringQutation = "\"";

                    String s1 = resultPath .substring((resultPath .indexOf(stringDuration)+stringDuration.length()+3),(resultPath .indexOf(stringStatus)-33));

                    String s2 = s1.substring((s1.indexOf(stringValue)+stringValue.length()+4),s1.length());
                    busSTime = Integer.parseInt(s2);

                    //status OK = 정상
                    //String s3 = resultPath.substring((resultPath.indexOf(stringStatus)+stringStatus.length()+5),(resultPath.indexOf(stringQutation)));
                    //LogManager.printLog("status: " + s3);

                    LogManager.printLog("bus최단시간 " + startName + "-label : " + startLabel + "  " + endName + "-label : "
                            + endLabel + " time " + busSTime);

                    /* Spot 만들기 */
                    Spot spot1 = new Spot(startLabel, startName);
                    Spot spot2 = new Spot(endLabel, endName, busSTime);

                    LogManager.printLog("Spot1 : " + spot1.getName());
                    LogManager.printLog("Spot2 : " + spot2.getName());

                    insertSpot(spot1, spot2);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}