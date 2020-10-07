import java.awt.*;

public class Vertex {
    int width = 13;
    int height = 13;
    int r = 15;
    boolean isSelect = false;
    boolean isOverOnTempEdge = false;
    static int idGen=0;
    String name;
    String color_selected;

    int x, y;
    String color;

    public Vertex(int x, int y,String color,String color_selected) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.color_selected = color_selected;
        name = "V"+this.idGen;
        idGen++;
    }


    boolean inCircle(int x0, int y0) {
        return ((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y)) <= r * r;
    }

    public void draw(Graphics2D g) {
        if(isSelect)g.setColor(Color.decode(color_selected));
        else if(isOverOnTempEdge)g.setColor(Color.decode(color_selected));
        else g.setColor(Color.decode(color));
        g.drawString(name,x,y-3);
        g.fillOval(x,y,width,height);
    }
}
