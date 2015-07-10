package esqueletoretilineo;

/**
 * @author Felipe
 */
public class Edge {
    public Vertex source, destination;

    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
    }
    
    public Edge normalized(){
        Edge normalized = this.clone();
        Vector3 s = normalized.source.position;
        Vector3 d = normalized.destination.position;
        normalized.destination.position = Vector3.sum(s, Vector3.subtration(d, s).normalized());
        return normalized;
    }
    
    public Vector3 delta(){
        return Vector3.subtration(destination.position, source.position);
    }
    
    public Edge normalized(float distance){
       Edge normalized = this.clone();
        Vector3 s = normalized.source.position;
        Vector3 d = normalized.destination.position;
        Vector3 dir = Vector3.subtration(d, s).normalized();
        dir.x *= distance;
        dir.y *= distance;
        normalized.destination.position = Vector3.sum(s, dir);
        return normalized;
    }
    
    public Edge clone(){
        return new Edge(this.source.clone(), this.destination.clone());
    }
    
}
