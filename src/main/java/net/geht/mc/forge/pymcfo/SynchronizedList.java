package net.geht.mc.forge.pymcfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Thead synchronized list type (not list compatible for now)
 * <p>
 * - You cannot replace elements
 * - You cannot store NULL
 * <p>
 * This is to solve two ArrayList problems:
 * - It is not thread synchronized
 * - Index position of the added element is not known after add
 * <p>
 * To allow fast operation, it is necessary not to search for array entries when removing them.
 * This is archived here by never removing elements within the array, instead they are NULLed.
 * <p>
 * You need to give the element to remove with it's index, to make sure, to
 */
public class SynchronizedList<T>
  {
  private List<T> list = new ArrayList<T>();

  /**
   * Add element to the list
   *
   * @param e element to add
   * @return stable array index (int)
   */
  public synchronized int add(T e)
    {
      int n;

      if (e==null) return -1;

      n = list.size();
      list.add(n, e);
      assert list.get(n)==e;
      return n;
    }

  /**
   * Remove element from the list
   * Authorization is by giving the index returned by add() again.
   *
   * @param n index returned by add
   * @param e element added by add
   */
  public synchronized void del(int n, T e)
    {
      if (n<0 || list.get(n)!=e) return;
      list.set(n, null);
      while (n==list.size()-1)
        {
          list.remove(n);
          if (--n<0 || list.get(n)!=null)
            break;
        }
    }
  }
