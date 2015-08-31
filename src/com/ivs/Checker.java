package com.ivs;

import com.data.IVConstants;

public class Checker {

	
	private static final int MIN = 0;
	private static final int MAX = 1;
	
	
	private long TOTAL_SPREADS;
	private long MATCHED_SPREADS;
	
	private IVs iVs;
	
	private int[][] ranges;
	private boolean[] HPs;
	
	
	private boolean printMatches;
	
	
	public Checker() {
		init();
		iVs = new IVs();
	}
	public Checker( IVs iVs ) {
		init();
		this.iVs = iVs;
	}
	
	
	private void init() {
		TOTAL_SPREADS   = 0;
		MATCHED_SPREADS = 0;
		printMatches = true;
		ranges = new int[6][2];
		for( int stat=0; stat<ranges.length; stat++ ) {
			ranges[stat][MIN] = com.data.IVConstants.MIN;
			ranges[stat][MAX] = com.data.IVConstants.MAX;
		}
		HPs = new boolean[16];
		for( int type=0; type<16; type++ ) {
			HPs[type] = true;
		}
	}
	
	// Run
	public void run() {
		TOTAL_SPREADS   = 0;
		MATCHED_SPREADS = 0;
		for( int n=0; n<com.data.IVConstants.FORCED_IV_ORDERS.length; n++ ) {
			for( int rndiv1=0; rndiv1<32; rndiv1++ ) {
				for( int rndiv2=0; rndiv2<32; rndiv2++ ) {
					for( int rndiv3=0; rndiv3<32; rndiv3++ ) {
						int[] rndivs = new int[] {rndiv1,rndiv2,rndiv3};
						int rndctr = 0;
						for( int st=0; st<6; st++ ) {
							if( com.data.IVConstants.FORCED_IV_ORDERS[n][st] ) {
								iVs.setIV(st,com.data.IVConstants.MAX);
							} else {
								iVs.setIV(st,rndivs[rndctr]);
								rndctr++;
							}
						}
						if( check() ) {
							MATCHED_SPREADS++;
							if( printMatches ) {
								System.out.println( iVs.IVs2String() );
							}
						}
						TOTAL_SPREADS++;
					}
				}
			}
		}
	}
	
	public void runNoFixedIVs() {
		TOTAL_SPREADS = 32*32*32*32*32*32;
		MATCHED_SPREADS=0;
		
		// TODO: Use nfor class instead of this.
		for( int i0=ranges[0][0]; i0<=ranges[0][1]; i0++ ) {
			for( int i1=ranges[1][0]; i1<=ranges[1][1]; i1++ ) {
				for( int i2=ranges[2][0]; i2<=ranges[2][1]; i2++ ) {
					for( int i3=ranges[3][0]; i3<=ranges[3][1]; i3++ ) {
						for( int i4=ranges[4][0]; i4<=ranges[4][1]; i4++ ) {
							for( int i5=ranges[5][0]; i5<=ranges[5][1]; i5++ ) {
								int[] ivs = new int[] {i0,i1,i2,i3,i4,i5};
								for( int stat=0; stat<6; stat++ ) {
									iVs.setIV( stat , ivs[stat] );
								}
								if( check() ) {
									MATCHED_SPREADS++;
									if( printMatches ) {
										System.out.println( iVs.IVs2String() );
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public long matches() {
		return MATCHED_SPREADS;
	}
	public long total() {
		return TOTAL_SPREADS;
	}
	
	// Setters
	// Print?
	public void setPrintMatches( boolean print ) {
		printMatches = print;
	}
	// Hidden Powers
	public void setHPs( boolean[] newHPs ) {
		assert( newHPs.length==16 );
		for( int type=0; type<newHPs.length; type++ ) {
			setHP( type , newHPs[type] );
		}
	}
	public void setHP( int type , boolean allow ) {
		assert( type>=0  &&  type<=15 );
		HPs[type] = allow;
	}
	// Ranges
	public void setRanges( int[][] newRanges ) {
		assert( newRanges.length==6 );
		for( int stat=0; stat<6; stat++ ) {
			setRange( stat , newRanges[stat] );
		}
	}
	public void setRange( int stat , int[] range ) {
		assert( stat>=0  &&  stat<=6 );
		assert( range.length==2 );
		assert( range[MIN]>=com.data.IVConstants.MIN  &&
				range[MIN]<=com.data.IVConstants.MAX  &&
				range[MAX]>=com.data.IVConstants.MIN  &&
				range[MAX]>=com.data.IVConstants.MAX  &&
				range[MIN]<=range[MAX] );
		ranges[stat][MIN] = range[MIN];
		ranges[stat][MAX] = range[MAX];
	}
	
	// Checks
	public boolean check() {
		return (checkHP() && checkStats());
	}
	
	// Hidden Powers
	private boolean checkHP() {
		return HPs[getHP()];
	}
	
	private int getHP() {
		int t = (int)Math.floor(
				(  com.data.IVConstants.hpOdd( iVs.getIV(com.data.IVConstants.HP ) ) +
				2 *com.data.IVConstants.hpOdd( iVs.getIV(com.data.IVConstants.ATT) ) +
				4 *com.data.IVConstants.hpOdd( iVs.getIV(com.data.IVConstants.DEF) ) +
				8 *com.data.IVConstants.hpOdd( iVs.getIV(com.data.IVConstants.SPE) ) +
				16*com.data.IVConstants.hpOdd( iVs.getIV(com.data.IVConstants.SPA) ) +
				32*com.data.IVConstants.hpOdd( iVs.getIV(com.data.IVConstants.SPD) ))*
				15/63
				);
		assert( t>=0  &&  t<=15 );
		return t;
	}
	
	// Ranges
	private boolean checkStats() {
		for( int stat=com.data.IVConstants.HP; stat<6; stat++ ) {
			if( !checkStat(stat) ) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkStat( int stat ) {
		assert( stat >= 0  &&  stat <= 6 );
		if( iVs.getIV(stat) < ranges[stat][MIN]  ||
				iVs.getIV(stat) > ranges[stat][MAX] ) {
			return false;
		}
		return true;
	}
	
}
