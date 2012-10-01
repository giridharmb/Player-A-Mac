package com.giri.player;

public class AllSongsObjectRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int _index = 0;
	
	public AllSongsObjectRequest(int n) {
		_index = n;
	}
	
	public Object execute() {
		return _index;
	}

}
