package CLI;

import classes.Graph;
import classes.Vertex;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);

        while (true) {

            System.out.println("MENU");
            System.out.println("## 1) Ler grafo manual");
            System.out.println("## 2) Mostrar dados de grafo");
            System.out.println("## 3) Usar grafo de exemplo");
            System.out.println("## 4) sair");
            int op = read.nextInt();

            if (op == 1) {
                System.out.println("\nO Grafo é direcionado?");
                boolean directed = read.nextBoolean();
                Graph graph = new Graph(directed);
                System.out.println("Número de vértices:");
                int nVertexes = read.nextInt();
                for (int i = 0; i < nVertexes; i++) {
                    System.out.println("Nome da vértice:");
                    String vertexName = read.next();
                    graph.addVertex(vertexName);
                }
                System.out.println("Número de arestas:");
                int nEdges = read.nextInt();
                for (int i = 0; i < nEdges; i++) {
                    System.out.println("Nome da primeira vértice");
                    String v1 = read.next();
                    System.out.println("Nome da segunda vértice");
                    String v2 = read.next();
                    System.out.println("Valor da aresta");
                    int value = read.nextInt();
                    Vertex vertex1 = graph.getVertex(v1);
                    Vertex vertex2 = graph.getVertex(v2);
                    graph.addEdge(vertex1, vertex2, value);
                }
            } else if (op == 2) {
                System.out.println("opção 2");
            } else if(op == 3){
                System.out.println("opção 3");
            }
            else if (op == 4) {
                break;
            }
        }

    }
    
}
