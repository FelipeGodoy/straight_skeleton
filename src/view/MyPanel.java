package view;

import esqueletoretilineo.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import javax.swing.JPanel;

/**
 *
 * @author Felipe
 */
public class MyPanel extends JPanel {
    
    private final float RADIUS = 8f;
    
    public static MyPanel instance;
    
    public MyPanel(){
        super();
        instance = this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEdges(g, Color.red, Polygon.instance().skeletonEdges);
        drawVertices(g, Color.black, Polygon.instance().skeletonVertices);
        drawEdges(g,Color.green, Polygon.instance().edges);
        drawVertices(g, Color.blue, Polygon.instance().vertices);
        
    }
    
    private void drawVertices(Graphics g, Color c, Collection<Vertex> vertices){
        g.setColor(c);
        for (Vertex vertex : vertices) {
            g.fillOval(Math.round(vertex.position.x - Math.round(RADIUS) * 0.5f), Math.round(vertex.position.y - Math.round(RADIUS) * 0.5f), Math.round(RADIUS), Math.round(RADIUS));
        }
    }
    
    private void drawEdges(Graphics g, Color c, Collection<Edge> edges){
        g.setColor(c);
        for (Edge edge : edges) {
            g.drawLine((int) edge.source.position.x, (int) edge.source.position.y, (int) edge.destination.position.x, (int) edge.destination.position.y);
        }
    }

}
