package net.geht.mc.forge.pymcfo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Get a lock on the main thread
 */
final public class Lock
  {
  private static ReentrantLock l = new ReentrantLock(true);

  private Lock() {}

  public static void put() { l.unlock(); }
  public static void get() { l.lock(); }
  public static boolean get(long timeout) throws InterruptedException { return l.tryLock(timeout, TimeUnit.MILLISECONDS); }
  }
