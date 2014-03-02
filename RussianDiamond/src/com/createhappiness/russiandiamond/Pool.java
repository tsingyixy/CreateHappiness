package com.createhappiness.russiandiamond;

import java.util.ArrayList;
import java.util.List;
/**
 * 这个内存池主要用来循环使用游戏中创建的方块
 * 否则垃圾回收器会不断调用来回收被消掉的方块
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
