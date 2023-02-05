import java.io.*;
import java.util.*;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis {
    public abstract int getResult();

    public abstract void visitNode(TreeNode node);

    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    ArrayList<TreeLeaf> visitTreeLeaf = new ArrayList<>();

    public int getResult() {
        int sumValues = 0;
        for (TreeLeaf currLeaf : visitTreeLeaf) {
            sumValues += currLeaf.getValue();
        }
        return sumValues;
    }

    public void visitNode(TreeNode node) {
        // we don't to implement this one
    }

    public void visitLeaf(TreeLeaf leaf) {
        visitTreeLeaf.add(leaf);
        // //System.out.println("SumInLeavesVisitor visit leaf");
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    ArrayList<Tree> visitTree = new ArrayList<>();

    public int getResult() {
        long result = 1L;
        int redNodes = 0;
        // System.out.println(visitTree.size() + " trees visited");
        for (Tree tree : visitTree) {
            if (tree.getColor().equals(Color.RED)) {
                result = (result*tree.getValue()) % (1000000007);
                if (redNodes > 0)
                    // System.out.print(", ");
                    // System.out.print(tree.getValue());
                    redNodes++;
            }
        }
        // System.out.println();
        // System.out.println(redNodes + " red nodes");
        return (int) (result);
    }

    public void visitNode(TreeNode node) {
        visitTree.add(node);
    }

    public void visitLeaf(TreeLeaf leaf) {
        visitTree.add(leaf);
    }
}

class FancyVisitor extends TreeVis {
    ArrayList<TreeLeaf> visitTreeLeaf = new ArrayList<>();
    ArrayList<TreeNode> visitTreeNode = new ArrayList<>();

    public int getResult() {
        // the absolute difference between
        // the sum of values stored in the tree's non-leaf nodes at even depth
        int sumTreeNodesValues = 0;
        for (TreeNode treeNode : visitTreeNode) {
            if (treeNode.getDepth() % 2 == 0)
                sumTreeNodesValues += treeNode.getValue();
        }
        // the sum of values stored in the tree's green leaf nodes
        int sumTreeLeafsValues = 0;
        for (TreeLeaf treeLeaf : visitTreeLeaf) {
            if (treeLeaf.getColor().equals(Color.GREEN))
                sumTreeLeafsValues += treeLeaf.getValue();
        }
        return Math.abs(sumTreeNodesValues - sumTreeLeafsValues);
    }

    public void visitNode(TreeNode node) {
        visitTreeNode.add(node);
    }

    public void visitLeaf(TreeLeaf leaf) {
        visitTreeLeaf.add(leaf);
    }
}

public class SolutionWithoutEntry {

    public static Tree solve() {
        // read the tree from STDIN and return its root as a return value of this
        // function
        File input = new File("input2.txt");
        try {
            Scanner sc = new Scanner(input);
            // get the number of nodes
            int numNodes = sc.nextInt();

            // get node values
            int[] nodeValues = new int[numNodes];
            for (int i = 0; i < numNodes; i++) {
                nodeValues[i] = sc.nextInt();
            }

            // get node colors
            Color[] nodeColors = new Color[numNodes];
            for (int i = 0; i < numNodes; i++) {
                nodeColors[i] = sc.nextInt() == 0 ? Color.RED : Color.GREEN;
            }

            // get edges
            // let's build a dictionary where the key is the node and the value oth the
            // related node
            Map<Integer, Set<Integer>> relationships = new HashMap<>();

            while (sc.hasNext()) {
                // System.out.println("has more lines");
                // the node is the key and the value is the depth
                int node1 = sc.nextInt();
                int node2 = sc.nextInt();
                // System.out.printf("one node %d <- related node %d\n", node1, node2);
                if (relationships.containsKey(node1)) {
                    Set<Integer> relatedNodes = relationships.get(node1);
                    relatedNodes.add(node2);
                    relationships.remove(node1);
                    relationships.put(node1, relatedNodes);
                    //relationships.replace(node1, relatedNodes);
                } else {
                    Set<Integer> relatedNodes = new HashSet<>();
                    relatedNodes.add(node2);
                    relationships.put(node1, relatedNodes);
                }
                if (relationships.containsKey(node2)) {
                    Set<Integer> relatedNodes = relationships.get(node2);
                    relatedNodes.add(node1);
                    relationships.remove(node2);
                    relationships.put(node2, relatedNodes);
                    //relationships.replace(node2, relatedNodes);
                } else {
                    Set<Integer> relatedNodes = new HashSet<>();
                    relatedNodes.add(node1);
                    relationships.put(node2, relatedNodes);
                }
            }
            // System.out.println("Finished relationships");
            sc.close();

            for (Integer currKey : relationships.keySet()) {
                StringBuilder strRelatedNodes = new StringBuilder();
                Set<Integer> relatedNodes = relationships.get(currKey);
                for (int relatedNode : relatedNodes) {
                    if (strRelatedNodes.length() != 0)
                        strRelatedNodes.append(", ");
                    strRelatedNodes.append(relatedNode);
                }
                // System.out.printf("%d - %s\n", currKey, strRelatedNodes.toString());
            }

            // now let's build a dictionary with the depths and see which nodes are not
            // leafs
            Map<Integer, Integer> depths = new HashMap<>();
            // let's add the root node
            depths.put(1, 0);
            // lets work with an array to mark the nodes that had the depth already
            // calculated
            boolean[] depthCalculated = new boolean[numNodes];
            for (int i = 0; i < depthCalculated.length; i++) {
                depthCalculated[i] = false;
            }
            // the root is already calculated
            depthCalculated[0] = true;
            boolean allDepthsCalculated;
            int depthsCalculations = 0;
            do {
                depthsCalculations++;
                // System.out.println("Depths calculations " + depthsCalculations);
                allDepthsCalculated = true;
                for (int i = 1; i < depthCalculated.length; i++) {
                    if (!depths.containsKey(i + 1)) {
                        allDepthsCalculated = false;
                        for (int relatedNode : relationships.get(i + 1)) {
                            if (depths.containsKey(relatedNode)) {
                                int parentDepth = depths.get(relatedNode);
                                // System.out.printf("depth for %d with parent id %d and parent depth %d\n", i +
                                // 1, relatedNode, parentDepth);
                                depths.put(i + 1, parentDepth + 1);
                                depthCalculated[i] = true;
                            }
                        }
                    }
                }
                for (int i = 0; i < depthCalculated.length; i++) {
                    // System.out.println("depth calculated for " + i + " " + depthCalculated[i]);
                }
            } while (!allDepthsCalculated);

            // System.out.println("All depths calculated");

            // how can we now if it's a node or a leaf
            // if my relationship is of a superior depth then I'm the parent
            // now create the Tree Nodes or Leafs
            Map<Integer, Tree> treeMap = new HashMap<>();
            boolean allNodesAdded;
            do {
                allNodesAdded = true;
                for (int i = 0; i < numNodes; i++) {
                    if (treeMap.containsKey(i + 1))
                        continue;// already has this node
                    // System.out.printf("Create node %d of %d\n", i + 1, numNodes);
                    int parentId = i + 1;// by default is the root the parend id
                    // who is the parent
                    for (int relatedNode : relationships.get(i + 1)) {
                        if (depths.get(i + 1) > depths.get(relatedNode)) {
                            parentId = relatedNode;
                            break;
                        }
                    }
                    // Am I a leaf
                    boolean imALeaf = true;
                    for (int relatedNode : relationships.get(i + 1)) {
                        if (depths.get(i + 1) < depths.get(relatedNode)) {
                            imALeaf = false;
                            break;
                        }
                    }
                    if (imALeaf) {
                        // System.out.println("Is leaf");
                        // let's add this leaf to the parent
                        if (!treeMap.containsKey(parentId) && i != 0) {
                            allNodesAdded = false;
                            // System.out.printf("leaf %d skipped\n", i + 1);
                            continue;
                        }
                        TreeLeaf treeLeaf = new TreeLeaf(nodeValues[i], nodeColors[i], depths.get(i + 1));
                        // System.out.println("Created leaf");
                        treeMap.put(i + 1, treeLeaf);
                        // System.out.println("Leaf added to treemap");
                        if (i == 0)
                            continue;// the root doesn't have a parent
                        TreeNode parentTreeNode = (TreeNode) treeMap.get(parentId);
                        parentTreeNode.addChild(treeLeaf);
                        // System.out.printf("added leaf %d to parent %d\n", i + 1, parentId);
                    } else {

                        // System.out.println("Is node");
                        if (!treeMap.containsKey(parentId) && i != 0) {
                            allNodesAdded = false;
                            // System.out.printf("node %d skipped\n", i + 1);
                            continue;
                        }
                        TreeNode treeNode = new TreeNode(nodeValues[i], nodeColors[i], depths.get(i + 1));
                        // System.out.println("Created node");
                        treeMap.put(i + 1, treeNode);
                        // let's add this node to the parent
                        if (i == 0)
                            continue;// the root doesn't have a parent
                        TreeNode parentTreeNode = (TreeNode) treeMap.get(parentId);
                        parentTreeNode.addChild(treeNode);
                        // System.out.printf("added node %d to parent %d\n", i + 1, parentId);
                    }
                }
            } while (!allNodesAdded);
            // return the root
            return treeMap.get(1);
        } catch (Exception e) {
            // System.out.println("File not found");
        }
        return null;
    }

    public static void main(String[] args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}