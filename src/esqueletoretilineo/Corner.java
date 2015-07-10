/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esqueletoretilineo;

import java.util.ArrayList;

/**
 * @author Felipe
 */
public class Corner {
   public Edge link;
   public float speed;
   
   public float lastRightDistance;
   public ArrayList<Float> lastAngles;
   
   public Corner(Edge link){
       this.link = link;
       this.speed = 0.1f;
       this.lastAngles = new ArrayList<>();
   }
   
   public Corner(Vertex v){
       this(new Edge(v, v.clone()));
   }
   
   public Corner(Vector3 v){
       this(new Vertex(v));
   }
   
   public float rightDistance(){
       return Vector3.distance(link.destination.position, link.destination.right().position);
   }
   
   public void swapRightEdge(Corner c){
       c.link.source.addEdgeTo(this.link.source.right());
       c.link.destination.addEdgeTo(this.link.destination.right());
       this.link.source.right().edges.get(1).destination = c.link.source;
       this.link.destination.right().edges.get(1).destination = c.link.destination;
   }
   
   public void swapLeftEdge(Corner c){
       c.link.source.addEdgeTo(this.link.source.left());
       c.link.destination.addEdgeTo(this.link.destination.left());
       this.link.source.left().edges.get(0).destination = c.link.source;
       this.link.destination.left().edges.get(0).destination = c.link.destination;
   }
   
   public boolean isConvex(){
       return link.source.angle() > 1f;
   }
   
   public void applyMedianStep(){
       float speed = this.speed / (float)Math.sin(link.destination.angle() * 0.5 * Math.PI);
       if(isConvex()){
           speed = - speed;
       }
       link.destination.position = link.destination.median(speed).destination.position;
   }
    
}
