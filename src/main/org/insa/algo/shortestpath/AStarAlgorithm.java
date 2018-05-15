package org.insa.algo.shortestpath;

import org.insa.graph.Graph;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	private ShortestPathData data;
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        this.data = data;
    }
    
    protected void initializeLabel(Graph graph) {
        tabLabel = new LabelStar[graph.size()]; //On cree un tableau de label de taille n; cela permet aussi de reset le tableau de Label si on lance l'algo plusieurs fois de suite 
		for (Node node : graph) { //initialisation du label pour chaque noeud
			LabelStar l = new LabelStar(node.getId(), this.data);
			tabLabel[node.getId()]= l;
		}
    }

}
