import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static LinkedList snake;
    private static int foodX, foodY;
    private static Direction direction = null;
    private static boolean gameOver = false;

    enum Direction {
        Up, Down, Left, Right
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        snake = new LinkedList();
        snake.add(5, 5);
        spawnFood();

        while (!gameOver) {
            printBoard();
            direction = getDirectionInput(scanner);

            moveSnake();
            checkCollisions();
        }

        System.out.println("Game Over!");
    }

    private static Direction getDirectionInput(Scanner scanner) {
        char input = scanner.next().toLowerCase().charAt(0);

        return switch (input) {
            case 'w' -> Direction.Up;
            case 's' -> Direction.Down;
            case 'a' -> Direction.Left;
            case 'd' -> Direction.Right;

            default -> getDirectionInput(scanner);
        };
    }

    private static void printBoard() {
        char[][] board = new char[HEIGHT][WIDTH];

        // Initialize empty board
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = '.';
            }
        }

        // Place food on the board
        board[foodY][foodX] = 'F';

        // Place the snake on the board
        Node current = snake.getHead();
        // This is the standard way to walk a linked list. If you don't understand this, re-watch the course video.
        while (current != null) {
            board[current.y][current.x] = 'O';
            current = current.next;
        }

        // Print the board
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                // Print out the current character with a space
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void moveSnake() {
        Node head = snake.getHead();
        int newHeadX = head.x;
        int newHeadY = head.y;

        switch (direction) {
            case Direction.Up:
                newHeadY--;
                break;
            case Direction.Down:
                newHeadY++;
                break;
            case Direction.Left:
                newHeadX--;
                break;
            case Direction.Right:
                newHeadX++;
                break;
        }

        // Add new head to the linked list
        snake.add(newHeadX, newHeadY);

        // Check for collision with food. If so, spawn more food and exit.
        // This will not call snake.remove() and therefore keep the additional (new) body part.
        if (newHeadX == foodX && newHeadY == foodY) {
            spawnFood();
            return;
        }

        snake.remove();
    }

    private static void spawnFood() {
        Random rand = new Random();
        foodX = rand.nextInt(WIDTH);
        foodY = rand.nextInt(HEIGHT);
    }

    private static void checkCollisions() {
        Node head = snake.getHead();
        // Check wall collision
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver = true;
        }

        // Check for self-collision (if the snake head overlaps with any body segment)
        Node current = head.next;
        while (current != null) {
            if (head.x == current.x && head.y == current.y) {
                gameOver = true;
                break;
            }
            current = current.next;
        }
    }
}
