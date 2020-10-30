import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Vertexs extends ArrayList<Vertex> {

    int idGen = 0;

    public void add_vertex(int x,int y,String vertex_color,String vertex_color_selected){

        Vertex a = new Vertex(x,y,vertex_color,vertex_color_selected,idGen);
        idGen++;
        this.add(a);

    }

    public void delete_vertex(Vertex v,Edges e){
        for(int i=e.size()-1;i>=0;i--){
            if(e.get(i).vertexA==v||e.get(i).vertexB==v){

//                System.out.println("remove :"+i);
                e.delete_edge(e.get(i));

            }
        }
        this.remove(v);

        //handle idGen

        this.idGen = this.size();
        for(int i=0;i<this.size();i++){
            this.get(i).idGen = i;
        }

    }

    public void draw(Graphics2D g){
        for (Vertex s : this) {
            s.draw(g);
        }
    }


}
