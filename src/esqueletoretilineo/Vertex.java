
package esqueletoretilineo;

import java.util.ArrayList;

/**
 * @author Felipe
 */
public class Vertex {
    public Vector3 position;
    public ArrayList<Edge> edges;
    public boolean fromSkeletton;

    public Vertex(Vector3 position) {
        this.position = position;
        this.edges = new ArrayList<>();
        this.fromSkeletton = false;
    }

    public Vertex(Vector3 position, ArrayList<Edge> edges, boolean fromSkeletton) {
        this.position = position;
        this.edges = edges;
        this.fromSkeletton = fromSkeletton;
    }
    
    public Vertex right(){
        return this.edges.get(0).destination;
    }
    
    public Vertex left(){
        return this.edges.get(1).destination;
    }
    
    public float distanceToRight(){
        return Vector3.distance(position, this.right().position);
    }
    
    public float distanceToLeft(){
        return Vector3.distance(position, this.left().position);
    }
    
    public void addEdgesTo(Vertex other){
        this.edges.add(new Edge(this,other));
        other.edges.add(new Edge(other, this));
    }
    public void addEdgeTo(Vertex other){
        this.edges.add(new Edge(this,other));
    }
    
    public void swapRithtToLeft(){
        Edge e = this.edges.get(0);
        this.edges.remove(0);
        this.edges.add(e);
    }
    
    public boolean removeEdgeTo(Vertex other){
        if(this._removeEdgeTo(other)){
            return other._removeEdgeTo(this);
        }
        return false;
    }
    
    private boolean _removeEdgeTo(Vertex other){
        for(Edge edge : this.edges){
            if(edge.destination == other){
                return this.edges.remove(edge);
            }
        }
        return false;
    }
    
    public float angle(){
        Vector3 v1 = this.edges.get(0).normalized().delta();
        Vector3 v2 = this.edges.get(this.edges.size() - 1).normalized().delta();
        float angle = (float)(Math.acos(Vector3.dot(v1, v2)) / Math.PI);
        if(!Vector3.isRightHand(v1, v2)){
            angle = 2f - angle;
        }
        return angle;
    }
    
    public Edge median(){
        Edge a = this.edges.get(0).normalized();
        Edge b = this.edges.get(1).normalized();
        Vector3 d1 = a.destination.position;
        Vector3 d2 = b.destination.position;
        return new Edge(this, new Vertex(Vector3.subtration(Vector3.sum(d1,d2), position)));
    }
    
    public Edge median(float distance){
        Edge a = this.edges.get(0).normalized();
        Edge b = this.edges.get(1).normalized();
        Vector3 d1 = Vector3.subtration(a.destination.position,position);
        Vector3 d2 = Vector3.subtration(b.destination.position, position);
        Vector3 d = Vector3.sum(d1,d2).normalized();
        d.x*=distance;
        d.y*=distance;
        d = Vector3.sum(position, d);
        return new Edge(this, new Vertex(d));
    }
    
    @Override
    public Vertex clone(){
        return new Vertex(this.position.clone());
    }
    
}
