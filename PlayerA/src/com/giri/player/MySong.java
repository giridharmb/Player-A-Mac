package com.giri.player;

public class MySong extends SongRequest {
	
	private static final long serialVersionUID = 1L;
	
	int index = 0;
	
	public MySong(int n) {
		index = n;
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		MainWindow.PLAY(index);
		return null;
	}

}
