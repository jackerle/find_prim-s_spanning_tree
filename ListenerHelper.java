import java.awt.event.*;
import java.util.ArrayList;

public class ListenerHelper implements MouseListener, MouseMotionListener, KeyListener {

    GUI gui;


    ListenerHelper(GUI gui){
        this.gui = gui;
        this.gui.c.addMouseListener(this);
        this.gui.c.addMouseMotionListener(this);
        this.gui.c.addKeyListener(this);
    }

    public void set_selected(int x,int y){
        Object obj = null;
        for(Vertex s :gui.Vertexs){
            if (s.inCircle(x, y)) {
                s.isSelect = true;
//                System.out.println("in circle");
                obj = s;
                break;
            }
        }
        if(obj == null){
            for (Edge t : gui.Edges) {
                if (t.inLine(x, y)) {
                    t.isSelect = true;
                    obj = t;
                    break;
                }
            }
        }

        if(obj == null) {
            // click in nothing
            if (gui.selected == null) {
                return;
            } else {
                if (gui.selected instanceof Vertex) {
                    Vertex s = (Vertex) gui.selected;
                    s.isSelect = false;
                } else {
                    Edge t = (Edge) gui.selected;
                    t.isSelect = false;
                }
                gui.selected =null;
            }
        }

        else{
            if(gui.selected==null){
                gui.selected = obj;
            }else {
                if(obj ==gui.selected)return;
                else{
                    if (gui.selected instanceof Vertex) {
                        Vertex s = (Vertex) gui.selected;
                        s.isSelect = false;

                    } else {
                        Edge t = (Edge) gui.selected;
                        t.isSelect = false;
                    }
                    gui.selected = obj;
                }
            }
        }

        if(gui.Vertexs.contains(gui.selected)&&gui.mode!=2){
            gui.update_status("Name : "+((Vertex)gui.selected).name+"      Degree : "+((Vertex) gui.selected).degree);
            gui.edit.setVisible(true);
            gui.edit.setText("Edit Vertex");

        }
        else if(gui.Edges.contains(gui.selected)){
            gui.update_status("Name : "+((Edge)gui.selected).name);
            gui.edit.setVisible(true);
            gui.edit.setText("Edit Edge");
        }
        else{
            gui.update_status("");
            gui.edit.setVisible(false);
        }
    }




    @Override
    public void keyTyped(KeyEvent ke) {

        int key_event = (int)ke.getKeyChar();

        if(key_event == 127){ //delete

            if(gui.selected instanceof Vertex){

                gui.Vertexs.delete_vertex((Vertex) gui.selected,gui.Edges);
                gui.update_status("deleted Vertex : "+((Vertex) gui.selected).name);

            }
            if(gui.selected instanceof Edge){

                gui.Edges.delete_edge((Edge) gui.selected);
                gui.update_status("deleted Edge : "+((Edge) gui.selected).name);

            }

            gui.selected = null;
        }

        gui.draw();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();


        //check if in create_mode
        if(e.getButton()==MouseEvent.BUTTON1){

            set_selected(x,y);

            if(gui.mode==1){
                if(!gui.Vertexs.contains(gui.selected)){
                    Vertex a = new Vertex(x,y,gui.vertex_color,gui.vertex_color_selected);

                    gui.Vertexs.add_vertex(a);

                    gui.mode = 0;
                    gui.selected = null;
                    gui.update_status("created Verrtex : "+a.name);
                }
                else{
                    gui.mode=0;
                }
            }


            if(gui.mode==2&&gui.Vertexs.contains(gui.selected)){
                Vertex v = (Vertex)gui.selected;

                //create Edge
                if(gui.TempEdge!=null&&!gui.TempEdge.vertexA.name.equals(v.name)){
                    boolean duplicate = false;
                    for(Edge ed : gui.Edges){
                        if((ed.vertexB == gui.TempEdge.vertexA&&ed.vertexA==v)||(ed.vertexA == gui.TempEdge.vertexA &&ed.vertexB==v)){
                            duplicate = true;
                        }
                    }
                    if(duplicate){
                        gui.update_status("It's duplicate Edge");
                    }
                    else{
                        gui.TempEdge.x1 = v.x;
                        gui.TempEdge.y1 = v.y;
                        Edge edge = new Edge(gui.TempEdge.vertexA,v,gui.edge_color,gui.edge_color_selected);
                        edge.x_center = (gui.TempEdge.vertexA.x + v.x)/2;
                        edge.y_center = (gui.TempEdge.vertexA.y + v.y)/2;

                        gui.Edges.add_edge(edge);
                        gui.update_status("created Edge : "+edge.name);
                    }

                    gui.TempEdge = null;
                    gui.mode = 0;
                    ((Vertex)gui.selected).isOverOnTempEdge = false;
                    ((Vertex)gui.selected).isSelect = false;
                    gui.selected = null;

                }
                else{
                    gui.TempEdge = new TempEdge(x,y,gui.temp_edge_color);
                    gui.TempEdge.setA((Vertex) gui.selected);

                }
            }
            else if(gui.mode==2&&gui.selected==null){
                gui.mode=0;
                gui.TempEdge = null;
                gui.update_status("Click on Vertex! (You clicked on Nothing)");
            }
            else if(gui.mode==2&&gui.selected instanceof Edge){
                gui.mode = 0;
                gui.TempEdge = null;
                ((Edge) gui.selected).isSelect = false;
                gui.selected = null;
                gui.update_status("Click  on Vertex! (You clicked on Edge)");
            }
        }
        else if (e.getButton()==MouseEvent.BUTTON3){
            if(gui.mode==2)gui.TempEdge=null;
            if(gui.mode!=0)gui.mode=0;

        }
        gui.draw();
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

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();


        if(gui.mode==2&&gui.selected!=null&&gui.selected instanceof Vertex){
            for(Vertex s : gui.Vertexs){
                if(s.inCircle(x,y)&&!s.name.equals(((Vertex)gui.selected).name)){
                    System.out.println("on temp");
                    s.isOverOnTempEdge = true;
                }
                else{
                    s.isOverOnTempEdge = false;
                }
            }
            //System.out.println(x);
            Vertex s = (Vertex) gui.selected;
            gui.TempEdge.x1 = x;
            gui.TempEdge.y1 = y;

            gui.draw();
        }
    }
}
