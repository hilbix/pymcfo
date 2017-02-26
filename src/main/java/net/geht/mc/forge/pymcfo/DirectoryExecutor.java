package net.geht.mc.forge.pymcfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DirectoryExecutor implements Runnable
  {
  private File dir;
  private Thread t;

  public static String canonicalPath(File file)
    {
      try
        {
          return file.getCanonicalPath();
        } catch (IOException e)
        {
          return file.getName();
        }
    }

  public static String canonicalPath(String file)
    {
      return canonicalPath(new File(file));
    }

  public DirectoryExecutor(String dir) { init(dir); }

  public DirectoryExecutor(File dir) { init(dir); }

  private void init(String dir) { init(new File(dir)); }

  private void init(File file)
    {
      this.dir = dir;

      t = new Thread(this);
      t.start();  // run this
    }

  public DirectoryExecutor join() throws InterruptedException
    {
      t.join();
      return this;
    }

  @Override
  public void run()
    {
      String src = canonicalPath(dir);

      System.out.println("");
      System.out.println("CWD "+canonicalPath("."));
      System.out.println("SRC "+src);

      if (!dir.exists() || !dir.isDirectory())
        System.out.println("SRC not found");
      else
        {
          for (File py : dir.listFiles(new CaseInsensitiveSuffixFileFilter(Config.PY_EXTENSION)))
            {
              try
                {
                  System.out.println("EXEC "+canonicalPath(py));
                  new ThreadExecutor(new CodeExecutor(py), py.getName());
                } catch (FileNotFoundException e)
                {
                  e.printStackTrace();
                }
            }
          System.out.println("DONE");
        }
      System.out.println("");
    }
  }
