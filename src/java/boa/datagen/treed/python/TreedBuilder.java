package boa.datagen.treed.python;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;


public class TreedBuilder extends ASTVisitor implements TreedConstants {
	private int index = 1;
	private ASTNode root;
	//tree records children for every ast node: node -> childs
	HashMap<ASTNode, ArrayList<ASTNode>> tree = new HashMap<ASTNode, ArrayList<ASTNode>>();
	HashMap<ASTNode, Integer> treeHeight = new HashMap<ASTNode, Integer>(), treeDepth = new HashMap<ASTNode, Integer>();
	//treeVector[node]: represents whole subtree rooted at node, including current root of the
	//subtree and all descendants. on the other hand, treeRootVector represents
	//direct childs of current node only including itself
	HashMap<ASTNode, HashMap<String, Integer>> treeVector = new HashMap<ASTNode, HashMap<String, Integer>>(), treeRootVector = new HashMap<ASTNode, HashMap<String, Integer>>();
	
	public TreedBuilder(ASTNode root) {
		super();
		this.root = root;
		treeDepth.put(root, 0);
	}
	
	@Override
	public boolean visitGeneral(ASTNode node) {
		node.setProperty(PROPERTY_INDEX, index++);
		tree.put(node, new ArrayList<ASTNode>());
		if (node != root) {
			ASTNode p = node.getParent();
			treeDepth.put(node, treeDepth.get(p) + 1);
		}
		return true;
	}
	
	@Override
	public void endvisitGeneral(ASTNode node) {
		buildTree(node);
		buildTreeHeight(node);
		buildVector(node);
	}
	
	public void buildTree(ASTNode node) {
		if (node != root) {
			ASTNode p = node.getParent();
			ArrayList<ASTNode> children = tree.get(p);
			children.add(node);
		}
	}

	public void buildTreeHeight(ASTNode node) {
		ArrayList<ASTNode> children = tree.get(node);
		int max = 0;
		for (ASTNode child : children) {
			int h = treeHeight.get(child);
			if (h > max)
				max = h;
		}
		treeHeight.put(node, max + 1);
	}

	private void buildVector(ASTNode node) {
		ArrayList<ASTNode> children = tree.get(node);
		HashMap<String, Integer> vector = new HashMap<String, Integer>(), rootVector = new HashMap<String, Integer>();
		char label = TreedUtils.buildLabelForVector(node);
		String feature = "" + label;
		rootVector.put(feature, 1);
		for (ASTNode child : children) {
			add(vector, treeVector.get(child));
			HashMap<String, Integer> childRootVector = treeRootVector.get(child);
			for (String cf : childRootVector.keySet()) {
				if (cf.length() < GRAM_MAX_LENGTH) {
					String rf = label + cf;
					rootVector.put(rf, childRootVector.get(cf));
				}
			}
			childRootVector.clear();
			treeRootVector.remove(child);
		}
		add(vector, rootVector);
		treeVector.put(node, vector);
		treeRootVector.put(node, rootVector);
	}

	private void add(HashMap<String, Integer> vector, HashMap<String, Integer> other) {
		for (String key : other.keySet()) {
			int c = other.get(key);
			if (vector.containsKey(key))
				c += vector.get(key);
			vector.put(key, c);
		}
	}
}
