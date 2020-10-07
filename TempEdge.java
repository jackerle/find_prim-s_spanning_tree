import java.awt.*;

public class TempEdge {
    Vertex vertexA;
    int x1;
    int y1;
    String color;
    int missing = 7;

    TempEdge(int x, int y,String color) {
        this.x1 = x;
        this.y1 = y;
        this.color = color;
    }
    void setA(Vertex a) {
        this.vertexA = a;
    }

    void draw(Graphics2D g){
        g.setColor(Color.decode(color));
        g.setStroke(new BasicStroke(5));
        if(vertexA == null){
            return;
        }
        if(!vertexA.inCircle(x1,y1)){
            g.drawLine(vertexA.x+missing, vertexA.y+missing, x1, y1);
        }
    }

}
