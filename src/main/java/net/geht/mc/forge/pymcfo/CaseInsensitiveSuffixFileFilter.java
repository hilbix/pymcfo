package net.geht.mc.forge.pymcfo;

import java.io.File;
import java.io.FileFilter;

/**
 * FileFilter for Files with given extension (i. E. ".ext")
 */
public class CaseInsensitiveSuffixFileFilter implements FileFilter
  {
  private String suffix;

  public CaseInsensitiveSuffixFileFilter(String extension)
    {
      suffix = extension;
    }

  @Override
  public boolean accept(File file)
    {
      return file.getName().toLowerCase().endsWith(suffix) && !file.isDirectory();
    }
  }
