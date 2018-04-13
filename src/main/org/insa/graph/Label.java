package org.insa.graph;

import java.util.ArrayList;

public class Label implements Comparable<Label>{
	
	public int idNode;
	public double cout;
	public int mark; //0: non marqué, 1: dans le tas, 2: marqué et sorti du tas
	public Arc arcPere;
	public static ArrayList<Label> listLabel; 
	
	public Label(int idNode) {
		this.idNode = idNode;
		this.cout = Double.POSITIVE_INFINITY;
		this.mark = 0;
		this.arcPere = null;
		listLabel.add(this);
	}
	
	public static Label getLabel(int id) throws IllegalArgumentException {
		for(Label l: listLabel) {
			if(l.idNode == id) {
				return l;
			}
		}
		throw new IllegalArgumentException("Le noeud n'a pas de label !");
	}
	
    @Override
    public int compareTo(Label other) {
    	
        return (int)(this.cout - other.cout);
    }

}