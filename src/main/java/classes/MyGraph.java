package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyGraph {
    private static List<Vertex> vertexList = null;
    private final List<Edge> edgeList;
    private final boolean directed;

    public MyGraph(boolean directed) {
        vertexList = new ArrayList<Vertex>();
        edgeList = new ArrayList<Edge>();
        this.directed = directed;
    }

    public boolean isDirected(){
        return this.directed;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public boolean containsVertex(Vertex v){
        return vertexList.contains(v);
    }

    public Vertex addVertex(String name) {
        Vertex v = new Vertex(name);
        vertexList.add(v);
        return v;
    }

    public void addEdge(Vertex origin, Vertex destiny, String value) {
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

            for (Edge e : u.adj) {
                if(isDirected()) {
                    r.append(u.name).append(" -> ");
                }
                else{
                    r.append(u.name).append(" -- ");
                }
                Vertex v = e.destiny;
                r.append(v.name).append(";");
                r.append("\n");
            }

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

    public static Vertex getVertex(String name){
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

    public void saveGraphFile(String graphFileName) throws IOException {
        File arquivo = new File("./src/main/files/graphFiles/"+graphFileName+".dot");

        arquivo.createNewFile();

        FileWriter fw = new FileWriter( arquivo );

        BufferedWriter bw = new BufferedWriter( fw );

        if (isDirected()){
            bw.write("digraph "+graphFileName);
        }
        else{
            bw.write("strict graph "+graphFileName);
        }
        bw.write("{");
        bw.newLine();

        bw.write(getAdjList());

        bw.write("}");
        bw.newLine();

        bw.close();
        fw.close();
    }

    public static MyGraph readGraphFile(String graphPath) throws IOException {
        File arquivo = new File( graphPath);
        MyGraph graph = null;

        if(arquivo.exists()) {
            FileReader fr = new FileReader( arquivo );
            BufferedReader br = new BufferedReader( fr );
            String[] vertexes;
            String v2;
            String value;

            while( br.ready() ) {
                String linha = br.readLine();
                if (linha.contains("digraph")){
                    graph = new MyGraph(true);
                    System.out.println(linha.contains("digraph"));
                }
                else if(linha.contains("graph")){
                    graph = new MyGraph(false);
                }

                if(linha.contains("->") || linha.contains("--")){
                    if(graph.isDirected()){
                        vertexes = linha.split("->");
                    }
                    else{
                        vertexes = linha.split("--");
                    }
                    String v1 = vertexes[0].strip();

                    if(vertexes[1].contains("[")){
                        String[] v2_label = vertexes[1].split("\\[");
                        v2 = v2_label[0].strip();
                        String[] labelStrList = v2_label[1].split("=");
                        value = labelStrList[1].strip();
                        value = value.substring(0, value.length() - 1);
                    }
                    else {
                        v2 = vertexes[1];
                        v2 = v2.substring(0, v2.length() - 1).strip();
                        value = "0";
                    }
                    Vertex vertexV1 = getVertex(v1);
                    if (vertexV1 == null) {
                        vertexV1 = graph.addVertex(v1);
                    }
                    Vertex vertexV2 = getVertex(v2);
                    if (vertexV2 == null) {
                        vertexV2 = graph.addVertex(v2);
                    }

                    graph.addEdge(vertexV1, vertexV2, value);

                }

            }
        }

        return graph;
    }
}