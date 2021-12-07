package tests;

import classes.Graph;
import classes.Vertex;

import java.util.List;
import java.util.stream.IntStream;

public class Main {
    static Graph createSimpleGraph() {
        Graph graph = new Graph(false);
        Vertex v1 = graph.addVertex("v1");
        Vertex v2 = graph.addVertex("v2");
        Vertex v3 = graph.addVertex("v3");
        Vertex v4 = graph.addVertex("v4");
        Vertex v5 = graph.addVertex("v5");
        Vertex v6 = graph.addVertex("v6");
        graph.addEdge(v1, v3, 0);
        graph.addEdge(v1, v2, 0);
        graph.addEdge(v1, v4, 0);
        graph.addEdge(v3, v2, 0);
        graph.addEdge(v4, v2, 0);
        graph.addEdge(v2, v6, 0);
        graph.addEdge(v5, v4, 0);
        graph.addEdge(v6, v5, 0);
        return graph;
    }

    static Graph createMultiGraph() {
        Graph graph = new Graph(true);
        Vertex v1 = graph.addVertex("v1");
        Vertex v2 = graph.addVertex("v2");
        Vertex v3 = graph.addVertex("v3");
        Vertex v4 = graph.addVertex("v4");
        Vertex v5 = graph.addVertex("v5");
        Vertex v6 = graph.addVertex("v6");
        graph.addEdge(v1, v3, 0);
        graph.addEdge(v1, v2, 0);
        graph.addEdge(v1, v2, 0);
        graph.addEdge(v1, v4, 0);
        graph.addEdge(v1, v1, 0);
        graph.addEdge(v3, v2, 0);
        graph.addEdge(v2, v2, 0);
        graph.addEdge(v4, v2, 0);
        graph.addEdge(v2, v6, 0);
        graph.addEdge(v5, v4, 0);
        graph.addEdge(v6, v5, 0);
        graph.addEdge(v6, v5, 0);
        return graph;
    }

    public static void main(String[] args) {
        Graph graph = createMultiGraph();

        System.out.println("Lista de Adjacências");
        System.out.println(graph.getAdjList());

        System.out.println("Lista de Adjacências do vértice V1");
        Vertex v1 = graph.getVertex("v1");
        List<Vertex> adjListVertexV1 = graph.getAdjListVertex(v1);
        System.out.println(adjListVertexV1);

        System.out.println("\nMatriz de Adjacências");
        int[][] adjMatrix = graph.getAdjMatrix();
        for (int[] matrix : adjMatrix) {
            IntStream.range(0, adjMatrix.length).forEach(j -> System.out.printf("%4d", matrix[j]));
            System.out.println();
        }

        int[][] incidenceMatrix = graph.getIncidenceMatrix();
        System.out.println("\nMatriz de incidência");
        if (incidenceMatrix == null){
            System.out.println("Grafo não direcionado");
        }
        else {
            for (int[] matrix : incidenceMatrix) {
                IntStream.range(0, incidenceMatrix[0].length).forEach(j -> System.out.printf("%4d", matrix[j]));
                System.out.println();
            }
        }

        int graphOrder = graph.getGraphOrder();
        System.out.println("\nOrdem do grafo");
        System.out.println(graphOrder);

        System.out.println("\nGrau do vértice");
        System.out.println(graph.getVertexDegree(v1));

        System.out.println("\nGrafo é simples?");
        System.out.println(graph.isSimple());

        Vertex v6 = graph.getVertex("v6");

        System.out.println("\nO caminho existe?");
        System.out.println(graph.hasPath(v1, v6));

        System.out.println("\nGrafo é direcionado?");
        System.out.println(graph.isDirected());
    }



}
