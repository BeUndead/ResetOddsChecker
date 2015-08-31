package com.data;

public class IVConstants {

	public static final int HP  = 0;
	public static final int ATT = 1;
	public static final int DEF = 2;
	public static final int SPA = 3;
	public static final int SPD = 4;
	public static final int SPE = 5;

	public static final int MIN = 0;
	public static final int MAX = 31;
	
	private static final boolean T = true;
	private static final boolean F = false;
	
	public static final boolean[][] FORCED_IV_ORDERS = {
		{T,T,T,F,F,F},
		{T,T,F,T,F,F},
		{T,T,F,F,T,F},
		{T,T,F,F,F,T},
		{T,F,T,T,F,F},
		{T,F,T,F,T,F},
		{T,F,T,F,F,T},
		{T,F,F,T,T,F},
		{T,F,F,T,F,T},
		{T,F,F,F,T,T},
		{F,T,T,T,F,F},
		{F,T,T,F,T,F},
		{F,T,T,F,F,T},
		{F,T,F,T,T,F},
		{F,T,F,T,F,T},
		{F,T,F,F,T,T},
		{F,F,T,T,T,F},
		{F,F,T,T,F,T},
		{F,F,T,F,T,T},
		{F,F,F,T,T,T}
	};
	
	public static int hpOdd( int n ) {
		return n&1;
	}
	
	public static final int HP_FIGHTING = 0;
	public static final int HP_FLYING   = 1;
	public static final int HP_POISON   = 2;
	public static final int HP_GROUND   = 3;
	public static final int HP_ROCK     = 4;
	public static final int HP_BUG      = 5;
	public static final int HP_GHOST    = 6;
	public static final int HP_STEEL    = 7;
	public static final int HP_FIRE     = 8;
	public static final int HP_WATER    = 9;
	public static final int HP_GRASS    = 10;
	public static final int HP_ELECTRIC = 11;
	public static final int HP_PSYCHIC  = 12;
	public static final int HP_ICE      = 13;
	public static final int HP_DRAGON   = 14;
	public static final int HP_DARK     = 15;
	
	public static final double SYNC_NATURE = 13/25;
	public static final double NATURE = 1/25;
	public static final double ADDITIONAL_SYNC_NATURE = 1/50;
	
}
