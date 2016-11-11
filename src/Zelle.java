/**
 * Created by oem on 10/22/16.
 */
public class Zelle{
    public int heuristicCost = 0; //bis hirhin Kosten
    public int finalCost = 0; //gesamt Kosten
    public int x;
    public int y;
    public Zelle parent;

    public Zelle (int x, int y){
        this.x = x;
        this.y = y;
    }
    public Zelle (){

    }
    @Override
    public String toString(){
        return "["+this.x+", "+this.y+"]";
    }
}