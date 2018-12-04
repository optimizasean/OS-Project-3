//Operating Systems Project (osp) package
package osp;



/*****************************************************************\
 * Ratio class generates the randoms for our reading and writing
 * simulator.
 * 
 * @author wtberry
\*****************************************************************/
public class Ratio{
    /*****************************************************************\
     * Ratio {@link #request(double, double) request} method takes two
     * doubles and calculates a probability based on this for reading
     * and writing percetage. It returns the result as read, write, or
     * idle.
     * 
     * @param w_p: probability of writing, double.
     * @param r_p: probability of reading. double.
     * @param idle: probability of reading, double.
     * @return String: read, write, idle
    \*****************************************************************/
    public String request(double r_p, double w_p) {
        double r = Math.random();
        if((0<=r) && (r<r_p)) {
            //System.out.println("read: " + r_p);
            //System.out.println("random: " + r);
            return "read";
        } else if((r_p <= r) && (r<r_p + w_p)) {
            //System.out.println("Write: " + w_p);
            //System.out.println("Random: " + r);
            return "write";
        } else return "idle";
    }
}
