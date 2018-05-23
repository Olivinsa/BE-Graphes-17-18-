package org.insa.algo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class TestPerf {

	@Test
	public void test() {
		//
		mainTest(Mode.TIME, 100);
		mainTest(Mode.LENGTH, 100);
		
	}
	
	//fonction qui lance les tests sur le mode donné et le nbr de tests
	public void mainTest(Mode mode, int nbrTests) {
		//plus nbrTests est elevé, plus la moyenne des temps sera précise
	
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File("TestPerf_"+mode.name()+"_"+nbrTests+".txt")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// normalement si le fichier n'existe pas, il est crée à la racine du projet
		
		try {
			writer.write("Graphe\tTaille\tTempsB\tTempsD\tTempsA\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			writer.write(testGraphe("insa", mode, nbrTests));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.write(testGraphe("bordeaux", mode, nbrTests));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.write(testGraphe("guadeloupe", mode, nbrTests));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.write(testGraphe("toulouse", mode, nbrTests));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.write(testGraphe("paris", mode, nbrTests));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//fonction qui lance plusieurs fois les 3 algos sur un graphe
	//renvoie la moyenne du tps de clacul pour les 3 algos
	public String testGraphe(String graphName, Mode mode, int nbrTests) {
		//All roads allowed
		
		//recuperation du graphe
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/"+graphName+".mapgr";
    	GraphReader reader = null;
    	Graph graph = null;
		try {
			reader = new BinaryGraphReader(
				  new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			graph = reader.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//moyenne de temps de calcul des algos	
		int moyenneB = 0;
		int moyenneD = 0;
		int moyenneA = 0;
		//on lance sur pleins de paires de noeuds random
		for(int i =0; i<nbrTests; i++) {
			//affichage de l'avancement
			System.out.println(graph.getMapName()+" "+i);
			//choix aleatoire de 2 noeuds
			Random random = new Random();
			int randomDepart = random.nextInt(graph.size());
			int randomArrivee = random.nextInt(graph.size());
			
			//creation des datas correspondantes
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
	                return mode;
	            }

	            @Override
	            public String toString() {
	                return "All roads allowed";
	            }
	        };
	        ShortestPathData data = new ShortestPathData(graph, graph.get(randomDepart),  graph.get(randomArrivee), AS );
	        
	        //on mesure le temps que prend Bellman pour faire le chemin entre ces 2 noeuds
	        Chrono chronoB = new Chrono();
	        chronoB.start(); // démarrage du chrono
	        BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
	    	B.run();
	        chronoB.stop(); // arrêt
	        moyenneB+=chronoB.getDureeMicro(); // ajout du résultat en millisecondes
	        
	        //on mesure le temps que prend Dijkstra pour faire le chemin entre ces 2 noeuds
	        Chrono chronoD = new Chrono();
	        chronoD.start(); // démarrage du chrono
	        DijkstraAlgorithm D = new DijkstraAlgorithm(data);
	    	D.run();
	        chronoD.stop(); // arrêt
	        moyenneD+=chronoD.getDureeMicro(); // ajout du résultat en millisecondes
	        
	        //on mesure le temps que prend AStar pour faire le chemin entre ces 2 noeuds
	        Chrono chronoA = new Chrono();
	        chronoA.start(); // démarrage du chrono
	        AStarAlgorithm A = new AStarAlgorithm(data);
	    	A.run();
	        chronoA.stop(); // arrêt
	        moyenneA+=chronoA.getDureeMicro(); // ajout du résultat en millisecondes
			
		}
		//affichage de l'avancement
	    System.out.println(graph.getMapName()+" terminated");
	    //on ajoute la ligne dans le fichier
	    return (graph.getMapName()+"\t"+graph.size()+"\t"+moyenneB/nbrTests+"\t"+moyenneD/nbrTests+"\t"+moyenneA/nbrTests+"\n");
			 
	}

}
