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
        this.vertexList = new ArrayList<Vertex>();
        this.edgeList = new ArrayList<Edge>();
        this.directed = directed;
    }

    public boolean isDirected(){
        return this.directed;
    }

    public List<Vertex> getVertexList() {
        return new ArrayList<Vertex>(this.vertexList);
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public boolean containsVertex(Vertex v){
        return vertexList.contains(v);
    }

    public Vertex addVertex(String name) {
        Vertex v = new Vertex(name);
        this.vertexList.add(v);
        return v;
    }

    public void addEdge(Vertex origin, Vertex destiny, float value) {
        Edge e = new Edge(origin, destiny, value);
        origin.addAdj(e);
        this.edgeList.add(e);
        if (!isDirected()){
            Edge e_inverted = new Edge(destiny, origin, value);
            destiny.addAdj(e_inverted);
            this.edgeList.add(e_inverted);
        }
    }

    public String getAdjList() {
        StringBuilder r = new StringBuilder();
        for (Vertex u : this.getVertexList()) {
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
        ArrayList<Vertex> encapVertexList = (ArrayList<Vertex>) getVertexList();
        int n_vertex = encapVertexList.size();
        int[][] adjMatrix = new int[n_vertex][n_vertex];
        for (int i = 0; i < n_vertex; i++) {
            Vertex v = encapVertexList.get(i);
            List<Vertex> vList = getAdjListVertex(v);
            for (int j = 0; j < n_vertex; j++) {
                Vertex v2 = encapVertexList.get(j);
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
        ArrayList<Vertex> encapVertexList = (ArrayList<Vertex>) getVertexList();
        if (isDirected()) {
            int n_vertex = encapVertexList.size();
            int n_edges = edgeList.size();
            int[][] incidenceMatrix = new int[n_vertex][n_edges];
            for (int i = 0; i < n_edges; i++) {
                Edge e = edgeList.get(i);
                for (int j = 0; j < n_vertex; j++) {
                    incidenceMatrix[j][i] = 0;
                    Vertex v = encapVertexList.get(j);
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
        return getVertexList().size();
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
            for (Vertex v: getVertexList()) {
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
        ArrayList<Vertex> encapVertexList = (ArrayList<Vertex>) getVertexList();
        boolean hasPath = false;
        List<Vertex> visitedVertexes = new ArrayList<>();
        visitedVertexes.add(origin);
        List<Vertex> adjList = getAdjListVertex(origin);
        if(encapVertexList.contains(origin) && encapVertexList.contains(destiny)){
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
        File arquivo = new File(graphPath);
        MyGraph graph = null;

        if(arquivo.exists()) {
            FileReader fr = new FileReader( arquivo );
            BufferedReader br = new BufferedReader( fr );
            String[] vertexes;
            String v2;
            String value;
            float valueFloat;

            while( br.ready() ) {
                String linha = br.readLine();
                if (linha.contains("digraph")){
                    graph = new MyGraph(true);
                    System.out.println(linha.contains("digraph"));
                }
                else if(linha.contains("graph")){
                    graph = new MyGraph(false);
                }

                else if((linha.contains("->") || linha.contains("--"))){
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
                        value = value.replaceAll("[^\\d.]", "");
                        valueFloat = Float.parseFloat(value);
                    }
                    else {
                        v2 = vertexes[1];
                        v2 = v2.substring(0, v2.length() - 1).strip();
                        valueFloat = 0;
                    }
                    Vertex vertexV1 = getVertex(v1);
                    if (vertexV1 == null) {
                        vertexV1 = graph.addVertex(v1);
                    }
                    Vertex vertexV2 = getVertex(v2);
                    if (vertexV2 == null) {
                        vertexV2 = graph.addVertex(v2);
                    }

                    graph.addEdge(vertexV1, vertexV2, valueFloat);

                }

                else if(!linha.contains("{") && !linha.contains("}")){
                    assert graph != null;
                    graph.addVertex(linha.split(";")[0].strip());
                }
            }
        }

        return graph;
    }

    public float[][] getGraphMatrix(){
        ArrayList<Vertex> encapVertexList = (ArrayList<Vertex>) this.getVertexList();
        int nVertex =  encapVertexList.size();
        float[][] graph = new float[nVertex][nVertex];

        for (int i = 0; i < nVertex; i++) {
            for (int j = 0; j < nVertex; j++) {
                for (Edge e: edgeList) {

                    if (e.origin.name.equals(encapVertexList.get(i).name) && e.destiny.name.equals(encapVertexList.get(j).name)){
                        graph[i][j] = e.value;
                    }
                }

            }
        }


        return graph;
    }

    int minDistance(float[] dist, Boolean[] sptSet)
    {
        float min = Float.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < this.getVertexList().size(); v++) {
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }

        return min_index;
    }

    void printDijkstra(float[] dist)
    {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < this.getVertexList().size(); i++) {
            System.out.println(i + " \t\t " + dist[i]);
        }
    }

    public void dijkstra(int src)
    {
        float graph[][] = getGraphMatrix();
        int nVertex = this.getVertexList().size();
        float dist[] = new float[nVertex];

        Boolean sptSet[] = new Boolean[nVertex];

        for (int i = 0; i < nVertex; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        dist[src] = 0;

        for (int count = 0; count < nVertex - 1; count++) {
            int u = minDistance(dist, sptSet);

            sptSet[u] = true;

            for (int v = 0; v < nVertex; v++) {

                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        printDijkstra(dist);
    }

    int minKey(float[] key, Boolean[] mstSet)
    {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < this.getVertexList().size(); v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = (int) key[v];
                min_index = v;
            }
        }

        return min_index;
    }

    void printMST(int parent[], float[] key)
    {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < this.getVertexList().size(); i++) {
            System.out.println(parent[i] + " - " + i + "\t" + key[i]);
        }
    }

    public void primMST()
    {
        float[][] graph = this.getGraphMatrix();
        int V = this.getVertexList().size();

        int parent[] = new int[V];

        float key[] = new float[V];

        Boolean mstSet[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        printMST(parent, key);
    }
}