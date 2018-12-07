//Operating Systems Project (osp) package
package osp;

import java.io.Serializable;
import java.util.Vector;

public class VectorClock implements Serializable {
	private static final long serialVersionUID = 1L;
	public int ID;
	public Vector<Integer> TS = new Vector<Integer>();
	public Vector<Integer> writeTS = new Vector<Integer>();

	public VectorClock(int ID){
		this.ID = ID;
		for(int i=0; i<5; i++) this.TS.add(0);
		for(int i=0; i<5; i++) this.writeTS.add(0);
		
	}
	
	
	public void inc() {
		this.TS.set(this.ID-1,this.TS.get(this.ID-1)+1);
		return;
	}
	
	
	public void merge(VectorClock sender){
		for(int i=0; i<this.TS.size(); i++) {
			if(i == this.ID - 1){
				//sender.inc();
			}
			else {
				this.TS.set(i, Math.max(this.TS.get(i), sender.TS.get(i)));
			}
		}
		return;
	}
	
	
	public void setWriteTime(VectorClock clock) {
		for(int i=0; i<5; i++) {
			this.writeTS.set(i, clock.TS.get(i));
		}
	}
	
	
	public static String compare(VectorClock first, VectorClock second) {
		String ret = "first<->second";
		
		int countFirst = 0;
		int countSecond = 0;
		
		for(int i=0;i<5; i++) {
			if(first.writeTS.get(i) <= second.writeTS.get(i))countFirst++;
			if(second.writeTS.get(i) <= first.writeTS.get(i))countSecond++;
		}
		
		if(countFirst == countSecond) ret = "first=second";
		else if(countFirst == 5)ret = "first->second";
		else if(countSecond == 5) ret = "second->first";
		
		return ret;
	}
	
	
	public String print(){
		//System.out.println("PC"+this.ID+this.TS+"\t");
		return "PC"+this.ID+this.TS;
	}

	public String toString(){
		if (this == null) return null;
		if (this.TS == null) return "[]";
		return "PC"+this.ID+this.TS;
	}
	
	
}
