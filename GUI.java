import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GUI extends JFrame implements MouseListener, MouseMotionListener {

    //Enviroment
    private int screen_width = 1280;
    private int screen_height = 720;
    private String paint_screen_color = "#272324";
    private String menu_bar_color = "#83B799";
    private String vertex_color = "#E2CD6D";
    private String vertex_color_selected = "#E86F68";
    private String temp_edge_color = "#ED6A40";
    private String edge_color = "#FCB7A8";
    private String edge_color_selected = "#E86F68";

    /*
    mode :
    0 = none
    1 = add vertex
    2 = add edge
     */
    int mode = 0;

    //List of Vertex
    ArrayList<Vertex> Vertexs = new ArrayList<>();
    ArrayList<Edge> Edges = new ArrayList<>();
    TempEdge TempEdge = null;
    Object selected = null;

    //List of JFrame
    Canvas c;
    JPanel menubar = new JPanel();
    JPanel detail_vertex = new JPanel();
    JLabel show_name_vertex = new JLabel();


    public Canvas create_paint_bar(){


        Canvas c = new Canvas() {
            @Override
            public void paint(Graphics g) {
            }
        };
        c.setBackground(Color.decode(paint_screen_color));
        c.setBounds(0,0,screen_width-(screen_width/4),screen_height);
        return  c;
    }
    public JPanel create_menu_bar(){

        int l_space = 45;
        int shift = 45;
        JButton create_vertex = new JButton("Create_Vertex");
        JButton create_edge = new JButton("Create_Egde");
        detail_vertex = getDetail_vertex();

        JPanel menubar = new JPanel();
        menubar.setBackground(Color.decode(menu_bar_color));
        menubar.setBounds(screen_width-(screen_width/4),0,(screen_width/4),screen_height);


        create_vertex.setBounds(screen_width-(screen_width/4),0,(screen_width/8),l_space);
        create_edge.setBounds(screen_width-(screen_width/4)+(screen_width/8),0,(screen_width/8),l_space);


        create_vertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mode = 1;
                draw();
                System.out.printf("setMode1");

            }
        });
        create_edge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selected = null;
                mode = 2;
                draw();
                System.out.println("setMode2");
            }
        });

        getContentPane().add(detail_vertex);
        getContentPane().add(create_vertex);
        getContentPane().add(create_edge);

        return menubar;
    }
    public JMenuBar mb(){
        JMenuBar mb = new JMenuBar();
        JMenu me1 = new JMenu("File");
        JMenuItem mSave = new JMenuItem("Save");
        JMenuItem mLoad = new JMenuItem("Load");
        JMenuItem mExit = new JMenuItem("Exit");
        me1.add(mSave);
        me1.add(mLoad);
        me1.add(mExit);
        mb.add(me1);
        return mb;
    }
    public JPanel getDetail_vertex(){

        JPanel detail_vertex = new JPanel();
        JLabel show_name  = new JLabel();

        detail_vertex.setBounds(screen_width-(screen_width/4),45,screen_width/4,200);
        detail_vertex.setBackground(Color.decode(menu_bar_color));
        detail_vertex.setBorder(BorderFactory.createTitledBorder("Detail"));

        show_name.setBounds(screen_width-(screen_width/4)+10,50,screen_width/4,200);

        detail_vertex.setVisible(false);
        show_name_vertex = show_name;
        detail_vertex.add(show_name_vertex);


        return detail_vertex;
    }

    /**
     * Constructor
     */
    GUI() throws Exception{
        super("Draw_Tree");
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        setSize(screen_width,screen_height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menubar = create_menu_bar();
        c = create_paint_bar();
        c.addMouseListener(this);
        c.addMouseMotionListener(this);
        add(c);
        add(menubar);

        setJMenuBar(mb());
        setVisible(true);
    }



    BufferedImage grid = null;

    public void draw(){

        if(Vertexs.contains(selected)){
            detail_vertex.setVisible(true);
            show_name_vertex.setText("Name : "+((Vertex)selected).name);
        }
        else{
            detail_vertex.setVisible(false);
        }

        c.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(mode==1){
            c.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
        if(mode==2){
            c.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        Graphics2D g = (Graphics2D) c.getGraphics();
        if(grid==null){
            grid = (BufferedImage)createImage(c.getWidth(),c.getHeight());
        }

        Graphics2D g2 = grid.createGraphics();
        g2.setColor(Color.decode(paint_screen_color));
        g2.fillRect(0, 0, getWidth(), getHeight());

        for(Edge e : Edges){
            e.draw(g2);
        }

        if (TempEdge != null) {
            TempEdge.draw(g2);
        }

        for (Vertex s : Vertexs) {
            s.draw(g2);
        }

        g.drawImage(grid, null, 0, 0);
    }



    public void set_selected(int x,int y){
        Object obj = null;
        for(Vertex s :Vertexs){
            if (s.inCircle(x, y)) {
                s.isSelect = true;
                System.out.println("in circle");
                obj = s;
                break;
            }
        }
        if(obj == null) {
            // click in nothing
            if (selected == null) {
                return;
            } else {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    s.isSelect = false;
//                } else {
//                    Edge_ t = (Edge_) selected;
//                    t.isSelect = false;
//                }
                    selected =null;
                }
            }
        }

        else{
            if(selected==null){
                selected = obj;
            }else {
                if(obj ==selected)return;
                else{
                    if (selected instanceof Vertex) {
                        Vertex s = (Vertex) selected;
                        s.isSelect = false;

                    } else {
//                        Edge_ t = (Edge_) selected;
//                        t.isSelect = false;
                    }
                    selected = obj;
                }
            }
        }

    }









    public static void main(String[] args){
        try{
            GUI gui = new GUI();
        }
        catch(Exception e){

        }
    }




    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //check if in create_mode
        if(e.getButton()==MouseEvent.BUTTON1){

            set_selected(x,y);

            if(mode==1){
                if(!Vertexs.contains(selected)){
                Vertexs.add(new Vertex(x,y,vertex_color,vertex_color_selected));
                this.mode = 0;
                selected = null;                }
                else{
                    this.mode=0;
                }
            }


            if(mode==2&&Vertexs.contains(selected)){
                Vertex v = (Vertex)selected;

                //create Edge
                if(TempEdge!=null&&!TempEdge.vertexA.name.equals(v.name)){


                    TempEdge.x1 = v.x;
                    TempEdge.y1 = v.y;
                    Edge edge = new Edge(TempEdge.vertexA,v,edge_color,edge_color_selected);
                    edge.x_center = (TempEdge.vertexA.x + v.x)/2;
                    edge.y_center = (TempEdge.vertexA.y + v.y)/2;

                    Edges.add(edge);


                    TempEdge = null;
                    this.mode = 0;
                    ((Vertex)selected).isOverOnTempEdge = false;
                }
                else{
                    TempEdge = new TempEdge(x,y,temp_edge_color);
                    TempEdge.setA((Vertex) selected);
                }
            }
            else if(mode==2&&selected==null){
                mode=0;
                TempEdge = null;
            }
        }
        else if (e.getButton()==MouseEvent.BUTTON3){
            if(mode==2)TempEdge=null;
            if(mode!=0)mode=0;

        }
        draw();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();


        if(mode==2&&selected!=null&&selected instanceof Vertex){
            for(Vertex s : Vertexs){
                if(s.inCircle(x,y)&&!s.name.equals(((Vertex)selected).name)){
                    System.out.println("on temp");
                    s.isOverOnTempEdge = true;
                }
                else{
                    s.isOverOnTempEdge = false;
                }
            }
            //System.out.println(x);
            Vertex s = (Vertex) selected;
            TempEdge.x1 = x;
            TempEdge.y1 = y;

            draw();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }


}