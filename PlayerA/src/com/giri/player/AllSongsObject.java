package com.giri.player;

import java.io.Serializable;
import java.util.LinkedList;

public class AllSongsObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LinkedList<String> _ll = new LinkedList<String>();
	
	public AllSongsObject(LinkedList<String> lls) {
		_ll = lls;
	}

	public LinkedList<String> get_ll() {
		return _ll;
	}

	public void set_ll(LinkedList<String> _ll) {
		this._ll = _ll;
	}
	
}
