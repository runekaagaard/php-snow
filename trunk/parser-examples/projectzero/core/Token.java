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
package com.ibm.p8.engine.parser.core;

import com.ibm.p8.engine.core.util.NameString;

//////////////////////////////////////////////////////////////////
//                                                              //
//                 IMPORTANT WARNING                            //
//                                                              //
//   This file was auto-generated by the LPG Eclipse Tooling.   //
//       Do not edit this file. It will be overwritten.         //
//                                                              //
//////////////////////////////////////////////////////////////////


/**
 * Tokens are generated by the scanner when the input is read. Tokens don't
 * contain any string values, just indices to the original input char array
 * Tokens have an associated Ast node, which is useful for content assist, etc.
 * LexStream has support to find the token under a given character offset.
 * 
 * Based on the LEG examples shipped with JikesPG
 * 
 */
public class Token implements PHPParsersym {

    private Token predoc;

	/**
	 * Get the token doc.
	 * 
	 * @return - an int
	 */
	public Token getDoc() {
		return predoc;
	}
    
	/**
	 * Set the token doc.
	 * @param t <code>Token</code>
	 * 
	 */
	public void setDoc(Token t) {
		predoc = t;
	}
	private int kind = 0;

	/**
	 * Get the token kind.
	 * 
	 * @return - an int
	 */
	public int getKind() {
		return kind;
	}

	private int startOffset = 0;

	/**
	 * Get the token start.
	 * 
	 * @return - an int
	 */
	public int getStartOffset() {
		return startOffset;
	}

	private int endOffset = 0;

	/**
	 * Get the token end.
	 * 
	 * @return - an int
	 */
	public int getEndOffset() {
		return endOffset;
	}

	private int line;

	/**
	 * Get the token line.
	 * 
	 * @return - an int
	 */
	public int getLine() {
		return line;
	}


	private Scanner scanner;

	/**
	 * Get the token scanner.
	 * 
	 * @return - a scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}


	/**
	 * Set the token kind.
	 * @param knd - the kind
	 */
	public void setKind(int knd) {
		kind = knd;
	}

	/**
	 * Set the token start.
	 * @param off the offset
	 */
	public void setStartOffset(int off) {
		startOffset = off;
	}

	/**
	 * Set the token end.
	 * @param off the offset
	 */
	public void setEndOffset(int off) {
		endOffset = off;
	}

	/**
	 * Set the token line.
	 * @param ln the line.
	 */
	public void setLine(int ln) {
		line = ln;
	}


	/**
	 * Get the token scanner.
	 * @param s the scanner.
	 */
	public void setScanner(Scanner s) {
		scanner = s;
	}

/**
	 * Constructor.
	 * 
	 */
	public Token() {
	}

	/**
	 * Convert to string.
	 * 
	 * @see java.lang.Object#toString()
	 * @return a string
	 */
	public String toString() {
		return toString(scanner.getContents()).intern();
	}
	
	NameString nameString = null;
/**
	 * Convert to <code>NameString</code> .
	 * 
	 * @see java.lang.Object#toString()
	 * @return a string
	 */
	public NameString toNameString() {
		if (nameString == null) {
			nameString = new NameString(toString(scanner.getContents()));
		}
		return nameString;
	}
	
	/**
	 * Convert the contents to a string.
	 * 
	 * @param contents -
	 *            the char array.
	 * @return a string.
	 */
	public String toString(char[] contents) {
		try {
			int len = endOffset - startOffset + 1;
			if (startOffset == -1) {
				return "<EOF>";
			}
			return new String(contents, startOffset, len);
		} catch (Exception e) {
			return "Internal Error: token " + getTokenKindName()
					+ " endOffset=" + endOffset + " contents.length="
					+ contents.length;
		}
	}

