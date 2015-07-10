package esqueletoretilineo;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Felipe
 */
public class Polygon {

    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;

    public ArrayList<Vertex> skeletonVertices;
    public ArrayList<Edge> skeletonEdges;

    public ArrayList<Corner> processingCorners;

    protected static Polygon instance;

    public Polygon() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.skeletonVertices = new ArrayList<>();
        this.skeletonEdges = new ArrayList<>();
        this.processingCorners = new ArrayList<>();
    }

    public static Polygon instance() {
        if (instance == null) {
            instance = new Polygon();
        }
        return instance;
    }

    private boolean rightHand() {
        Vertex min = this.vertices.get(0);
        for (Vertex vert : this.vertices) {
            if (vert.position.y < min.position.y) {
                min = vert;
            }
        }
        return min.angle() <= 1f;
    }

    public void applySkeleton() {
        this.clearSkeleton();
        this.setupProcessingCorners();
//        boolean rightHand = rightHand();
//        int count = 0;
//        ArrayList<Collision> collisions = new ArrayList<>();
//        while(processingCorners.size() > 1 && count < 100){
//            for(int i =0; i < processingCorners.size(); i++){
//                Corner c = processingCorners.get(i);
//                c.applyMedianStep();
//                collisions.add(new CornerCollision(processingCorners, i));
//            }
//            for(Collision c : collisions){
//                c.ApplyCollision();
//            }
//            skeletonEdges.clear();
//            skeletonVertices.clear();
//            collisions.clear();
//            count++;
//        }
        for (Corner c : processingCorners) {
            c.applyMedianStep();
            skeletonEdges.add(c.link);
            skeletonVertices.add(c.link.destination);
        }
    }

    public void applyStep() {
        int count = 0;
        while(processingCorners != null && processingCorners.size() >= 3 && count < 10000) {
            ArrayList<Collision> collisions = new ArrayList<>();
            for(int i =0; i < processingCorners.size(); i++){
                Corner c = processingCorners.get(i);
                c.applyMedianStep();
                collisions.add(new CornerCollision(processingCorners, i));
            }
            for(Collision c : collisions){
                c.ApplyCollision();
            }
            collisions.clear();
            count++;
        }
        if(processingCorners.size() == 2){
            Corner c1 = processingCorners.get(0);
            Corner c2 = processingCorners.get(1);
            skeletonEdges.add(new Edge(c1.link.destination, c2.link.destination));
        }
    }

    public void clearSkeleton() {
        this.skeletonVertices.clear();
        this.skeletonEdges.clear();
    }

    protected void setupProcessingCorners() {
        this.processingCorners.clear();
        Vertex f = this.vertices.get(0);
        this.processingCorners.add(new Corner(f.clone()));
        if (f.edges.size() > 0) {
            Vertex v = f.right();
            while (v != f) {
                this.processingCorners.add(new Corner(v.clone()));
                v = v.right();
            }
        }
        for (int i = 0; i < this.processingCorners.size(); i++) {
            Vertex v = processingCorners.get(i).link.source;
            Vertex right = processingCorners.get((i + 1) % this.processingCorners.size()).link.source;
            Vertex left = processingCorners.get((i > 0) ? i - 1 : this.processingCorners.size() - 1).link.source;
            v.addEdgeTo(right);
            v.addEdgeTo(left);
            Vertex vD = processingCorners.get(i).link.destination;
            Vertex rightD = processingCorners.get((i + 1) % this.processingCorners.size()).link.destination;
            Vertex leftD = processingCorners.get((i > 0) ? i - 1 : this.processingCorners.size() - 1).link.destination;
            vD.addEdgeTo(rightD);
            vD.addEdgeTo(leftD);
            processingCorners.get(i).lastRightDistance = processingCorners.get(i).rightDistance();
        }
    }

    public void addVertex(Vector3 position) {
        this.edges.clear();
        Vertex vertex = new Vertex(position);
        ArrayList<Vertex> newVertices = new ArrayList<>();
        for (Vertex v : this.vertices) {
            newVertices.add(v.clone());
        }
        newVertices.add(vertex);
        for (int i = 0; i < newVertices.size(); i++) {
            Vertex v = newVertices.get(i);
            Vertex right = newVertices.get((i + 1) % newVertices.size());
            Vertex left = newVertices.get((i > 0) ? i - 1 : newVertices.size() - 1);
            v.addEdgeTo(right);
            v.addEdgeTo(left);
            this.edges.add(v.edges.get(0));
            this.edges.add(v.edges.get(1));
        }
        this.vertices.clear();
        this.vertices = newVertices;
        if (!rightHand()) {
            for (Vertex v : this.vertices) {
                v.swapRithtToLeft();
            }
        }
        if (vertices.size() >= 3) {
            applySkeleton();
        }
    }

    public void clear() {
        this.vertices.clear();
        this.edges.clear();
        this.clearSkeleton();
    }
}
