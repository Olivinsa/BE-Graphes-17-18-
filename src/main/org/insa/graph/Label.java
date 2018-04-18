package org.insa.graph;

public class Label implements Comparable<Label>{
	
	public int idNode;
	public double cout;
	public int mark; //0: non marqué, 1: dans le tas, 2: marqué et sorti du tas
	public Arc arcPere;
	
	
	
	public Label(int idNode) {
		this.idNode = idNode;
		this.cout = Double.POSITIVE_INFINITY;
		this.mark = 0;
		this.arcPere = null;
	}
	
	

	
    @Override
    public int compareTo(Label other) {
    	
        return (int)(this.cout - other.cout);
    }

}