	/**
	 * Test for equality.
	 * 
	 * @param contents -
	 *            the char array
	 * @param string -
	 *            a string
	 * @return - boolean true is matched.
	 */
	public boolean equals(char[] contents, String string) {
		int len = string.length();
		if (endOffset - startOffset + 1 != len) {
			return false;
		}
		for (int n = 0, k = startOffset; n < len; n++, k++) {
			if (string.charAt(n) != contents[k]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Equality case independent.
	 * @param contents - the char array.
	 * @param string the string.
	 * @return true if equal.
	 */
	public boolean equalsIgnoreCase(char[] contents, String string) {
		int len = string.length();
		if (endOffset - startOffset + 1 != len) {
			return false;
		}
		for (int n = 0, k = startOffset; n < len; n++, k++) {
			if (string.charAt(n) != Character.toLowerCase(contents[k])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the token name.
	 * @return string name.
	 */
	public String getTokenKindName() {
		if (kind == -1) {
			return "[-1]";
		}
		return tokenNames[kind];
	}
	
	/**
	 * return the token names list.
	 * @return list of strings
	 */
	public static String[] getTokenNames() {
		return tokenNames;
	}
	
	/**
	 * return the token name.
	 * @param i - index.
	 * @return string
	 */
	public static String getTokenName(int i) {
		if (i > 0 && i < tokenNames.length) {
			return tokenNames[i];
		}
		return "";
	}


	private static String[] tokenNames = new String[NUM_TOKENS + 1];
	static {
			tokenNames[44] = "T_INCLUDE";
			tokenNames[45] = "T_INCLUDE_ONCE";
			tokenNames[46] = "T_EVAL";
			tokenNames[47] = "T_REQUIRE";
			tokenNames[48] = "T_REQUIRE_ONCE";
			tokenNames[77] = "T_COMMA";
			tokenNames[110] = "T_LOGICAL_OR";
			tokenNames[111] = "T_LOGICAL_XOR";
			tokenNames[112] = "T_LOGICAL_AND";
			tokenNames[49] = "T_PRINT";
			tokenNames[10] = "T_EQUAL";
			tokenNames[11] = "T_PLUS_EQUAL";
			tokenNames[12] = "T_MINUS_EQUAL";
			tokenNames[13] = "T_MUL_EQUAL";
			tokenNames[14] = "T_DIV_EQUAL";
			tokenNames[15] = "T_CONCAT_EQUAL";
			tokenNames[16] = "T_MOD_EQUAL";
			tokenNames[17] = "T_AND_EQUAL";
			tokenNames[18] = "T_OR_EQUAL";
			tokenNames[19] = "T_XOR_EQUAL";
			tokenNames[20] = "T_SL_EQUAL";
			tokenNames[21] = "T_SR_EQUAL";
			tokenNames[78] = "T_QUESTION";
			tokenNames[72] = "T_COLON";
			tokenNames[79] = "T_BOOLEAN_OR";
			tokenNames[73] = "T_BOOLEAN_AND";
			tokenNames[69] = "T_VERTICAL_LINE";
			tokenNames[67] = "T_CARET";
			tokenNames[22] = "T_AMPERSAND";
			tokenNames[113] = "T_IS_EQUAL";
			tokenNames[114] = "T_IS_NOT_EQUAL";
			tokenNames[115] = "T_SHR_EQUAL";
			tokenNames[116] = "T_IS_IDENTICAL";
			tokenNames[117] = "T_IS_NOT_IDENTICAL";
			tokenNames[118] = "T_LT";
			tokenNames[119] = "T_IS_SMALLER_OR_EQUAL";
			tokenNames[120] = "T_GT";
			tokenNames[121] = "T_IS_GREATER_OR_EQUAL";
			tokenNames[26] = "T_SL";
			tokenNames[27] = "T_SR";
			tokenNames[2] = "T_PLUS";
			tokenNames[3] = "T_MINUS";
			tokenNames[28] = "T_PERIOD";
			tokenNames[23] = "T_ASTERISK";
			tokenNames[24] = "T_SLASH";
			tokenNames[25] = "T_PERCENT";
			tokenNames[50] = "T_EXCLAMATION";
			tokenNames[122] = "T_INSTANCEOF";
			tokenNames[51] = "T_TILDE";
			tokenNames[4] = "T_INC";
			tokenNames[5] = "T_DEC";
			tokenNames[52] = "T_INT_CAST";
			tokenNames[53] = "T_DOUBLE_CAST";
			tokenNames[54] = "T_STRING_CAST";
			tokenNames[55] = "T_ARRAY_CAST";
			tokenNames[56] = "T_OBJECT_CAST";
			tokenNames[57] = "T_BOOL_CAST";
			tokenNames[58] = "T_UNSET_CAST";
			tokenNames[59] = "T_FLOAT_CAST";
			tokenNames[60] = "T_AT_MARK";
			tokenNames[80] = "T_BRACKET_OPEN";
			tokenNames[33] = "T_NEW";
			tokenNames[61] = "T_CLONE";
			tokenNames[62] = "T_EXIT";
			tokenNames[63] = "T_DIE";
			tokenNames[81] = "T_IF";
			tokenNames[123] = "T_ELSEIF";
			tokenNames[124] = "T_ELSE";
			tokenNames[125] = "T_ENDIF";
			tokenNames[29] = "T_LNUMBER";
			tokenNames[30] = "T_DNUMBER";
			tokenNames[7] = "T_STRING";
			tokenNames[64] = "T_STRING_VARNAME";
			tokenNames[1] = "T_VARIABLE";
			tokenNames[126] = "T_NUM_STRING";
			tokenNames[82] = "T_INLINE_HTML";
			tokenNames[145] = "T_CHARACTER";
			tokenNames[146] = "T_BAD_CHARACTER";
			tokenNames[68] = "T_ENCAPSED_AND_WHITESPACE";
			tokenNames[70] = "T_CONSTANT_ENCAPSED_STRING";
			tokenNames[83] = "T_ECHO";
			tokenNames[84] = "T_DO";
			tokenNames[74] = "T_WHILE";
			tokenNames[127] = "T_ENDWHILE";
			tokenNames[85] = "T_FOR";
			tokenNames[128] = "T_ENDFOR";
			tokenNames[86] = "T_FOREACH";
			tokenNames[129] = "T_ENDFOREACH";
			tokenNames[87] = "T_DECLARE";
			tokenNames[130] = "T_ENDDECLARE";
			tokenNames[101] = "T_AS";
			tokenNames[88] = "T_SWITCH";
			tokenNames[131] = "T_ENDSWITCH";
			tokenNames[102] = "T_CASE";
			tokenNames[103] = "T_DEFAULT";
			tokenNames[89] = "T_BREAK";
			tokenNames[90] = "T_CONTINUE";
			tokenNames[104] = "T_FUNCTION";
			tokenNames[132] = "T_CONST";
			tokenNames[91] = "T_RETURN";
			tokenNames[92] = "T_TRY";
			tokenNames[133] = "T_CATCH";
			tokenNames[93] = "T_THROW";
			tokenNames[94] = "T_USE";
			tokenNames[95] = "T_GLOBAL";
			tokenNames[71] = "T_STATIC";
			tokenNames[96] = "T_ABSTRACT";
			tokenNames[97] = "T_FINAL";
			tokenNames[105] = "T_PRIVATE";
			tokenNames[106] = "T_PROTECTED";
			tokenNames[107] = "T_PUBLIC";
			tokenNames[134] = "T_VAR";
			tokenNames[98] = "T_UNSET";
			tokenNames[65] = "T_ISSET";
			tokenNames[66] = "T_EMPTY";
			tokenNames[135] = "T_HALT_COMPILER";
			tokenNames[108] = "T_CLASS";
			tokenNames[136] = "T_INTERFACE";
			tokenNames[137] = "T_EXTENDS";
			tokenNames[138] = "T_IMPLEMENTS";
			tokenNames[109] = "T_OBJECT_OPERATOR";
			tokenNames[139] = "T_DOUBLE_ARROW";
			tokenNames[34] = "T_LIST";
			tokenNames[31] = "T_ARRAY";
			tokenNames[35] = "T_CLASS_C";
			tokenNames[36] = "T_METHOD_C";
			tokenNames[37] = "T_FUNC_C";
			tokenNames[38] = "T_LINE_C";
			tokenNames[39] = "T_FILE_C";
			tokenNames[147] = "T_COMMENT";
			tokenNames[140] = "T_DOC_COMMENT";
			tokenNames[148] = "T_OPEN_TAG";
			tokenNames[149] = "T_OPEN_TAG_WITH_ECHO";
			tokenNames[150] = "T_CLOSE_TAG";
			tokenNames[151] = "T_WHITESPACE";
			tokenNames[141] = "T_START_HEREDOC";
			tokenNames[142] = "T_END_HEREDOC";
			tokenNames[6] = "T_DOLLAR_OPEN_CURLY_BRACES";
			tokenNames[9] = "T_CURLY_OPEN";
			tokenNames[143] = "T_PAAMAYIM_NEKUDOTAYIM";
			tokenNames[40] = "T_SEMICOLON";
			tokenNames[32] = "T_LPAR";
			tokenNames[99] = "T_RPAR";
			tokenNames[100] = "T_CURLY_CLOSE";
			tokenNames[144] = "T_BRACKET_CLOSE";
			tokenNames[75] = "T_DOUBLE_QUOTE";
			tokenNames[76] = "T_SINGLE_QUOTE";
			tokenNames[41] = "T_BACK_QUOTE";
			tokenNames[8] = "T_DOLLAR";
			tokenNames[42] = "T_BINARY";
			tokenNames[43] = "T_LOWER_BINARY";
			tokenNames[152] = "$eof";
			tokenNames[153] = "$error";
	}
}