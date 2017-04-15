package net.geht.mc.forge.pymcfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class DirectoryExecutor implements Runnable
  {
  private File dir;
  private Thread t;
  private URLClassLoader loader;

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
      dir = file;
      loader = new URLClassLoader(Config.PYMCFO_LIBS+"jython-standalone-2.7.0.jar");

      //System.setProperty("legacy.debugClassLoading", "true");
      t = new Thread(this);
      t.start();
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

                  Class<?> klass = loader.loadClass("net.geht.mc.forge.pymcfo.PythonCodeExecutor", true);
                  Constructor inter = klass.getDeclaredConstructor(File.class);
//                  //inter.setAccessible(true);
                  new ThreadExecutor((Runnable)inter.newInstance(py), py.getName());
                } catch (NoSuchMethodException e)
                {
                  System.out.println("NoSuchMethodException");
                  e.printStackTrace();
                } catch (InstantiationException e)
                {
                  System.out.println("InstantiationException");
                  e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                  System.out.println("IllegalAccessException");
                  e.printStackTrace();
                } catch (InvocationTargetException e)
                {
                  System.out.println("InvocationTargetException");
                  e.printStackTrace();
                } catch (ClassNotFoundException e)
                {
                  System.out.println("ClassNotFoundException");
                  e.printStackTrace();
                }
            }
          System.out.println("DONE");
        }
      System.out.println("");
    }
  }
