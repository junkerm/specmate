package com.specmate.bdd;

import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class DoubleMap {
	
	//mapping from index to (variable, condition) pair
	private Map<Integer, Pair<String, String>> index2pair;
	
	//mapping from (variable, condition) pair to index
	private Map<Pair<String, String>, Integer> pair2index;
	
	public void add(Pair<String, String> pair, int index){
		index2pair.put(index, pair);
		pair2index.put(pair, index);
	}
	
	/*
	 * True if the given pair is already included in the DoubleMap.
	 */
	public boolean hasPair(Pair<String, String> pair){
		if(pair2index.containsKey(pair)){
			return true;
		}
		return false;
	}
	
	/*
	 * Returns the index for the given pair.
	 */
	public int getIndexFor(Pair<String, String> pair){
		return pair2index.get(pair);
	}
	
	/*
	 * Returns the pair for the given index.
	 */
	public Pair<String, String> getPairFor(int index){
		return index2pair.get(index);
	}
	
	@Override
	public String toString(){
		return index2pair.toString();
	}
	
	

}
