package net.geht.mc.forge.pymcfo;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

@Mod(modid = pymcfo.MODID, version = pymcfo.VERSION)
public class pymcfo
  {
  public static final String MODID = Config.NAME;
  public static final String VERSION = Config.VERSION;

  public String canonicalPath(File file)
    {
      try
        {
          return file.getCanonicalPath();
        } catch (IOException e)
        {
          return file.getName();
        }
    }

  public String canonicalPath(String file)
    {
      return canonicalPath(new File(file));
    }

  @EventHandler
  public void init(FMLInitializationEvent event)
    {
      File   dir = new File(Config.PYMCFO_SCRIPTS);
      String src = canonicalPath(dir);
      String cwd = canonicalPath(".");

      System.out.println("");
      System.out.println("starting: "+MODID);
      System.out.println("CWD "+cwd);
      System.out.println("SRC "+src);

      if (!dir.exists() || !dir.isDirectory())
        System.out.println("SRC not found");
      else
        {
          for (File py : dir.listFiles(new CaseInsensitiveSuffixFileFilter(Config.PY_EXTENSION)))
            {
              try
                {
                  System.out.println("running "+canonicalPath(py));
                  new CodeExecutor(py);
                } catch (FileNotFoundException e)
                {
                  e.printStackTrace();
                }
            }
        }
      System.out.println("startup complete: "+MODID);
      System.out.println("");
    }
  }
