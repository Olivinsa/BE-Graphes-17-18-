package org.insa.algo.utils;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;


import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest2 {

    private static Graph graph;


    @BeforeClass
    public static void initAll() throws IOException {
    	String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
    	GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        graph = reader.read();
    }
    
	@Test
	public void test1()  throws IOException {
		//test d'un chemin où origine = destination
		initAll();
		
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

        ShortestPathData data = new ShortestPathData(graph, graph.get(1178),  graph.get(1178), AS );
    	
    	BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
    	ShortestPathSolution s_B = B.run();

    	DijkstraAlgorithm D = new DijkstraAlgorithm(data);
    	ShortestPathSolution s_D = D.run();
    	
		assertEquals(s_B.toString(),s_D.toString());
	}
	
	@Test
	public void test2()  throws IOException {
		//test d'un chemin infaisable
		initAll();
		
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

        ShortestPathData data = new ShortestPathData(graph, graph.get(1170),  graph.get(298), AS );
    	
    	BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
    	ShortestPathSolution s_B = B.run();

    	DijkstraAlgorithm D = new DijkstraAlgorithm(data);
    	ShortestPathSolution s_D = D.run();
    	
		assertEquals(s_B.toString(),s_D.toString());
	}
	

	@Test
	public void test3()  throws IOException {
		//test mode : shortest path all road allowed
		initAll();
		
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

        ShortestPathData data = new ShortestPathData(graph, graph.get(344),  graph.get(535), AS );
    	
    	BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
    	ShortestPathSolution s_B = B.run();

    	DijkstraAlgorithm D = new DijkstraAlgorithm(data);
    	ShortestPathSolution s_D = D.run();
    	
		assertTrue(s_B.getPath().getLength() == s_D.getPath().getLength());
	}
	
	@Test
	public void test4()  throws IOException {
		//test mode : fastest path all road allowed
		initAll();
		
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
                return Mode.TIME;
            }

            @Override
            public String toString() {
                return "Fastest path, all roads allowed";
            }
        };

        ShortestPathData data = new ShortestPathData(graph, graph.get(344),  graph.get(535), AS );
    	
    	BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
    	ShortestPathSolution s_B = B.run();

    	DijkstraAlgorithm D = new DijkstraAlgorithm(data);
    	ShortestPathSolution s_D = D.run();
    	
		assertTrue(s_B.getPath().getMinimumTravelTime() == s_D.getPath().getMinimumTravelTime());
	}
	
	@Test
	public void test5()  throws IOException {
		//test mode : shortest path only road open for cars
		initAll();
		
		ArcInspector AS = new ArcInspector() {

            @Override
            public boolean isAllowed(Arc arc) {
            	if(arc.getRoadInformation().getAccessRestrictions().isAllowedFor(AccessMode.FOOT,arc.getRoadInformation().getAccessRestrictions().getRestrictionFor(AccessMode.MOTORCAR))) {
            		return true;
            	}
            	return false;
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

        ShortestPathData data = new ShortestPathData(graph, graph.get(344),  graph.get(535), AS );
    	
    	BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
    	ShortestPathSolution s_B = B.run();

    	DijkstraAlgorithm D = new DijkstraAlgorithm(data);
    	ShortestPathSolution s_D = D.run();
    	System.out.println(s_B +"---"+ s_D);
		assertTrue(s_B.getPath().getMinimumTravelTime() == s_D.getPath().getMinimumTravelTime());
	}
	
}
