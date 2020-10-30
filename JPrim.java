import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    DefaultTableModel tableModel;
    Canvas c;
    JPanel menubar;
    Vertex_table[] vt;
    Edges prim_tree = new Edges();
    Vertexs prim_vertex = new Vertexs();
    JTable table_prime;
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




        vt = new Vertex_table[Vertexs.size()];
        vt[start_index] = new Vertex_table(this.start,null,0);

        Vertex added = (Vertex)Vertexs.get(start_index).clone();
        added.width = 30;
        added.height = 30;
        added.color = "#66a103";
        added.miss = -7;
        prim_vertex.add(added);

        c = create_frame();
        menubar = create_bar();
        tableModel.setValueAt("-",start_index,1);
        tableModel.setValueAt("-",start_index,2);
        add(c);
        add(menubar);
        setVisible(true);
        init_();
//        draw();

    }


    public void init_ (){
        vt[start_index].isFinish = true;

        //-------------Change table-----------------//
        for(int i=0;i<Edges.size();i++){

            Edge edge = Edges.get(i);

            if(edge.vertexA.name.equals(this.start.name)){
                int id = edge.vertexB.idGen;

                if((vt[id]==null||((vt[id].distance>edge.weight)&&vt[id].isFinish==false))){
                    vt[id] = new Vertex_table(this.start,edge,edge.weight);
                    tableModel.setValueAt(edge.weight,edge.vertexB.idGen,1);
                    tableModel.setValueAt(this.start.name,edge.vertexB.idGen,2);
                }
            }
            if(Edges.get(i).vertexB.name.equals(this.start.name)){
                int id = edge.vertexA.idGen;

                if((vt[id]==null||((vt[id].distance>edge.weight)&&vt[id].isFinish==false))){
                    vt[id] = new Vertex_table(this.start,edge,edge.weight);
                    tableModel.setValueAt(edge.weight,edge.vertexA.idGen,1);
                    tableModel.setValueAt(this.start.name,edge.vertexA.idGen,2);

                }
            }

        }
    }


    public void find_prim_tree(){


        if(N<Vertexs.size()-1){

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


            //set next target
            vt[next].isFinish = true;
            System.out.println(vt[next].isFinish);
            this.start = Vertexs.get(next);
            Vertex added = (Vertex)Vertexs.get(next).clone();
            tableModel.setValueAt("-",next,1);
            tableModel.setValueAt("-",next,2);
            added.width = 30;
            added.height = 30;
            added.color = "#ff0000";
            added.miss = -7;
            prim_vertex.add(added);
            vt[next].line.stroke = 10;
            vt[next].line.isSelect = true;
            vt[next].line.color_selected = "#ff0000";
            prim_tree.add(vt[next].line);
            System.out.println(this.start.name);
            //////////////////////


            N++;

            draw();



            vt[start_index].isFinish = true;

            //-------------Change table-----------------//
            for(int i=0;i<Edges.size();i++){

                Edge edge = Edges.get(i);

                if(edge.vertexA.name.equals(this.start.name)){
                    int id = edge.vertexB.idGen;

                    if((vt[id]==null||((vt[id].distance>edge.weight)&&vt[id].isFinish==false))){
                        vt[id] = new Vertex_table(this.start,edge,edge.weight);
                        tableModel.setValueAt(edge.weight,edge.vertexB.idGen,1);
                        tableModel.setValueAt(this.start.name,edge.vertexB.idGen,2);
                    }
                }
                if(Edges.get(i).vertexB.name.equals(this.start.name)){
                    int id = edge.vertexA.idGen;

                    if((vt[id]==null||((vt[id].distance>edge.weight)&&vt[id].isFinish==false))){
                        vt[id] = new Vertex_table(this.start,edge,edge.weight);
                        tableModel.setValueAt(edge.weight,edge.vertexA.idGen,1);
                        tableModel.setValueAt(this.start.name,edge.vertexA.idGen,2);

                    }
                }

            }




//            find_prim_tree();
        }
        else{
            System.out.println("finish");
            gui.success("Finish!");
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

    public JPanel create_bar(){
        JPanel menubar = new JPanel();
        JButton next_butt = new JButton("Next");

        menubar.setBackground(Color.decode(gui.menu_bar_color));
        menubar.setBounds(gui.screen_width-(gui.screen_width/4),0,(gui.screen_width/4),gui.screen_height);


        Object[] column = {"Vertex","Weight","From"};
        tableModel = new DefaultTableModel(null,column);

        Object[][] data  = new Object[vt.length][3];

        for(int i=0;i<vt.length;i++){
            data[i][0] = Vertexs.get(i).name;
            data[i][1] = "inf";
            data[i][2] = "inf";
            tableModel.addRow(data[i]);
        }


        table_prime = new JTable(tableModel);
        table_prime.setFont(new Font("Serif", Font.BOLD, 18));
        table_prime.setRowHeight(table_prime.getRowHeight()+15);
        table_prime.setEnabled(false);

        JScrollPane jpane = new JScrollPane(table_prime);
        jpane.setBounds(gui.screen_width-(gui.screen_width/4)+30,30,(gui.screen_width/8)+60,300);
        next_butt.setBounds(gui.screen_width-(gui.screen_width/4)+30,380,(gui.screen_width/8),45);
        next_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                find_prim_tree();
            }
        });
        getContentPane().add(jpane);
        getContentPane().add(next_butt);

        return menubar;
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

        for (Vertex v : prim_vertex){
            if(v!=null)
                v.draw(g2);
        }

        g.drawImage(grid, null, 0, 0);
        tableModel.fireTableDataChanged();

    }


}
