package org.insa.algo.shortestpath;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	ShortestPathData data = getInputData();
    	ShortestPathSolution solution = null; //initialisation de la solution
    	Graph graph = data.getGraph();
    	// Notify observers about the first event (origin processed)
    	notifyOriginProcessed(data.getOrigin());
    	for (Node node : graph) {//initialisation du label pour chaque noeud
    		new Label(node.getId());
    	}
    	
    	Node origin = data.getOrigin();
    	Label labelOrigin = Label.getLabel(origin.getId()); //label du noeud de départ
    	labelOrigin.cout = 0;
		
		//Insertion dans le tas
		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		tas.insert(labelOrigin); //au debut, le tas ne contient que le label du noeud de départ
		labelOrigin.mark = 1; //1 -> noeud dans le tas
		
		//Itérations
		boolean nonFin = true;
		while(!tas.isEmpty() && nonFin) {
			Label labelX = tas.deleteMin();//recuperation du label
			labelX.mark = 2; //2 -> label marqué et sorti du tas
			Node x = graph.get(labelX.idNode); //noeud associé à ce label
			Iterator<Arc> successeurs = x.iterator(); //liste des arcs sortants de x
			
			//si le noeud est la destination, le chemin est trouvé, donc on sort
			if(x == data.getDestination()) {
				nonFin = false;
			}
			
			while(successeurs.hasNext()) { //pour chacun des arcs sortants
				Arc a = successeurs.next();
				Node voisin = a.getDestination();
				Label labelVoisin = Label.getLabel(voisin.getId()); //on prend le label du successeur
				
				//si le noeud voisin n'a pas été marqué
				if(labelVoisin.mark != 2) {
					double newCout = labelX.cout + data.getCost(a); //calcul du cout menant à ce noeud et passant par cet arc
					
					if(labelVoisin.cout > newCout){ //si le cout est plus petit, on le remplace
						labelVoisin.cout = newCout;
						labelVoisin.arcPere = a;//et on remplace le pere
						
						if(labelVoisin.mark == 0) {//si le label du voisin n'est pas déjà dans le tas
							tas.insert(labelVoisin);//on l'y met
							labelVoisin.mark = 1; //1 -> dans le tas
						}
					}

				}
			}
			
		}
		
		
		//Reconstitution du chemin en cherchant les pere dans le sens inverse
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Node currentNode = data.getDestination();
		Arc arcPere = Label.getLabel(currentNode.getId()).arcPere;
		try {
			
			while(arcPere.getDestination().compareTo(origin) != 0) { //tant qu'on a pas atteint l'origine
				arcs.add(arcPere);
				currentNode = arcPere.getOrigin();// predecesseur du noeud
				arcPere = Label.getLabel(currentNode.getId()).arcPere;
			}
			
		}catch (NullPointerException e) {
			// si l'un des peres est null, alors il n'y a pas de chemin
			throw new NullPointerException("Pas de chemin trouvé !");
		}

		// On remet le chemin dans le bon sens
		Collections.reverse(arcs);

		// Create the final solution.
		solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
		return solution;
	}
}
