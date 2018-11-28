//Operating Systems Project (osp) package
package osp;

import java.util.Vector;

public class VectorClock {

	private int ID;
	private Vector<Integer> TS;

	public VectorClock(int ID){
		this.ID = ID;
		Vector<Integer> temp = new Vector<Integer>();
		for(int i=0;i<5;i++)temp.add(0);
		this.TS = temp;
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
	
	
	public static String compare(VectorClock first, VectorClock second) {
		String ret = "Error";
		
		int countFirst = 0;
		int countSecond = 0;
		
		for(int i=0;i<first.TS.size(); i++) {
			if(first.TS.get(i) <= second.TS.get(i))countFirst++;
			if(second.TS.get(i) <= first.TS.get(i))countSecond++;
		}
		
		if(countFirst == countSecond) ret = "PC"+first.ID+"<->PC"+second.ID;
		else if(countFirst == first.TS.size())ret = "PC"+first.ID+"->PC"+second.ID;
		else if(countSecond == second.TS.size()) ret = "PC"+second.ID+"<->PC"+first.ID;
		
		return ret;
	}
	
	
	public void print(){
		System.out.print("PC"+this.ID+this.TS+"\t");
	}
}
