import java.awt.*;

public class Vertex implements Cloneable{


    int width = 13;
    int height = 13;
    int r = 15;
    int miss = 0;
    int degree;
    boolean isSelect = false;
    boolean isOverOnTempEdge = false;
    int idGen;
    String name;
    String color_selected;

    int x, y;
    String color;

    public Vertex(int x, int y,String color,String color_selected,int idGen) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.color_selected = color_selected;
        this.degree =0;
        this.idGen = idGen;
        name = "V"+idGen;
    }

    public Object clone(){
        try{
            return super.clone();
        }
        catch (Exception e){
            return null;
        }

    }


    boolean inCircle(int x0, int y0) {
        return ((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y)) <= r * r;
    }

    public void draw(Graphics2D g) {
        if(isSelect)g.setColor(Color.decode(color_selected));
        else if(isOverOnTempEdge)g.setColor(Color.decode(color_selected));
        else g.setColor(Color.decode(color));
        g.drawString(name,x,y-3);
        g.fillOval(x+miss,y+miss,width,height);
    }
}
