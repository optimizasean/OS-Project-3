import java.util.Vector;

import javax.sound.midi.Receiver;

public class TimeStamp {
	
	/**
	 * Updates the local clock of the given PC by incrementing it by 1. Use 
	 * whenever an event occurs, except for when a message is being sent or
	 * received. Since merge method handles that. 
	 * 
	 * @param ID - the PC that you want to update the local clock for
	 * @param TS - the time stamp of the correlating PC
	 */
	public static void inc(int ID, Vector<Integer> TS) {
		TS.set(ID-1,TS.get(ID-1)+1);
	}
	
	
	/**
	 * Used to merge the time stamp vectors from a sending PC to a receiving PC. The sender's
	 * vector remains unmodified, the receiving PC's vector is updated.   
	 * 
	 * @param receiverID - the PC that is receiving a message
	 * @param senderTS - PC's vector that is sending the message
	 * @param receiverTS - PC's vector that is receiving the message
	 */
	public static void merge(int senderID, Vector<Integer> senderTS, int receiverID, Vector <Integer> receiverTS){
		inc(senderID, senderTS);//(send event)
		for(int i=0; i<receiverTS.size(); i++) {
			if(i == receiverID - 1){
				inc(receiverID, receiverTS);//(receive event)
			}
			else {
				receiverTS.set(i, Math.max(receiverTS.get(i), senderTS.get(i)));
			}
		}
	}
	
	
	/**
	 * Compares two time stamps and determines which occurred first.
	 * TS1>TS2 return 1
	 * TS1<TS2 return 0
	 * If concurrent, or equal return -1
	 * 
	 * @param TS1 - first time stamp vector
	 * @param TS2 - second time stamp vector
	 * @return integer
	 */
	public static int compare(Vector<Integer> TS1, Vector<Integer> TS2) {
		int ret = -1;
		if(TS1.size() != TS2.size()) {
			return ret;
		}
		
		int countTS1 = 0;
		int countTS2 = 0;
		
		for(int i=0;i<TS1.size(); i++) {
			if(TS1.get(i) <= TS2.get(i))countTS1++;
			if(TS2.get(i) <= TS1.get(i))countTS2++;
		}
		if(countTS1==countTS2)ret = -1;
		else if(countTS1 == TS1.size())ret = 0;
		else if(countTS2 == TS2.size()) ret = 1;
		else ret = -1;
		
		return ret;
	}
	
}
