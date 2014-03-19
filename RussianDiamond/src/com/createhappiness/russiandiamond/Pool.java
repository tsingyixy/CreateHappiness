package com.createhappiness.russiandiamond;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于回收循环利用游戏中的方块的内存池
 * 
 * @author tsingyi
 * 
 * @param <T>
 */
class DiamondPool {
	private List<Diamond> allocated;
	private List<Diamond> unallocated;

	public Diamond push(Diamond object) {
		allocated.add(object);
		return object;
	}

	public DiamondPool() {
		allocated = new ArrayList<Diamond>();
		unallocated = new ArrayList<Diamond>();
	}

	public int size() {
		return unallocated.size();
	}

	public Diamond getInstance() {
		Diamond object = unallocated.remove(unallocated.size() - 1);
		allocated.add(object);
		return object;
	}

	public void Free(Diamond object) {
		unallocated.add(object);
	}
}
