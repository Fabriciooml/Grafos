package tests;

import classes.Graph;
import classes.Vertex;

import java.util.List;
import java.util.stream.IntStream;

public class Main {
    static Graph createGraph() {
        Graph graph = new Graph();
        Vertex v1 = graph.addVertex("v1");
        Vertex v2 = graph.addVertex("v2");
        Vertex v3 = graph.addVertex("v3");
        Vertex v4 = graph.addVertex("v4");
        Vertex v5 = graph.addVertex("v5");
        Vertex v6 = graph.addVertex("v6");
        graph.addEdge(v1, v3);
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v3, v2);
        graph.addEdge(v4, v2);
        graph.addEdge(v2, v6);
        graph.addEdge(v5, v4);
        graph.addEdge(v6, v5);
        return graph;
    }

    public static void main(String[] args) {
        Graph graph = createGraph();

        System.out.println("Lista de Adjacências");
        System.out.println(graph.getAdjList());

        System.out.println("Lista de Adjacências do vértice V1");
        Vertex v1 = graph.getVertex("v1");
        List<Vertex> adjListVertexV1 = graph.getAdjListVertex(v1);
        System.out.println(adjListVertexV1);

        System.out.println("Matriz de Adjacências");
        int[][] adjMatrix = graph.getAdjMatrix();
        for (int[] matrix : adjMatrix) {
            IntStream.range(0, adjMatrix.length).forEach(j -> System.out.printf("%4d", matrix[j]));
            System.out.println();
        }

        int[][] incidenceMatrix = graph.getIncidenceMatrix();
        System.out.println("Matriz de incidência");
        for (int[] matrix : incidenceMatrix) {
            IntStream.range(0, incidenceMatrix[0].length).forEach(j -> System.out.printf("%4d", matrix[j]));
            System.out.println();
        }
    }



}
