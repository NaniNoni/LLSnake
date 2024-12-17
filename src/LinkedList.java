class Node {
    // In this case, the node stores the x and y values directly. Wrapping it in a type such as a Vector2(i)
    // may be a good, but it's not necessary in this case and not worth the additional complexity.
    int x, y;
    // This is a singly linked list. No previous pointer necessary.
    Node next;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.next = null;
    }
}

class LinkedList {
    private Node head;
    private Node tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    // Add a node to the linked list (represents the snake's body)
    public void add(int x, int y) {
        Node newNode = new Node(x, y);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Remove the first node (represents removing the snake's tail when moving forward)
    public void remove() {
        if (head != null) {
            head = head.next;
        }
    }

    // Get the head node (the snake's head)
    public Node getHead() {
        return head;
    }

    // Display the list (debugging)
    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print("(" + current.x + ", " + current.y + ") ");
            current = current.next;
        }
        System.out.println();
    }
}
