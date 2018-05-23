package org.insa.algo.shortestpath;


import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.LabelProbleme;
import org.insa.graph.Node;

public class Probleme{
	
	private Node depart1;
	private Node depart2;
	private Node arrivee1;
	private Node arrivee2;
	private Graph graph;
	
	public LabelProbleme[] tabLabelProbleme;
	
	
	public Probleme(Graph graph, Node depart1, Node depart2, Node arrivee1, Node arrivee2) {
		this.graph = graph;
		this.depart1 = depart1;
		this.depart2 = depart2;
		this.arrivee1 = arrivee1;
		this.arrivee2 = arrivee2;
		initializeLabel();
	}
	
	public Node findRDV() {
		ArcInspector AS = new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
                return true;
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getLength();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }

            @Override
            public String toString() {
                return "Shortest path, all roads allowed";
            }
        };
		ShortestPathData data1 = new ShortestPathData(graph, this.depart1, this.depart1, AS);
		ShortestPathData data2 = new ShortestPathData(graph, this.depart2, this.depart2, AS);
    	DijkstraVersTous D1 = new DijkstraVersTous(data1, tabLabelProbleme, "A");
    	DijkstraVersTous D2 = new DijkstraVersTous(data2, tabLabelProbleme, "B");

    	D1.doRun();
    	D2.doRun();
    	
    	double coutTotalMin = Double.POSITIVE_INFINITY;
    	Node RDV = null;
    	
		System.out.println(this.graph.size());
    	for(int i = 0; i < this.graph.size(); i++) {
    		Node n = graph.get(i);
    		LabelProbleme l = tabLabelProbleme[i];
    		if (l.coutDepuisDepart1 != -1 && l.coutDepuisDepart2 != -1) {
    			if ((l.coutDepuisDepart1 + l.coutDepuisDepart2) < coutTotalMin) {
    				System.out.println(i);
		    		ShortestPathData dataA = new ShortestPathData(graph, n, this.arrivee1, AS);
		    		ShortestPathData dataB = new ShortestPathData(graph, n, this.arrivee2, AS);
		    		DijkstraAlgorithm DA = new DijkstraAlgorithm(dataA);
		    		DijkstraAlgorithm DB = new DijkstraAlgorithm(dataB);
		    		
		    		ShortestPathSolution s_DA = DA.run();
		    		ShortestPathSolution s_DB = DB.run();
		    		
		    		try {
			    		l.coutVersArrivee1 = s_DA.getPath().getMinimumTravelTime();
			    		l.coutVersArrivee2 = s_DB.getPath().getMinimumTravelTime();
			    		
			    		double coutTotal = l.coutDepuisDepart1 + l.coutDepuisDepart2 + l.coutVersArrivee1 + l.coutVersArrivee2;
			    		if (coutTotal < coutTotalMin) {
			    			coutTotalMin = coutTotal;
			    			RDV = n;
			    			System.out.println("remplacé");
			    		}
		    		} catch(Exception e) {
		    			System.out.println("pas de chemin");
		    		}
		    		
    			}
	    	}
    	}
		System.out.print("RDV= " +RDV.getId());
		return RDV;
	}

	public void run() {
		Node RDV = findRDV();
		ArcInspector AS = new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
                return true;
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getLength();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }

            @Override
            public String toString() {
                return "Shortest path, all roads allowed";
            }
        };
        ShortestPathData data1 = new ShortestPathData(graph, this.depart1, RDV, AS);
        ShortestPathData data2 = new ShortestPathData(graph, this.depart2, RDV, AS);
        ShortestPathData data3 = new ShortestPathData(graph, RDV, this.arrivee1, AS);
        ShortestPathData data4 = new ShortestPathData(graph, RDV, this.arrivee2, AS);
        
		DijkstraAlgorithm D1 = new DijkstraAlgorithm(data1);
		DijkstraAlgorithm D2 = new DijkstraAlgorithm(data2);
		DijkstraAlgorithm D3 = new DijkstraAlgorithm(data3);
		DijkstraAlgorithm D4 = new DijkstraAlgorithm(data4);
		
		ShortestPathSolution s_D1 = D1.run();
		ShortestPathSolution s_D2 = D2.run();
		ShortestPathSolution s_D3 = D3.run();
		ShortestPathSolution s_D4 = D4.run();
		
		
	}
	
	
	private void initializeLabel() {
		this.tabLabelProbleme = new LabelProbleme[this.graph.size()]; //On cree un tableau de label de taille n; cela permet aussi de reset le tableau de Label si on lance l'algo plusieurs fois de suite 
		for (Node node : this.graph) { //initialisation du label pour chaque noeud
			LabelProbleme l = new LabelProbleme(node.getId());
			this.tabLabelProbleme[node.getId()]= l;
		}
	}


}
