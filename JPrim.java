import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class JPrim extends JFrame {

    String start;
    ArrayList<Vertex> Vertexs;
    ArrayList<Edge> Edges;
    GUI gui;
    BufferedImage grid = null;
    Canvas c;






    JPrim(String start,GUI gui) throws Exception {

        super("Prime's Algo");
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        setSize(gui.screen_width,gui.screen_height);
        setResizable(false);


        this.start = start;
        this.Vertexs = new ArrayList<>(gui.Vertexs);
        this.Edges = new ArrayList<>(gui.Edges);
        this.gui = gui;


        c = create_frame();
        add(c);
        setVisible(true);
//        draw();

    }


    public Canvas create_frame(){
        Canvas c = new Canvas(){
            @Override
            public void paint(Graphics g) {
                draw();
            }
        };
        c.setBackground(Color.decode(gui.paint_screen_color));
        c.setBounds(0,0,gui.screen_width-(gui.screen_width/4),gui.screen_height);
        return c;

    }





    public void draw(){ ;

//        c.setCursor(new Cursor(Cursor.HAND_CURSOR));


        Graphics2D g = (Graphics2D) c.getGraphics();

        if(grid==null){
            grid = (BufferedImage)createImage(c.getWidth(),c.getHeight());
        }

        Graphics2D g2 = grid.createGraphics();

        g2.setColor(Color.decode(gui.paint_screen_color));
        System.out.println(getWidth());
        g2.fillRect(0, 0, getWidth(), getHeight());

        for(Edge e : Edges){
            e.draw(g2);
        }

        for (Vertex s : Vertexs) {
            s.draw(g2);
        }

        g.drawImage(grid, null, 0, 0);


    }


}
