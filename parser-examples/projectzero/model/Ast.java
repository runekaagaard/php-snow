/*
 * ============================================================================
 * Licensed Materials - Property of IBM
 * Project  Zero
 *
 * (C) Copyright IBM Corp. 2007  All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 * ============================================================================
 * Copyright (c) 1999 - 2006 The PHP Group. All rights reserved.
 * ============================================================================
 */
package com.ibm.p8.engine.parser.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.ibm.p8.engine.ast.AstTerminal_T_ARRAY;
import com.ibm.p8.engine.ast.AstTerminal_T_STRING;
import com.ibm.p8.engine.ast.AstVisitor;
import com.ibm.p8.engine.ast.Astclass_function_call;
import com.ibm.p8.engine.ast.Astfunction_call;
import com.ibm.p8.engine.ast.Aststatic_class_constant;
import com.ibm.p8.engine.ast.Aststatic_scalar;
import com.ibm.p8.engine.ast.Astvariable;
import com.ibm.p8.engine.ast.WritableNode;
import com.ibm.p8.engine.ast.utils.ExecutionContext;
import com.ibm.p8.engine.core.ErrorType;
import com.ibm.p8.engine.core.ExceptionWrapper;
import com.ibm.p8.engine.core.FatalError;
import com.ibm.p8.engine.core.PHPErrorHandler;
import com.ibm.p8.engine.core.PHPValue;
import com.ibm.p8.engine.core.RuntimeInterpreter;
import com.ibm.p8.engine.parser.core.Parser;
import com.ibm.p8.engine.parser.core.Rule;
import com.ibm.p8.engine.parser.core.Token;
import com.ibm.p8.engine.parser.custom.Factory;
import com.ibm.p8.engine.parser.gencode.CodeType;
import com.ibm.p8.engine.parser.gencode.GeneratorContext;
import com.ibm.p8.utilities.log.P8LogManager;
import com.ibm.phpj.logging.SAPIComponent;
import com.ibm.phpj.logging.SAPILevel;


//////////////////////////////////////////////////////////////////
//                                                              //
//   This file was auto-generated by the LPG Eclipse Tooling.   //
//   It is safe to edit this file. It will not be overwritten.  //
//                                                              //
//////////////////////////////////////////////////////////////////

/**
 * The base class for Ast nodes. You are expected to write concrete subclasses.
 * This class declaration can be enhanced to make nodes contain context-sensitive
 * date depending on the programming language they represent.
 * 
 */
public class Ast implements Visitable {

	protected static final Logger LOGGER = P8LogManager._instance.getLogger(SAPIComponent.Interpreter);

	private static int totalNodes;
	private Children children;
	private Token token;
	private Rule rule;
	private String ruleName;
	private Ast parent;
	private int id;
	private static final Ast EMPTY = new Ast();
	
	/** 
	 * Children - a class which is lighter weight than a Vector.
	 * few % lighter than ArrayList.
	 * does not support iterator() - since for short lists (like these)
	 * iterators are comparatively expensive - objects are allocated
	 * 
	 */
	private static class Children {
		private static final int INITIAL_SIZE = 5;

		private static final int INCREMENT_SIZE = 50;

		private Ast[] nodes = new Ast[INITIAL_SIZE];

		private int nnodes = 0;

		/**
		 * Method finds the number of children of a node.
		 * @return int - the number of children
		 */
		public int size() {
			return nnodes;
		}

		/**
		 * Method to get a specific child.
		 * @param index - this index into Children
		 * @return the Child
		 */
		public Ast get(int index) {
			return nodes[index];
		}

		/**
		 * Add a child to a node.
		 * @param node - The node to add.
		 * @return an Ast node
		 */
		public Ast add(Ast node) {
			ensureCapacity(nnodes + 1);
			return nodes[nnodes++] = node;
		}


		/**
		 * Set a child at an index - used by specialization.
		 * @param index of the child.
		 * @param node to add.
		 * @return the node added.
		 */
		public Ast set(int index, Ast node) {
			return nodes[index] = node;
		}

