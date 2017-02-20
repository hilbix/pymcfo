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
  private String name;

  public ThreadExecutor(Runnable r, String name)
    {
      init(r, name);
    }

  protected void debug(String s)
    {
      System.out.println("[[["+this.name+" "+s+"]]]");
    }

  protected void init(Runnable r, String name)
    {
      this.name = name;
      debug("new");
      this.r = r;
      t = new Thread(this);
      n = list.add(this);
      t.start();
      debug("started");
    }

  public ThreadExecutor(Runnable r, String name, ThreadExecutorCallback cb)
    {
      this.cb = cb;
      init(r, name);
    }

  public void stop() throws InterruptedException
    {
      debug("stopped");
      t.interrupt();
      t.join();
    }

  @Override
  public void run()
    {
      try
        {
          debug("start");
          r.run();
          debug("end");
        } finally
        {
          list.del(n, this);
          if (cb!=null)
            cb.pymcfo_thread_executor_callback();
        }
      debug("exit");
    }
  }
