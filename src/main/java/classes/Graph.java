package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graph {
    private final List<Vertex> vertexList;
    private final List<Edge> edgeList;
    private final boolean directed;

    public Graph(boolean directed) {
        vertexList = new ArrayList<Vertex>();
        edgeList = new ArrayList<Edge>();
        this.directed = directed;
    }

    public boolean isDirected(){
        return this.directed;
    }

    public Vertex addVertex(String name) {
        Vertex v = new Vertex(name);
        vertexList.add(v);
        return v;
    }

    public void addEdge(Vertex origin, Vertex destiny, float value) {
        Edge e = new Edge(origin, destiny, value);
        origin.addAdj(e);
        edgeList.add(e);
        if (!isDirected()){
            Edge e_inverted = new Edge(destiny, origin, value);
            destiny.addAdj(e_inverted);
            edgeList.add(e_inverted);
        }
    }

    public String getAdjList() {
        StringBuilder r = new StringBuilder();
        for (Vertex u : vertexList) {
            r.append(u.name).append(" -> ");
            for (Edge e : u.adj) {
                Vertex v = e.destiny;
                r.append(v.name).append(", ");
            }
            r.append("\n");
        }
        return r.toString();
    }

    public List<Vertex> getAdjListVertex(Vertex v){
        List<Vertex> listAdj = new ArrayList();
        for (Edge e: v.adj) {
            listAdj.add(e.destiny);
        }
        return listAdj;
    }

    public Vertex getVertex(String name){
        for (Vertex v: vertexList) {
            if (Objects.equals(v.name, name)){
                return v;
            }
        }
        return null;
    }

    public int[][] getAdjMatrix(){
        int n_vertex = vertexList.size();
        int[][] adjMatrix = new int[n_vertex][n_vertex];
        for (int i = 0; i < n_vertex; i++) {
            Vertex v = vertexList.get(i);
            List<Vertex> vList = getAdjListVertex(v);
            for (int j = 0; j < n_vertex; j++) {
                Vertex v2 = vertexList.get(j);
                if (vList.contains(v2)){
                    adjMatrix[i][j] = 1;
                }
                else {
                    adjMatrix[i][j] = 0;
                }
            }
        }
        return adjMatrix;
    }

    public int[][] getIncidenceMatrix(){
        if (isDirected()) {
            int n_vertex = vertexList.size();
            int n_edges = edgeList.size();
            int[][] incidenceMatrix = new int[n_vertex][n_edges];
            for (int i = 0; i < n_edges; i++) {
                Edge e = edgeList.get(i);
                for (int j = 0; j < n_vertex; j++) {
                    incidenceMatrix[j][i] = 0;
                    Vertex v = vertexList.get(j);
                    if (e.destiny.equals(v)) {
                        incidenceMatrix[j][i] = -1;
                    } else if (e.origin.equals(v)) {
                        incidenceMatrix[j][i] = 1;
                    }
                }
            }
            return incidenceMatrix;
        }
        return null;
    }

    public int getGraphOrder(){
        return vertexList.size();
    }

    public int getVertexDegree(Vertex vertex){
        return getAdjListVertex(vertex).size();
    }

    protected boolean isSelfPointing(Vertex vertex){
        List<Vertex> adjList = getAdjListVertex(vertex);
        return  adjList.contains(vertex);
    }

    public boolean isSimple(){
        if (!isDirected()){
            for (Vertex v: vertexList) {
                if (isSelfPointing(v)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean recHasPath(List<Vertex> adjList, Vertex destiny, List<Vertex> visitedVertexes){

        if(!adjList.contains(destiny)){
            for (Vertex v: adjList) {
                if(!visitedVertexes.contains(v)){
                    visitedVertexes.add(v);
                    List<Vertex> vAdjList = getAdjListVertex(v);
                    boolean hasPath = recHasPath(vAdjList, destiny, visitedVertexes);
                    if(hasPath){
                        return true;
                    }
                }
            }
        }
        else{
            return true;
        }
        return false;
    }

    public boolean hasPath(Vertex origin, Vertex destiny){
        boolean hasPath = false;
        List<Vertex> visitedVertexes = new ArrayList<>();
        visitedVertexes.add(origin);
        List<Vertex> adjList = getAdjListVertex(origin);
        if(vertexList.contains(origin) && vertexList.contains(destiny)){
            hasPath = recHasPath(adjList, destiny, visitedVertexes);
        }
        return hasPath;
    }
}