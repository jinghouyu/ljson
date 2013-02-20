package org.jinghouyu.json.core;

import java.util.HashSet;
import java.util.Set;

public class TextScanner {

	private CharSequence text;
	private int pos;
	private static final Set<Character> whitespaceFlags = new HashSet<Character>(6);
	private int row = 1;
	private int col = 1;
    
	static {
        whitespaceFlags.add(' ');
        whitespaceFlags.add('\n');
        whitespaceFlags.add('\r');
        whitespaceFlags.add('\t');
        whitespaceFlags.add('\f');
        whitespaceFlags.add('\b');
	}
	
	public TextScanner(CharSequence text) {
		this.text = text;
	}
	
	public char current() {
		try {
			return text.charAt(pos);
		} catch(IndexOutOfBoundsException e) {
			throw new JsonException("syntax error, unreachable end");
		}
	}
	
	public int currentPos() {
		return pos;
	}
	
	public Position currentPosition() {
		return new Position(row, col);
	}
	
	public char get(int i) {
		return text.charAt(i);
	}
	
	private void incrementRow() {
		row++;
		col = 1;
	}
	
	public void increment() {
		char current = current();
		boolean hasRowIncrement = false;
		if(current == '\n') {
			incrementRow();
			hasRowIncrement = true;
		}
		if(current == '\r') {
			if(!hasNext()) {
				incrementRow();
				hasRowIncrement = true;
			} else {
				char next = get(pos + 1);
				if(next != '\n') {
					incrementRow();
					hasRowIncrement = true;
				}
			}
		}
		if(!hasRowIncrement) {
			col++;
		}
		pos++;
	}
	
	public void skipWhiteSpace() {
		while(isWhiteSpace()) {
			increment();
		}
	}
	
	public boolean isWhiteSpace() {
		return hasNext() ? whitespaceFlags.contains(current()) : false;
	}
	
	public boolean hasNext() {
		return pos < text.length() - 1;
	}
	
	public String toString() {
		return text.subSequence(pos, text.length()).toString();
	}
}

class Position {
	private int row;
	private int col;
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public String toString() {
		return "row at " + row + " and col at " + col; 
	}
}