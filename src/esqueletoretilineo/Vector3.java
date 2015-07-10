package esqueletoretilineo;

/**
 * @author Felipe
 */
public class Vector3 {
    public float x,y,z;
    
    public Vector3(){
        this.x = this.y = this.z = 0f;
    }

    public Vector3(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0f;
    }
    
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void normalize(){
        float magnitude = this.magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
    }
    
    public Vector3 normalized(){
        Vector3 clone = this.clone();
        clone.normalize();
        return clone;
    }
    
    public float magnitude(){
        return (float)Math.sqrt(x*x + y*y + z*z);
    }
    
    public static float distance(Vector3 a, Vector3 b){
        return (new Vector3(a.x - b.x, a.y - b.y, a.z - b.z)).magnitude();
    }
    
    public static float dot(Vector3 a, Vector3 b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    
    public static Vector3 cross(Vector3 a, Vector3 b){
        return new Vector3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }
    
    public static boolean isRightHand(Vector3 a, Vector3 b){
        return (a.x * b.y - a.y * b.x) >= 0f;
    }
    
    @Override
    public Vector3 clone(){
        return new Vector3(this.x, this.y, this.z);
    }
    
    public boolean equals(Vector3 other){
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }
    
    public static Vector3 sum(Vector3 a, Vector3 b){
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    public static Vector3 subtration(Vector3 a, Vector3 b){
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    
    public static Vector3 mult(Vector3 a, Vector3 b){
        return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z);
    }
    
    public static Vector3 div(Vector3 a, Vector3 b){
        return new Vector3(a.x / b.x, a.y / b.y, a.z / b.z);
    }
    
    @Override
    public String toString(){
        return "(x:"+x+", y: "+y+", z: "+z+")";
    }
    
}
