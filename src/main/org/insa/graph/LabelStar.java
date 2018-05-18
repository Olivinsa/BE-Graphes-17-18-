package org.insa.graph;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.ShortestPathData;

public class LabelStar extends Label implements Comparable<Label>{
	
	private ShortestPathData data;
	
	private double speed;
	
	public LabelStar(int idNode, ShortestPathData data) {
		super(idNode);
		this.data = data;
		if (data.getMaximumSpeed() == -1) {
			speed = data.getGraph().getGraphInformation().getMaximumSpeed()/3.6;
		}
		else {
			speed = Math.min(data.getMaximumSpeed()/3.6, data.getGraph().getGraphInformation().getMaximumSpeed()/3.6);
		}
	}
	

	@Override
    public int compareTo(Label other) {
		Node n1 = data.getGraph().get(this.idNode);
		Node n2 = data.getGraph().get(other.idNode);
		Node arrive = data.getDestination();
		double distance_arrive1 = Point.distance(n1.getPoint(), arrive.getPoint());
		double distance_arrive2 = Point.distance(n2.getPoint(), arrive.getPoint());
		System.out.println(this.cout);
		if (data.getMode() == Mode.TIME) {

			distance_arrive1 = distance_arrive1/(double)speed;
			distance_arrive2 = distance_arrive2/(double)speed;
		}
        return (int)((this.cout+distance_arrive1) - (other.cout+distance_arrive2));
    }
}
