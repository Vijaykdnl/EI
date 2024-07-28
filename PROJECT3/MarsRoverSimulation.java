import java.util.*;

// Enum for Directions
enum Direction {
    N, E, S, W;

    public Direction turnLeft() {
        switch (this) {
            case N: return W;
            case E: return N;
            case S: return E;
            case W: return S;
            default: throw new IllegalStateException("Unexpected direction: " + this);
        }
    }

    public Direction turnRight() {
        switch (this) {
            case N: return E;
            case E: return S;
            case S: return W;
            case W: return N;
            default: throw new IllegalStateException("Unexpected direction: " + this);
        }
    }
}

// Command Interface
interface Command {
    void execute(Rover rover);
}

// Concrete Command to Move Forward
class MoveCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.moveForward();
    }
}

// Concrete Command to Turn Left
class TurnLeftCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.turnLeft();
    }
}

// Concrete Command to Turn Right
class TurnRightCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.turnRight();
    }
}

// Point Class to represent obstacles
class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

// Grid Class to represent the grid and obstacles
class Grid {
    private final int width, height;
    private final Set<Point> obstacles;

    public Grid(int width, int height, List<Point> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = new HashSet<>(obstacles);
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean hasObstacle(int x, int y) {
        return obstacles.contains(new Point(x, y));
    }
}

// Rover Class
class Rover {
    private int x, y;
    private Direction direction;
    private final Grid grid;

    public Rover(int x, int y, Direction direction, Grid grid) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.grid = grid;
    }

    public void moveForward() {
        int[] delta = getDirectionDelta();
        int newX = x + delta[0];
        int newY = y + delta[1];
        if (grid.isWithinBounds(newX, newY) && !grid.hasObstacle(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    private int[] getDirectionDelta() {
        switch (direction) {
            case N: return new int[]{0, 1};
            case E: return new int[]{1, 0};
            case S: return new int[]{0, -1};
            case W: return new int[]{-1, 0};
            default: throw new IllegalStateException("Unexpected direction: " + direction);
        }
    }

    public String statusReport() {
        return String.format("Rover is at (%d, %d) facing %s. No Obstacles detected.", x, y, direction);
    }
}

// Main Class to run the simulation
public class MarsRoverSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Grid size
        System.out.println("Enter grid width:");
        int width = scanner.nextInt();
        System.out.println("Enter grid height:");
        int height = scanner.nextInt();

        // Obstacles
        System.out.println("Enter number of obstacles:");
        int numObstacles = scanner.nextInt();
        List<Point> obstacles = new ArrayList<>();
        for (int i = 0; i < numObstacles; i++) {
            System.out.println("Enter obstacle " + (i + 1) + " coordinates (x y):");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            obstacles.add(new Point(x, y));
        }

        // Rover starting position and direction
        System.out.println("Enter rover starting x position:");
        int startX = scanner.nextInt();
        System.out.println("Enter rover starting y position:");
        int startY = scanner.nextInt();
        Direction startDirection = null;
        while (startDirection == null) {
            System.out.println("Enter rover starting direction (N, E, S, W):");
            String directionInput = scanner.next().toUpperCase();
            try {
                startDirection = Direction.valueOf(directionInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid direction. Please enter N, E, S, or W.");
            }
        }

        // Commands
        System.out.println("Enter commands (M for move, L for turn left, R for turn right), end with a non-command character:");
        List<Command> commands = new ArrayList<>();
        while (scanner.hasNext()) {
            String command = scanner.next().toUpperCase();
            switch (command) {
                case "M":
                    commands.add(new MoveCommand());
                    break;
                case "L":
                    commands.add(new TurnLeftCommand());
                    break;
                case "R":
                    commands.add(new TurnRightCommand());
                    break;
                default:
                    // Stop processing commands when a non-command is entered
                    scanner.close();
                    break;
            }
        }

        // Initialize grid and rover
        Grid grid = new Grid(width, height, obstacles);
        Rover rover = new Rover(startX, startY, startDirection, grid);

        // Simulate rover
        simulateRover(grid, rover, commands);
    }

    // Simulation Function
    private static void simulateRover(Grid grid, Rover rover, List<Command> commands) {
        for (Command command : commands) {
            command.execute(rover);
        }
        System.out.println(rover.statusReport());
    }
}
