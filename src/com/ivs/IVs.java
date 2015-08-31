package com.ivs;

import com.data.IVConstants;

public class IVs {

	private int[] iVs;
	
	public IVs() {
		init();
	}
	public IVs( int i0,
			int i1,
			int i2,
			int i3,
			int i4,
			int i5 ) {
		setiVs( new int[] {i0,i1,i2,i3,i4,i5} );
	}
	
	private void init() {
		iVs = new int[6];
	}
	
	
	public String IVs2String() {
		return iVs[0]+"."+iVs[1]+"."+iVs[2]+"."+iVs[3]+"."+iVs[4]+"."+iVs[5];
	}
	

	public int getIV( int stat ) {
		return iVs[stat];
	}
	public int[] getiVs() {
		return iVs;
	}
	
	public void setIV( int stat , int iV ) {
		iVs[stat] = iV;
		assert( isPossible() );
	}
	
	public void setiVs( int[] newiVs ) {
		if( newiVs.length != iVs.length ) {
			throw new IllegalArgumentException("Too many iVs received");
		}
		for( int i=0; i<iVs.length; i++ ) {
			iVs[i] = newiVs[i];
		}
		assert( isPossible() );
	}
	
	private boolean isPossible() {
		for( int i=0; i<iVs.length; i++ ) {
			if( iVs[i] < IVConstants.MIN 
					|| iVs[i] > IVConstants.MAX ) {
				return false;
			}
		}
		return true;
	}
}
