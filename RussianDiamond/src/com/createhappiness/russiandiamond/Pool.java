package com.createhappiness.russiandiamond;

import java.util.ArrayList;
import java.util.List;
/**
 * 用于回收循环利用游戏中的方块的内存池
 * @author tsingyi
 *
 * @param <T>
 */
class Pool<T> {
      private List<T> allocated;
      private List<T> unallocated;
      public void push(T object){
    	  allocated.add(object);
      }
      public Pool(){
    	  allocated = new ArrayList<T>();
    	  unallocated = new ArrayList<T>();
      }
      public T getInstance(){
           T object = unallocated.remove(unallocated.size() - 1);
           allocated.add(object);
          return object;
      }
      public void Free(T object){
    	  unallocated.add(object);
      }
}
