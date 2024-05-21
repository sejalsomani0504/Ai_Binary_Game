import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TreeNode {
    String data;
    TreeNode leftSubTree;
    TreeNode rightSubTree;

    public TreeNode(String data) {
        this.data = data;
        this.leftSubTree = null;
        this.leftSubTree = null;
    }
}

public class BinaryTreeGame {
    private TreeNode root;
    private static String helpInfo = null;

    public BinaryTreeGame() {
        root = null;
    }

    public static void displayHelp(){
        if(helpInfo == null){
            System.out.println("Please load a game first.");
            return;
        }
        System.out.println(helpInfo);
    }

    public static void inorderTraversal(TreeNode rooTreeNode) {
        if(rooTreeNode == null) return;
        inorderTraversal(rooTreeNode.leftSubTree);
        System.out.print(rooTreeNode.data + " ");
        inorderTraversal(rooTreeNode.rightSubTree);
    }

    public static void preorderTraversal(TreeNode rooTreeNode) {
        if(rooTreeNode == null) return;
        System.out.print(rooTreeNode.data + " ");
        preorderTraversal(rooTreeNode.leftSubTree);
        preorderTraversal(rooTreeNode.rightSubTree);
    }

    public static void postorderTraversal(TreeNode rooTreeNode) {
        if(rooTreeNode == null) return;
        postorderTraversal(rooTreeNode.leftSubTree);
        postorderTraversal(rooTreeNode.rightSubTree);
        System.out.print(rooTreeNode.data + " ");
    }

    public TreeNode sortedArrayToBST(int start, int end, Map<String,TreeNode> nodesMap) {
        if (start > end)
            return null;
        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nodesMap.get(String.valueOf(mid)).data);
        root.leftSubTree = sortedArrayToBST(start, mid - 1, nodesMap);
        root.rightSubTree = sortedArrayToBST(mid + 1, end, nodesMap);
        return root;
    }

    public void loadGame(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            Map<String,TreeNode> nodesMap = new HashMap<>();
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if(lineNumber == 1) {
                    continue;
                }
                if(lineNumber == 2) {
                    helpInfo = line;
                    continue;
                }
                String[] parts = line.split(" ", 2);
                String nodeNumber = parts[0].trim();
                String questionOrAnswer = parts[1].trim();
                TreeNode newNode = new TreeNode(questionOrAnswer);
                nodesMap.put(nodeNumber, newNode);

            }
            root = sortedArrayToBST(0,nodesMap.size()-1,nodesMap);
            reader.close();
            System.out.println("Game loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading the game file.");
        }
    }
    
    public void playGame() {
        if (root == null) {
            System.out.println("Please load a game first.");
            return;
        }

        TreeNode currentNode = root;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(currentNode.data + " : ");
            if (currentNode.leftSubTree == null && currentNode.rightSubTree == null) {
                System.out.println("\nI guessed it!");
                break;
            }

            String answer = scanner.nextLine().toLowerCase().trim();

            if (answer.equals("y")) {
                currentNode = currentNode.rightSubTree;
            } else if (answer.equals("n")) {
                currentNode = currentNode.leftSubTree;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }

    public static void main(String[] args) {
        BinaryTreeGame game = new BinaryTreeGame();

        while (true) {
            System.out.println("\nMenu Operations:");
            System.out.println("P: Play the game");
            System.out.println("L: Load another game file");
            System.out.println("D: Display the binary tree");
            System.out.println("H: Help Information");
            System.out.println("X: Exit the program");

            System.out.print("Your choice: ");
            String choice = new Scanner(System.in).nextLine().toLowerCase();

            switch (choice) {
                case "p":
                    game.playGame();
                    break;
                case "l":
                    System.out.print("Enter the filename to load: ");
                    String fileName = new Scanner(System.in).nextLine();
                    game.loadGame(fileName);
                    break;
                case "d":
                    System.out.println("\nWhat order do you want to display?");
                    System.out.println("1. Inorder");
                    System.out.println("2. Preorder");
                    System.out.println("3. Postorder");
                    System.out.println("4. Return to main menu");
                    System.out.print("Your choice: ");
                    String order = new Scanner(System.in).nextLine();
                    switch (order) {
                        case "1":
                            inorderTraversal(game.root);
                            System.out.println();
                            break;
                        case "2":
                            preorderTraversal(game.root);
                            System.out.println();
                            break;
                        case "3":
                            postorderTraversal(game.root);
                            System.out.println();
                            break;
                        case "4":
                            break;
                        default:
                            break;
                    }
                    break;
                case "h":
                    displayHelp();
                    break;
                case "x":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose from the available options.");
            }
        }
    }
}
