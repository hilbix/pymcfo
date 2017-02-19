package net.geht.mc.forge.pymcfo;

import org.python.core.*;
import org.python.util.InteractiveInterpreter;

import java.io.File;
import java.io.FileInputStream;

/**
 * Jython Code Executor
 *
 * See http://blog.macuyiko.com/post/2015/rebuilding-our-jython-console-plugin-for-minecraft.html
 */
public class CodeExecutor extends InteractiveInterpreter implements Runnable {

    @SuppressWarnings("WeakerAccess")
    public static final String LIBS = "libraries/";

    @SuppressWarnings("WeakerAccess")
    protected PyCode code;

    @Override
    public void run() {
        exec(code);
    }

    public CodeExecutor() { super(null, newState()); }

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

    public static PySystemState newState()
    {
        PySystemState state = new PySystemState();
        state.path.append(new PyString((new File(".")).getAbsolutePath()));
        state.path.append(new PyString((new File("pymcfo.lib/")).getAbsolutePath()));
        try {
            for (File lib : (new File(LIBS)).listFiles())
            {
                String name = lib.getName();
                if (name.endsWith(".jar"))
                    state.path.append(new PyString(new File(LIBS + name).getAbsolutePath()));
            }
        } catch (Exception e) {
        }
        return state;
    }
}
