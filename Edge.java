import java.awt.*;
import java.awt.geom.QuadCurve2D;

public class Edge{



    Vertex vertexA;
    Vertex vertexB;

    int x_center;
    int y_center;
    int r_center;
    String color;
    String color_selected;
    int missing = 7;
    int weight;

    boolean isSelect = false;
    String name = "E";
    static int idGen = 0;

    Edge(Vertex a , Vertex b,String color,String color_selected){
        this.name = this.name+idGen;
        idGen++;
        this.vertexA = a;
        this.vertexB = b;
        this.color = color;
        this.color_selected = color_selected;
        this.r_center = 25;
        this.weight = 1;

    }

    boolean inLine(int x0,int y0){
        return ((x0 - x_center) * (x0 - x_center) + (y0 - y_center) * (y0 - y_center)) <= r_center * r_center;
    }

    public void draw(Graphics2D g){
        g.setColor(isSelect?Color.decode(color_selected):Color.decode(color));
        g.setStroke(new BasicStroke(2));
        g.draw(new QuadCurve2D.Float(vertexA.x+missing, vertexA.y+missing, x_center+missing, y_center+missing, vertexB.x+missing, vertexB.y+missing));
        g.drawString(name,x_center+7,y_center);
    }



}
