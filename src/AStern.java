/**
 * Created by oem on 10/22/16.
 */
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.*;

public class AStern {
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    private static Zelle [][] zelle = new Zelle[5][5];

    private static PriorityQueue<Zelle> openlist;

    private static boolean closed[][];
    private static int startX, startY;
    private static int endI, endJ;

    public static void setWand(int x, int y){
        zelle[x][y] = null;
    }

    public static void setStartZelle(int x, int y){
        startX = x;
        startY = y;
    }

    public static void setEndZelle(int x, int y){
        endI = x;
        endJ = y;
    }

    static void kosten(Zelle current, Zelle t, int cost){
        if(t == null || closed[t.x][t.y])return;
        int t_final_cost = t.heuristicCost+cost;

        boolean inopenlist = openlist.contains(t);
        if(!inopenlist || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inopenlist)openlist.add(t);
        }
    }

    public static void AStar(){

        //add the start location to openlist list.
        openlist.add(zelle[startX][startY]);

        Zelle current;

        while(true){
            current = openlist.poll();
            if(current==null)
                break;
            closed[current.x][current.y]=true;

            if(current.equals(zelle[endI][endJ])){
                return;
            }

            Zelle t;
            if(current.x-1>=0){
                t = zelle[current.x-1][current.y];
                kosten(current, t, current.finalCost+V_H_COST);

                if(current.y-1>=0){
                    t = zelle[current.x-1][current.y-1];
                    kosten(current, t, current.finalCost+DIAGONAL_COST);
                }

                if(current.y+1<zelle[0].length){
                    t = zelle[current.x-1][current.y+1];
                    kosten(current, t, current.finalCost+DIAGONAL_COST);
                }
            }

            if(current.y-1>=0){
                t = zelle[current.x][current.y-1];
                kosten(current, t, current.finalCost+V_H_COST);
            }

            if(current.y+1<zelle[0].length){
                t = zelle[current.x][current.y+1];
                kosten(current, t, current.finalCost+V_H_COST);
            }

            if(current.x+1<zelle.length){
                t = zelle[current.x+1][current.y];
                kosten(current, t, current.finalCost+V_H_COST);

                if(current.y-1>=0){
                    t = zelle[current.x+1][current.y-1];
                    kosten(current, t, current.finalCost+DIAGONAL_COST);
                }

                if(current.y+1<zelle[0].length){
                    t = zelle[current.x+1][current.y+1];
                    kosten(current, t, current.finalCost+DIAGONAL_COST);
                }
            }
        }
    }

    public static void test(int xgroesse, int ygroesse, int sy, int sx, int ey, int ex, int[][] wand, Writer writer){
        System.out.println("\n\nTest");
        //Reset
        zelle = new Zelle[xgroesse][ygroesse];
        closed = new boolean[xgroesse][ygroesse];
        openlist = new PriorityQueue<>((Object o1, Object o2) -> {
            Zelle c1 = (Zelle)o1;
            Zelle c2 = (Zelle)o2;

            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
        });
        setStartZelle(sy, sx);

        setEndZelle(ey, ex);

        for(int i=0;i<xgroesse;++i){
            for(int j=0;j<ygroesse;++j){
                zelle[i][j] = new Zelle(i, j);
                zelle[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
            }
        }
        zelle[sy][sx].finalCost = 0;

        //Wände werden auf null gesetzt
        for(int i=0;i<wand.length;++i){
            setWand(wand[i][0], wand[i][1]);
        }

        //Ausgabe Matrix
        System.out.println("zelle: ");
        for(int i=0;i<xgroesse;++i){
            for(int j=0;j<ygroesse;++j){
                if(i==sy&&j==sx)
                    System.out.print("ST  ");
                else if(i==ey && j==ex)
                    System.out.print("ZL  ");
                else if(zelle[i][j]!=null)
                    System.out.printf("%-3d ", 0);
                else
                    System.out.print("WD  ");
            }
            System.out.println();
        }
        System.out.println();

        AStar();
        System.out.println("\nKosten pro Zelle: ");
        for(int i=0;i<xgroesse;++i){
            for(int j=0;j<xgroesse;++j){
                if(zelle[i][j]!=null)
                    System.out.printf("%-3d ", zelle[i][j].finalCost);
                else
                    System.out.print("WD  ");
            }
            System.out.println();
        }
        System.out.println();

        if(closed[endI][endJ]){
            System.out.println("Weg: ");
            try {
                writer.write("Weg: ");
            }catch (Exception e2){
                e2.printStackTrace();
            }
            Zelle current = zelle[endI][endJ];
            Zelle temp = current;
            ArrayList<String> list = new ArrayList<String>();
            while(current.parent!=null){
                list.add(""+current.parent);
                current = current.parent;
            }
            for (int i = list.size()-1; i >-1 ; i--) {
                try {
                    writer.write(list.get(i)+"->");
                    System.out.print(list.get(i)+"->");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                writer.write(""+temp+"\n");
                writer.flush();
                writer.close();
                System.out.println(temp);
                System.out.println();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }else
            try{
                writer.write("Kein Weg möglich!\n");
                writer.flush();
                writer.close();
            }catch (Exception e3){
                e3.printStackTrace();
            }
        System.out.println("Kein Weg möglich!");

    }

}