		/**
		 * Add a list of children to a node.
		 * @param cc - the children to add.
		 */
		public void addAll(Children cc) {
			for (int i = 0; i < cc.size(); i++) {
				add(cc.get(i));
			}
		}

		/**
		 * Find the index of a child in the children.
		 * @param node - the child to find
		 * @return the index or -1 for not found.
		 */
		public int indexOf(Ast node) {
			for (int i = 0; i < size(); i++) {
				if (node == get(i)) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * Make the array big enough to add a child.
		 * @param wanted - the size required.
		 */
		public void ensureCapacity(int wanted) {
			if (wanted > nodes.length) {
				// round up
				wanted = ((wanted / INCREMENT_SIZE) + 1) * INCREMENT_SIZE;
				Ast[] newNodes = new Ast[wanted];
				System.arraycopy(nodes, 0, newNodes, 0, nnodes);
				nodes = newNodes;
			}
		}
	}
	
	/**
	 * Return the number of children of this node.
	 * @return integer
	 */
	public int getNumChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}
	
	/**
	 * Set a child at an index.
	 * @param index of the child
	 * @param inchild to set.
	 */
	public void setChild(int index, Ast inchild) {
		children.set(index, inchild);
	}

	/**
	 * Set the total nodes for this tree.
	 * @param n - the number of nodes
	 */
	public static void setTotalNodes(int n) {
		totalNodes = n;
	}


	/**
	 * Constructor setting a unique identifier.
	 *
	 */
	public Ast() {
		id = totalNodes++;
	}

	/**
	 * Copy constructor.
	 * @param in - the ast node to clone.
	 */
	public Ast(Ast in) {
		children = in.children;
		token = in.token;
		rule = in.rule;
		ruleName = in.ruleName;
		parent = in.parent;
		children = in.children;
		id = in.id;
	}

	public void setRule(Rule inrule) {
		this.rule = inrule;
	}

	public Token getToken() {
		return token;
	}

	public Ast getParent() {
		return parent;
	}

	public void setParent(Ast par) {
		parent = par;
	}

	/**
	 * Get a child at index.
	 * @param index the index
	 * @return the child 
	 */
	public Ast getChild(int index) {
		return children.get(index);
	}
	
	/**
	 * Get a writable child at index.
	 * 
	 * @param index
	 *            the index
	 * @param runtime
	 *            the runtime
	 * 
	 * @return the writable child
	 */
	public WritableNode getWritableChild(RuntimeInterpreter runtime, int index) {
		Ast child = getChild(index);
		if (child instanceof WritableNode) {
			if (child instanceof Astvariable && ((Astvariable)child).isMethodCall()) {
				runtime.raisePreExecError(ErrorType.E_COMPILE_ERROR, "Method.WriteContext",
						null, this.getFileName(), this.getLineNumber());
				throw new ExceptionWrapper(runtime, PHPValue.createInt(0));
			}
			return (WritableNode) child;
		} else {
			if (child instanceof Astfunction_call || child instanceof Astclass_function_call) {
				runtime.raisePreExecError(ErrorType.E_COMPILE_ERROR, "Function.WriteContext",
						null, this.getFileName(), this.getLineNumber());
				throw new ExceptionWrapper(runtime, PHPValue.createInt(0));
			}
			if (LOGGER.isLoggable(SAPILevel.SEVERE)) {
				Object[] inserts = new Object[] { child };
				LOGGER.log(SAPILevel.SEVERE, "3031", inserts);
			}
			throw new FatalError("Attempted to obtain non-writable node for writing: " + child);
		}
	}

