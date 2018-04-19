/*
 * Copyright 2018, Robert Dyer, Mohd Arafat
 *                 and Bowling Green State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package boa.graphs.cdg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import boa.graphs.cfg.CFG;
import boa.graphs.cfg.CFGEdge;
import boa.graphs.cfg.CFGNode;
import boa.graphs.trees.PDTree;
import boa.graphs.trees.TreeNode;
import boa.types.Ast.Method;
import boa.types.Control;

/**
 * @author marafat
 */

public class CDG {

    private CDGNode entryNode;
    private Set<CDGNode> nodes = new HashSet<CDGNode>();

    public CDG(final CFG cfg) throws Exception {
        PDTree pdTree = new PDTree(cfg);
        constructCDG(pdTree, cfg);
        //remove start node and replace it with entry
        CDGNode startNode = getNode(0);
        CDGEdge firstEdge = new CDGEdge(getNode(cfg.getNodes().size()), startNode, "T");
        entryNode.getSuccessors().remove(startNode);
        entryNode.getOutEdges().remove(firstEdge);
        nodes.remove(startNode);
        entryNode.setId(0);
    }

    public CDG(final Method method) throws Exception {
        this(new CFG(method));
    }

    //Getters
    public CDGNode getEntryNode() {
        return entryNode;
    }

    public Set<CDGNode> getNodes() {
        return nodes;
    }

    /**
     * Returns the node for the given node id, otherwise returns null
     *
     * @param id node id
     * @return
     */
    public CDGNode getNode(int id) {
        for (CDGNode n: nodes)
            if (n.getId() == id)
                return n;

        return null;
    }

    /**
     * Builds a Control Dependence Graph using the post dominator tree and control edges
     *
     * @param pdTree post dominator tree
     * @param cfg control flow graph
     */
    private void constructCDG(final PDTree pdTree, final CFG cfg) {
        Map<Integer[], String> controlEdges = new HashMap<Integer[], String>();
        for (CFGNode n: cfg.getNodes()) {
            if (n.getKind() == Control.CFGNode.CFGNodeType.CONTROL)
            for (CFGEdge e: n.outEdges)
                if (e.label().equals("."))
                    controlEdges.put(new Integer[]{e.getSrc().getId(), e.getDest().getId()}, "F");
                else
                    controlEdges.put(new Integer[]{e.getSrc().getId(), e.getDest().getId()}, e.label());
        }
        controlEdges.put(new Integer[]{cfg.getNodes().size(), 0}, "T");

        int graphSize = pdTree.getNodes().size();

        for (Integer[] enodes: controlEdges.keySet()) {
            TreeNode src = pdTree.getNode(enodes[0]);
            TreeNode dest = pdTree.getNode(enodes[1]);
            TreeNode srcParent = pdTree.getNode(enodes[0]).getParent();
            CDGNode source = getNode(src);

            while (!srcParent.equals(dest)) {
                CDGNode destination = getNode(dest);
                source.addSuccessor(destination);
                destination.addPredecessor(source);

                CDGEdge edge = new CDGEdge(source, destination, controlEdges.get(enodes));
                source.addOutEdges(edge);
                destination.addInEdges(edge);

                dest = dest.getParent();

                if (source.getId() == graphSize-1)
                    entryNode = source;
            }
        }
    }

    /**
     * Checks if a node already exists and returns it, otherwise returns a new node.
     *
     * @param treeNode a post dominator tree node
     * @return a new tree node or an existing tree node
     */
    private CDGNode getNode(final TreeNode treeNode) {
        CDGNode node = new CDGNode(treeNode.getId());
        if (nodes.contains(node)) {
            for (CDGNode n : nodes) {
                if (n.equals(node))
                    return n;
            }
        }
        node.setStmt(treeNode.getStmt());
        node.setExpr(treeNode.getExpr());
        nodes.add(node);

        return node;
    }
}