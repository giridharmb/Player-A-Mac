package com.giri.player;

public class MyCalculation extends WorkRequest
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int n;
  
    public MyCalculation( int n )
    {
        this.n = n;
    }
    public Object execute()
    {
        return new Integer( n * n );
    }
}