import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class JPrim extends JFrame {

    Vertex start;
    int start_index;
    Vertexs Vertexs = new Vertexs();
    Edges Edges = new Edges();
    GUI gui;
    BufferedImage grid = null;
    Canvas c;
    Vertex_table[] vt;
    Edges prim_tree = new Edges();
    Vertexs prim_vertex = new Vertexs();
    int N = 0;



    class Vertex_table{
        boolean isFinish = false;
        Edge line;
        Vertex destination;
        int distance;

        Vertex_table(Vertex destination,Edge line,int distance){
            this.destination = destination;
            this.distance = distance;
            if(line!=null) this.line = (Edge) line.clone();
        }

    }





    JPrim(String start,GUI gui) throws Exception {

        super("Prime's Algo");

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        setSize(gui.screen_width,gui.screen_height);
        setResizable(false);


        this.gui = gui;
        this.Vertexs.idGen = gui.Vertexs.idGen;
        this.Edges.idGen = gui.Edges.idGen;
        this.Vertexs.addAll(this.gui.Vertexs);
        this.Edges.addAll(this.gui.Edges);




        for(int i = 0;i<Vertexs.size();i++){
            if(start.equals(Vertexs.get(i).name)){
                this.start = Vertexs.get(i);
                this.start_index = i;
            }
        }

        c = create_frame();
        add(c);
        setVisible(true);


        vt = new Vertex_table[Vertexs.size()];
        vt[start_index] = new Vertex_table(this.start,null,0);

        find_prim_tree();
//        draw();

    }


    public void find_prim_tree(){

        if(N<Vertexs.size()-1){
            vt[start_index].isFinish = true;

            //-------------Change table-----------------//
            for(int i=0;i<Edges.size();i++){

                Edge edge = Edges.get(i);

                if(edge.vertexA.name.equals(this.start.name)){
                    int id = edge.vertexB.idGen;

                    if((vt[id]==null||((vt[id].distance>edge.weight)&&!vt[id].isFinish))){
                        vt[id] = new Vertex_table(this.start,edge,edge.weight);
                    }
                }
                if(Edges.get(i).vertexB.name.equals(this.start.name)){
                    int id = edge.vertexA.idGen;

                    if((vt[id]==null||((vt[id].distance>edge.weight)&&!vt[id].isFinish))){
                        vt[id] = new Vertex_table(this.start,edge,edge.weight);
                    }
                }

            }


            //---------------------------------------

            int next = 0;
            int min  = Integer.MAX_VALUE;
            for(int i=0;i<vt.length;i++){
                if(vt[i]!=null&&!vt[i].isFinish){
                    System.out.println(vt[i].distance+" from "+Vertexs.get(i).name);
                }
                if(vt[i]!=null&&vt[i].distance<min&&!vt[i].isFinish){
                    next = i;
                    min = vt[i].distance;

                }
            }

            vt[next].isFinish = true;
            this.start = Vertexs.get(next);
            vt[next].line.stroke = 10;
            vt[next].line.isSelect = true;
            vt[next].line.color_selected = "#C2E2C3";
            prim_tree.add(vt[next].line);
            System.out.println(this.start.name);

            N++;

            draw();
            find_prim_tree();
        }
        else{
            System.out.println("finish");
        }

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
        g2.fillRect(0, 0, getWidth(), getHeight());

        for(Edge e : Edges){
            if (e!=null)
            e.draw(g2);
        }

        for (Vertex s : Vertexs) {
            if(s!=null)
            s.draw(g2);
        }

        for (Edge e : prim_tree){
            if(e!=null)
                e.draw(g2);
        }

        g.drawImage(grid, null, 0, 0);


    }


}
