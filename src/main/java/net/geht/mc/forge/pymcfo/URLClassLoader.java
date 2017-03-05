package net.geht.mc.forge.pymcfo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class URLClassLoader extends java.net.URLClassLoader
  {
  // https://analyzejava.wordpress.com/2014/09/25/java-classloader-namespaces/
  // http://stackoverflow.com/questions/1873916/is-it-possible-to-have-the-system-classloader-load-class-files-specified-at-run
  // http://stackoverflow.com/questions/4095976/how-to-put-custom-classloader-to-use
  // http://stackoverflow.com/questions/25191812/adding-folders-to-java-classpath-at-runtime
  // http://stackoverflow.com/questions/7884393/can-a-directory-be-added-to-the-class-path-at-runtime
  public URLClassLoader(String dir)
    {
      super(makeURL(dir), URLClassLoader.class.getClassLoader());
      System.out.println("adding classpath "+dir);
    }

  private static URL[] makeURL(String dir)
    {
      try
        {
          return new URL[]{new File(dir).toURI().toURL()};
        } catch (MalformedURLException e)
        {
          e.printStackTrace();
        }
      return null;
    }

  @Override
  public final Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
    {
      System.out.println("loadClass: "+name);
      return super.loadClass(name, resolve);
    }

  @Override
  protected Class<?> findClass(final String name) throws ClassNotFoundException
    {
      System.out.println("findClass: "+name);
      return super.findClass(name);
    }
  }