	/**
	 * Add a child to the node.
	 * @param parser - the parser adding
	 * @param child - the child to add.
	 * @param intoken - the token which caused this addition
	 */
	public void addChild(Parser parser, Ast child, Token intoken) {
		if (child == null) {
			child = Factory.createAstTerminal(intoken.getTokenKindName());
			child.token = intoken;
		}
		if (child != EMPTY) {
			if (parser.getFlatten() 
					&& !parser.getAstNodesToNotFlatten().contains(child.getClass())
					&& (child.hasOneChild()
							|| parser.getAstNodesToFlatten().contains(child.getClass()))) {
				flattenChild(child);
			} else {
				child.parent = this;
				if (children == null) {
					children = new Children();
				}
				children.add(child);
			}
		}
	}
	
	/**
	 * Accept a visitor.
	 * 
	 * @param v
	 *            the visitor
	 */
	public void accept(AstVisitor v) {
		// Warn as no visitor should call the base class.
		if (LOGGER.isLoggable(SAPILevel.WARNING)) {
			LOGGER.log(SAPILevel.WARNING, "5503");
		}
	}

	/**
	 * Test if there is a single child.
	 * @return true if there is one and only one.
	 */
	public boolean hasOneChild() {
		return (children != null) && (children.size() == 1);
	}

	/**
	 * Return a string name for this class.
	 * @return string to print
	 */
	public String toString() {
		
		String fullClassName = this.getClass().toString();
		int startOffset = fullClassName.lastIndexOf('.') + 1;
		String shortClassName = fullClassName.substring(startOffset);

		return String.format("%s (%s) [id:%d] {line:%d}",
				shortClassName,
				(token != null) ? "\"" + token.toString() + "\"" : "no token",
				getID(), 
				getLineNumber());
	}


