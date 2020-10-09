import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;


public class GUI extends JFrame {

    //Enviroment
    int screen_width = 1280;
    int screen_height = 720;
    String paint_screen_color = "#272324";
    String menu_bar_color = "#83B799";
    String vertex_color = "#E2CD6D";String vertex_color_selected = "#E86F68";
    String temp_edge_color = "#ED6A40";
    String edge_color = "#FCB7A8";
    String edge_color_selected = "#E86F68";
    static ListenerHelper helper;

    /*
    mode :
    0 = none
    1 = add vertex
    2 = add edge
     */
    int mode = 0;




    //List of Vertex
    Vertexs Vertexs = new Vertexs();
    Edges Edges = new Edges();

    TempEdge TempEdge = null;
    Object selected = null;

    //List of JFrame
    Canvas c;
    JPanel menubar;
    JPanel detail_vertex = new JPanel();
    JLabel show_name_vertex = new JLabel();
    JButton edit_vertex_name;
    JButton edit_edge_name;
    JButton edit_weight;


    //UI Create Method
    public Canvas create_paint_bar(){


        Canvas c = new Canvas() {
            @Override
            public void paint(Graphics g) {
                draw();
            }
        };
        c.setBackground(Color.decode(paint_screen_color));
        c.setBounds(0,0,screen_width-(screen_width/4),screen_height);
        return  c;
    }
    public JPanel create_menu_bar(){

        int l_space = 45;
        JButton create_vertex = new JButton("Create_Vertex");
        JButton create_edge = new JButton("Create_Egde");
        edit_vertex_name = new JButton("Edit Vertext Name");
        edit_edge_name = new JButton("Edit Edge Name");
        edit_weight = new JButton("Edit Weight");
        edit_vertex_name.setVisible(false);
        edit_edge_name.setVisible(false);
        detail_vertex = getDetail_vertex();
        edit_weight.setVisible(false);

        JPanel menubar = new JPanel();
        menubar.setBackground(Color.decode(menu_bar_color));
        menubar.setBounds(screen_width-(screen_width/4),0,(screen_width/4),screen_height);


        create_vertex.setBounds(screen_width-(screen_width/4),0,(screen_width/8),l_space);
        create_edge.setBounds(screen_width-(screen_width/4)+(screen_width/8),0,(screen_width/8),l_space);
        edit_vertex_name.setBounds(screen_width-(screen_width/4),100,(screen_width/4),l_space);
        edit_edge_name.setBounds(screen_width-(screen_width/4),100,(screen_width/4),l_space);
        edit_weight.setBounds(screen_width-(screen_width/4),100+l_space,(screen_width/4),l_space);

        create_vertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mode = 1;
                draw();
                update_status("Click in anywhere");

            }
        });
        create_edge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selected = null;
                mode = 2;
                draw();
                update_status("Click on Vertex");
            }
        });
        edit_vertex_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) throws NullPointerException{
                String ver_name = (String)JOptionPane.showInputDialog("Input New Name",((Vertex)selected).name);
                if(ver_name.length()>0){
                    ((Vertex) selected).name = ver_name;
                    success("Updated new name");
                    update_status("Name : "+((Vertex)selected).name+"      Degree : "+((Vertex) selected).degree);
                    draw();

                }
                else{
                    alert("Name must be 1 or more letter");
                }
            }
        });

        edit_edge_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) throws NullPointerException{
                String ver_name = (String)JOptionPane.showInputDialog("Input New Name",((Edge)selected).name);
                if(ver_name.length()>0){
                    ((Edge) selected).name = ver_name;
                    success("Updated new name");
                    update_status("Name : "+((Edge)selected).name+"       Weight : "+((Edge) selected).weight);
                    draw();

                }
                else{
                    alert("Name must be 1 or more letter");
                }
            }
        });

        edit_weight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)throws NullPointerException {
                String ver_name = (String)JOptionPane.showInputDialog("Input weigh",""+((Edge)selected).weight);
                try{
                    if(ver_name.length()>0){
                        ((Edge) selected).weight = Integer.parseInt(ver_name);
                        success("Updated Weight");
                        update_status("Name : "+((Edge)selected).name+"       Weight : "+((Edge) selected).weight);
                        draw();
                    }
                    else{
                        alert("Must have input");
                    }
                }
                catch (NumberFormatException e){
                    alert("Error please type new number");
                }

            }
        });

        getContentPane().add(detail_vertex);
        getContentPane().add(create_vertex);
        getContentPane().add(create_edge);
        getContentPane().add(edit_vertex_name);
        getContentPane().add(edit_edge_name);
        getContentPane().add(edit_weight);


        return menubar;
    }
    public JMenuBar mb(){
        GUI gui = this;
        JMenuBar mb = new JMenuBar();

        JMenu me1 = new JMenu("File");
        JMenuItem mSave = new JMenuItem("Save      Ctrl+S");
        JMenuItem mLoad = new JMenuItem("Open      Ctrl+O");
        JMenuItem mExit = new JMenuItem("Exit      Ctrl+W");

        mSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    helper.save();

                }
                catch (Exception e){

                }
            }
        });

        mLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    helper.open();
                    draw();
                }
                catch (Exception e){

                }
            }
        });

        JMenu me2 = new JMenu("Process");
        JMenuItem prim = new JMenuItem("Prim's algorithm");

        me2.add(prim);
        me1.add(mSave);
        me1.add(mLoad);
        me1.add(mExit);



        mb.add(me1);
        mb.add(me2);


        prim.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    try{
                        boolean check = false;
                        String start_vertex = (String)JOptionPane.showInputDialog(null,"Input your Start Vertex","Start Point",JOptionPane.PLAIN_MESSAGE);
                        if(start_vertex.length()>0){
                            for(Vertex v : Vertexs){
                                if(v.name.equals(start_vertex)){
                                    JPrim prim = new JPrim(start_vertex,gui);
                                    prim.draw();
                                    check=true;
                                    break;
                                }
                            }
                            if(!check) alert("don't have exit vertex name "+start_vertex);
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
            }
        });


        return mb;
    }
    public JPanel getDetail_vertex(){

        JPanel detail_vertex = new JPanel();

        detail_vertex.setBounds(screen_width-(screen_width/4),45,screen_width/4,45);
        detail_vertex.setBackground(Color.decode(menu_bar_color));
        detail_vertex.setBorder(BorderFactory.createTitledBorder("Detail"));

        detail_vertex.setVisible(true);
        detail_vertex.add(show_name_vertex);


        return detail_vertex;
    }


    //Controller Method
    public void add_Vertex(Vertex v){
        Vertexs.add(v);
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
        add(c);
        add(menubar);

        setJMenuBar(mb());
        setVisible(true);
    }

    BufferedImage grid = null;

    public static void main(String[] args){
        try{
            GUI gui = new GUI();
            helper = new ListenerHelper(gui);

        }
        catch(Exception e){

        }
    }

    public void update_status(String msg){
        show_name_vertex.setText(msg);
    }

    public void alert(String msg){
        JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
    public void success(String msg){
        JOptionPane.showMessageDialog(null,msg,"Success",JOptionPane.PLAIN_MESSAGE);
    }


    public void draw(){
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

        Edges.draw(g2);

        if (TempEdge != null) {
            TempEdge.draw(g2);
        }

        Vertexs.draw(g2);

        g.drawImage(grid, null, 0, 0);

    }
}