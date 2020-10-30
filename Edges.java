import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Edges extends ArrayList<Edge>{

    public int idGen = 0;

    public void add_edge(Edge e){


        this.add(e);
        e.vertexA.degree++;
        e.vertexB.degree++;
        e.setId(idGen);
        idGen++;

    }

    public void delete_edge(Edge e){

        e.vertexB.degree--;
        e.vertexA.degree--;
        this.remove(e);

        //handle id_gen

        this.idGen = this.size();
        for(int i=0;i<this.size();i++){
            this.get(i).idGen = i;
        }

    }


    public void draw(Graphics2D g){
        for(Edge e : this){
            e.draw(g);
        }
    }


}
