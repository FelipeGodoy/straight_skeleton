package esqueletoretilineo;

import java.util.ArrayList;

/**
 * @author Felipe
 */
public class CornerCollision implements Collision{
    
    public ArrayList<Corner> corners;
    public int index;
    public Corner c1, c2;
    public float value;
    
    public CornerCollision(ArrayList<Corner> corners, int index) {
        this.corners = corners;
        this.index = index;
        this.c1 = corners.get(index);
        this.c2 = corners.get((index +1) % corners.size());
        this.value = c1.link.destination.distanceToRight();
    }

    @Override
    public float value() {
        return value;
    }

    @Override
    public void ApplyCollision() {
        float v = value;
        float v2 = value;
        if(value <= 3f && corners.contains(c1) && corners.contains(c2)){
            Corner c = new Corner(c1.link.destination.clone());
            c2.swapRightEdge(c);
            c1.swapLeftEdge(c);
            corners.set(corners.indexOf(c1),c);
            corners.remove(c2);
            Polygon.instance.skeletonEdges.add(c1.link);
            Polygon.instance.skeletonEdges.add(c2.link);
            Polygon.instance.skeletonEdges.add(c.link);
            Polygon.instance.skeletonVertices.add(c.link.source);
            Polygon.instance.skeletonVertices.add(c.link.destination);
        }
    }
}
