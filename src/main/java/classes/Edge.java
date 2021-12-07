package classes;

public class Edge {
    Vertex origin;
    Vertex destiny;
    float value;

    Edge(Vertex origin, Vertex destiny, float value) {
        this.origin = origin;
        this.destiny = destiny;
        this.value = value;
    }
}
