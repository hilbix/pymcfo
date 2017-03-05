package net.geht.mc.forge.pymcfo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.python.core.CompileMode;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.InteractiveInterpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Jython Code Executor
 * <p>
 * See http://blog.macuyiko.com/post/2015/rebuilding-our-jython-console-plugin-for-minecraft.html
 */
public class CodeExecutor extends InteractiveInterpreter implements Runnable
  {
  @SuppressWarnings("WeakerAccess")
  public static final String LIBS = Config.MINECRAFT_LIBS;

  @SuppressWarnings("WeakerAccess")
  protected PyCode code;

  public CodeExecutor() { super(null, newState()); }

  public CodeExecutor(String script) { super(null, newState()); set(script); }

  public CodeExecutor(File file) throws FileNotFoundException
    {
      super(null, newState());
      set(file);
    }

  public static PySystemState newState()
    {
      PySystemState state = new PySystemState();
      state.path.append(new PyString((new File(".")).getAbsolutePath()));
      state.path.append(new PyString((new File(Config.PYMCFO_LIBS)).getAbsolutePath()));
      try
        {
          for (File jar : FileUtils.listFiles(new File(LIBS),
                                              FileFilterUtils.suffixFileFilter(Config.JAR_EXTENSION, IOCase.INSENSITIVE),
                                              TrueFileFilter.TRUE))
            state.path.append(new PyString(jar.getAbsolutePath()));
        } catch (Exception e) {}
      return state;
    }

  @Override
  public void run() { exec(code); }

  public CodeExecutor set(String script) { return set(script, false); }

  public CodeExecutor set(String script, boolean interactive)
    {
      code = Py.compile_flags(script, "<script>", interactive ? CompileMode.single : CompileMode.eval, this.cflags);
      return this;
    }

  public CodeExecutor set(File file) throws java.io.FileNotFoundException
    {
      code = Py.compile_flags(new FileInputStream(file), file.getName(), CompileMode.exec, this.cflags);
      return this;
    }
  }
