import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Vertexs extends ArrayList<Vertex> {



    public void add_vertex(Vertex v){

        this.add(v);

    }

    public void delete_vertex(Vertex v,Edges e){
        for(int i=e.size()-1;i>=0;i--){
            if(e.get(i).vertexA==v||e.get(i).vertexB==v){

                System.out.println("remove :"+i);
                e.delete_edge(e.get(i));

            }
        }
        this.remove(v);
    }

    public void draw(Graphics2D g){
        for (Vertex s : this) {
            s.draw(g);
        }
    }


}
