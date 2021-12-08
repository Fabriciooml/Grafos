package classes;

public class Edge {
    Vertex origin;
    Vertex destiny;
    int value;

    Edge(Vertex origin, Vertex destiny, int value) {
        this.origin = origin;
        this.destiny = destiny;
        this.value = value;
    }
}
