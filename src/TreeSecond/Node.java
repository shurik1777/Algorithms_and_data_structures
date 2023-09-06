package TreeSecond;

public class Node {
    public int key;
    public Node left;
    public Node right;
    public boolean color;

    public Node(int key, boolean color) {
        this.key = key;
        this.color = color;
    }
}