package net.geht.mc.forge.pymcfo;

/**
 * Register some runnables
 */

public class ThreadExecutor implements Runnable
  {
  protected static SynchronizedList<ThreadExecutor> list = new SynchronizedList<ThreadExecutor>();

  protected Thread t;
  protected int n;
  protected Runnable r;
  protected ThreadExecutorCallback cb = null;

  public ThreadExecutor(Runnable r)
    {
      init(r);
    }

  protected void init(Runnable r)
    {
      this.r = r;
      t = new Thread(this);
      n = list.add(this);
      t.start();
    }

  public ThreadExecutor(Runnable r, ThreadExecutorCallback cb)
    {
      this.cb = cb;
      init(r);
    }

  public void stop() throws InterruptedException
    {
      t.interrupt();
      t.join();
    }

  @Override
  public void run()
    {
      try
        {
          r.run();
        } finally
        {
          list.del(n, this);
          if (cb!=null)
            cb.pymcfo_thread_executor_callback();
        }
    }
  }
