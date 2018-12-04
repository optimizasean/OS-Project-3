/**
 * 
 */
package osp;


/**
 * @author Wataru Takahashi
 *
 */
public class Ratio{
  
  // This class takes in ratio of reading and writing probabilities, a
  // output integer with assigned reading/writing,
  
  /*****************
   * return integer assigned to reading / writing.
   * 
   * @param w_p: probability of writing, double.
   * @param r_p: probability of reading. double.
   * @param idle: probability of reading, double.
   * @return string: read, write, idle
   * "read"
   * "write"
   * "idle"
   */
  
  public String request(double r_p, double w_p) {
    double r = Math.random();
    if((0<=r) && (r<r_p)) {
      //System.out.println("read: " + r_p);
      //System.out.println("random: " + r);
      return "read";
    }
    else if((r_p <= r) && (r<r_p + w_p)) {
      //System.out.println("Write: " + w_p);
      //System.out.println("Random: " + r);
      return "write";
    }
    else return "idle";
  }
}