	/**
	 * Remove a child, because we asked to ignore it using the nodesToFlatten list,
	 * or it has a single child.
	 * 	 * @param child - the child to remove.
	 */
	private void flattenChild(Ast child) {

		if (children == null) {
			children = child.children;
		} else if (child.children != null) {
			children.addAll(child.children);
		}

		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				Ast newchild = children.get(i);
				newchild.parent = this;
			}
		}
	}

	/**
	 * Default code generator - I dont have an implementation for this node.
	 * 
	 * @param generator .
	 * @param push . 
	 * @param ctx .
	 *  
	 * @return the code type
	 */
	public CodeType generate(GeneratorContext generator, boolean push, ExecutionContext ctx) {
		// Trying to generate code from the Ast base class suggests an execution handler is missing.
		RuntimeInterpreter runtime = generator.getRuntime();
		Object[] insert = {this.toString()};
		runtime.raiseExecError(PHPErrorHandler.E_ERROR, null, "Engine.UnimplementedLanguageFeature", insert);
		return null;
	}

	public boolean isExecutableStatement() {
		return true;
	}

	public int getID() {
		return id;
	}

	/**
	 * Replaces one node with another.
	 * @param node - the node to take away.
	 * @param anotherNode - the node to add.
	 */
	public void replace(Ast node, Ast anotherNode) {
		int index = children.indexOf(node);
		assert index != -1 : "Could not find node " + node + " in children of " + this;  
		children.set(index, anotherNode);
		if (anotherNode.children != null) {
			for (int i = 0; i < anotherNode.children.size(); i++) {
				Ast child = anotherNode.children.get(i);
				child.parent = anotherNode;
			}
		}
	}

	/**
	 * Is this node the top node of the tree?
	 * @return true if node is root of tree
	 */
	public boolean isRootNode() {

		return (null == parent);
	}

	/**
	 * @return line number corresponding to this node, or 0
	 *         if no token can be found.
	 */
	public int getLineNumber() {
		Token closestToken = this.getClosestToken();
		if (closestToken == null) {
			return 0;
		}
		return closestToken.getLine();
	}

	/**
	 * @return filename corresponding to this node, or "" 
	 *         if no token can be found.
	 */
	public String getFileName() {
		Token closestToken = this.getClosestToken();

		if (closestToken == null) {
			return "";
		}
		if (closestToken.getScanner() == null) {
			return "";
		}
		return closestToken.getScanner().fileName;
	}

	/**
	 * @return this node's doc comment, or null if there is no doc comment.
	 */
	public String getDocCommentString() {
		
		// The scanner attaches the doc comment to the first non-null token encountered after the doc comment.
		// This token is found by carrying out a depth-first search from the current node.
		Token firstToken = this.getTokenDepthFirst();
		
		if (firstToken == null) {
			// No non-null token was found
			return null;
		}	
		
		if (firstToken.getDoc() == null) {
			// The first token found with a depth-first search has no doc comment.
			// Therefore, this node has no doc comment.
			return null;
		}
		
		return firstToken.getDoc().toString();
	}
	
	
	/**
	 * @return the first non-null token from this node using a depth-first search, or null if no such token is found.
	 */
	private Token getTokenDepthFirst() {
		Token currentToken = this.getToken();
		
		int c = 0;
		while (currentToken == null && c < this.getNumChildren()) {
			currentToken = this.getChild(c++).getTokenDepthFirst();
		}
		
		return currentToken;
	}

	/**
	 * @return closest non-null token, or null if no such token is found.
	 */
	private Token getClosestToken() {
		// First try downwards breadth-first search.
		// This is sufficient for most nodes.
		ArrayList<Ast> queue = new ArrayList<Ast>();
		queue.add(this);
		while (!queue.isEmpty()) {
			Ast current = queue.remove(0);

			Token tentative = current.getToken();
			if (tentative != null) {
				return tentative;
			}

			if (current.children != null) {
				for (int i = 0; i < current.children.size(); i++) {
					Ast child = current.children.get(i);
					queue.add(child);
				}
			}
		}

		// No luck looking through the children. Try back-tracking up through
		// parent. This is required for nodes that have a null token and no
		// children.
		Ast parent = this.getParent();
		if (parent != null) {
			Token tentative = parent.getClosestToken();

			if (tentative != null) {
				return tentative;
			}
		}

		// No non-null token found.
		return null;
	}

	/**
	 * Whether the entity represented by a node can be passed/returned/assigned
	 * by reference.
	 */
	public enum Referability {
		IS_REFERABLE,
		IS_NOT_REFERABLE,
		UNKNOWN,
	}
	
	/**
	 * Whether the entity represented by this node can be
	 * passed/returned/assigned by reference. For example:
	 * <ul>
	 * <li>references to variables can always be created</li>
	 * <li>references to constants can never be created</li>
	 * <li>references to function calls may or may not be valid depending on
	 * the function return value</li>
	 * </ul>
	 * 
	 * @return whether references can be made to the entity represented by this
	 *         node.
	 */
	public Referability isReferableNode() {
		return Referability.IS_NOT_REFERABLE;
	}

	/**
	 * Utility function to encapsulate logic required to check whether
	 * this is a static array declaration.
	 * @return true, if node represents a static array declaration
	 */
	public boolean isStaticArrayDeclaration() {
		return this instanceof Aststatic_scalar 
				&& this.getNumChildren() > 0
				&& this.getChild(0) instanceof AstTerminal_T_ARRAY;
	}
	
	/**
	 * Utility function to determine whether this is a static value that
	 * requires delayed initialisation.
	 * @return true, if node requires delayed initialisation
	 */
	public boolean requiresDelayedEvaluation() {
		return this instanceof Aststatic_class_constant // class constant - class may not have been defined.
		|| this instanceof AstTerminal_T_STRING			// possible constant
		|| this.isStaticArrayDeclaration();				// static array declaration may 
	}

	/**
	 * Indicates that this node represents a constant value.
	 * @return true if constant
	 */
	public boolean isConstant() {
		return false;
	}

	/**
	 * Returns the PHPValue of this constant node.
	 * @return value
	 */
	public PHPValue getConstantValue() {
		assert isConstant() : "getConstantValue called for variable node " + this;
		throw new FatalError("Unimplemented constant " + this);
	}
}
