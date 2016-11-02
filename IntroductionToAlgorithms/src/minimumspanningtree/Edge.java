package minimumspanningtree;

public class Edge implements Comparable<Edge>{  
    public int i,j,w;  
    public Edge(int i,int j,int w){  
        this.i=i;  
        this.j=j;  
        this.w=w;  
    }
    public Edge(int i,int j){  
        this.i=i;  
        this.j=j;  
        this.w=0;  
    }
      
  
    @Override  
    public int compareTo(Edge to) {  
//        Edge to=(Edge)o;  
        if(this.w>to.w) return 1;  
        else if(this.w==to.w) return 0;  
        else return -1;  
          
    }  
    @Override  
    public String toString() {  
        return "start="+i+"||end="+j+"||w="+w;  
    }  
}  