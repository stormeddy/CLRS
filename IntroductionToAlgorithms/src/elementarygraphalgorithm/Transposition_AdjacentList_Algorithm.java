package elementarygraphalgorithm;

import java.util.Iterator;

public class Transposition_AdjacentList_Algorithm {
	private Adjacent_List g;
	
	public Transposition_AdjacentList_Algorithm(Adjacent_List G){
		g=G;
	}
	public Adjacent_List transposeG(){
		Adjacent_List Gt = new Adjacent_List(g.getSize());
		for(int u=0;u<g.getSize();u++){
			Iterator<String> iter = g.getListByVertexIndex(u).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				Gt.addEdge(vstr , g.getVertexValue(u));	//Ìí¼Ó×ªÖÃ±ß
			}
		}
		return Gt;
	}
}
