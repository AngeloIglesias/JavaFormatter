package com.example.javaformatter.nodes;

public class Method {
//    public static void format(CompilationUnit cu) {
//        new VoidVisitorAdapter<Void>() {
//            @Override
//            public void visit(MethodCallExpr n, Void arg) {
//                super.visit(n, arg);
//
//                // Create a list of all arguments and comments
//                List<Node> nodes = new LinkedList<>();
//                nodes.addAll(n.getArguments());
//                n.getComments().forEach(comment -> {
//                    if (comment.getRange().isPresent()) {
//                        nodes.add(comment);
//                    }
//                });
//
//                // Sort the nodes by their start position
//                nodes.sort(Comparator.comparing(node -> node.getRange().get().begin));
//
//                // Format the nodes
//                for (Node node : nodes) {
//                    // Format the node (this is where you would add your own formatting rules)
//                    // In this example, we're just printing the node as-is
//                    System.out.println(node.toString());
//                }
//            }
//        }.visit(cu, null);
//    }
}
