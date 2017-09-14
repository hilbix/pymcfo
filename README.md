> This is in an early stage.  Python scripts already can execute.
>
> - Python scripts run in their own thread and Python interpreter each.
> - They can probably read all Minecraft objects and elements.
> - However I am not sure that they can properly alter things in Minecraft (due to the threading)


# PyMcFo

Minecraft Forge module to run Python scripts


## Install mod

> This is under development, so not all features are implemented for now!

- Locate the `.minecraft` folder.  Everything needs to be put below that:
  - Mac: `~/Library/Application Support/minecraft` (use Finder :: Go :: Folder)
  - Unix: `$HOME/.minecraft`
  - Windows: `%APPDATA%\.minecraft`
- Download suitable `pymcfo*.jar` from ["build" branch](https://github.com/hilbix/pymcfo/tree/builds)
  (or compile it yourself, it's in `build/libs/` then)
- Place the `pymcfo*.jar` into dir `mods`
- Run Minecraft once. This creates directory `pymcfo` with following subdirs:
  - `ini` when Minecraft is started (before entering world)
  - `srv` on the Minecraft server side
  - `usr` on the Minecraft client side
  - `run` for chat (or server console) `/py` command
  - `lib` where Jython and optional additional libs can be placed
- Download Jython from [jython.org](http://www.jython.org/downloads.html)
  - Put `jython*.jar` in dir `mods`.
  - ~~Put `jython*.jar` in dir `lib` below `pymcfo`.~~ (Does not work for unknown reason)
  - The copy I use is on ["libs" branch](https://github.com/hilbix/pymcfo/tree/libs)
- Download (or write) Python scripts.  Those scripts are text files and end on `.py`
- Place Python scripts into the proper directory (`ini`/`srv`/`usr`/`run`)
- As always: Be careful, as insecure scripts (those from unknown sources) can harm your data,
  as scripts can access everything on your computer.

Then:

- Launch Minecraft with Forge
- Module `pymcfo` reads all files at the proper stages
- Please keep in mind, each script runs in it's own thread, so they all run in parallel.
- See `examples/` folder for examples


## Use source

> On OS-X, you probably need some Linux command compatibility:
> 
>     git clone --recursive https://github.com/hilbix/macshim.git
>     cd macshim
>     make
>     make install
>
> Make sure, `$HOME/bin/` is in your `PATH`.

This should do, under Linux, OS-X and Ubuntu/Windows (Windows 10 creators update and above):

    git clone --recursive https://github.com/hilbix/pymcfo.git
    cd pymcfo
    make

And I think this already is much too complex.

> What I would like to see is just a link which can be clicked to build everything.
> - No complex questions asked except, perhaps, a local target folder.
> - No additional installations or updates of your environment needed.
> - No additional configuration needed, as everything is readily configured.
> - No additional explanations needed, as it just works.
> - And this must support, at least, Linux, Windows and OS-X.

Notes:

- After `make`, the module source is linked to ../pymcfo.jar
- The module needs the corresponding Forge version to run.


## FAQ

Update Forge?

- I did not find any better solution than that yet:

  - Go to [https://files.minecraftforge.net/](https://files.minecraftforge.net/)
  - Find the Forge version you want to use
  - Hover over to "MCP" and click the `(i)` button.
  - Open the ZIP with 7-zip or similar.
  - Find the file `build.gradle` in the ZIP
  - Show it as text (in 7-zip just press `F4`)
  - Scroll down to the `minecraft {` block.
  - Open your local project file `build.gradle`.
  - Update the `minecraft {` block of your local file accordingly
  - Be sure to replace `version` and `mappings`, as they belong together!

  Afterwards run Gradle task `SetupDecompWorkspace` and follow the yellow brick road.
  Or just run: `make`


Why?

- Because I was unable to find Python for Forge.


Why Forge?

- Because Forge runs on Servers as well as on standalone Clients.


Why Python?

- Jython easily integrates into Java.
- It is scripting and easy to change interactively.
- So no need to restart Minecraft while changing scripts.


Why no other Language?

- Forge includes Scala already, but I am not used to Scala, sorry.
- I like Python, everything is readily available and was easy to integrate.


Jython?

- This is a Python interpreter written in Java.


PyMcFo?

- Sorry, I did not find a catchy name.

  If you have a better idea,
  and allow me to use this freely (see License),
  I am all ears.


Logo?

- Sorry, I am not into graphics.

  If you have a nice logo (please SVG),
  and allow me to use this freely (see License),
  I am all ears.


`git` submodules?

- Some people think, `git submodule` is evil.  Many alternatives were developed, like `gitslave`, `git subtree`, `git subrepo` and `git monorepo`.
  However, git submodules offer one unique feature:  You can **support rewriting of history without breaking it**!

  Thanks to this unique feature, you can work against the problem of ever growing `git` repo data.

  For example, in future I can remove old builds or old variants of the big `libs/jython-*.jar` without trouble.3
  And I can split the `git` repo into a small main repo an a big overwhelming archive repo.
  You then can `git fetch` the archive into your local repo to bring back archived SHAs temporarily.
  The main repo still    stays fully functional and operable, without any holes nor any broken parts.

  Without `git submodule` you need to rewrite history by rebasing to be able to remove files from commits.


License?

- For all the license files please have a look into directory `license/`.
- For everything written by me (directories `src/` and `examples/`), following License applies:
 
      This Works is placed under the terms of the Copyright Less License,
      see file COPYRIGHT.CLL.  USE AT OWN RISK, ABSOLUTELY NO WARRANTY.`

  The CLL is a free license, with free as in free beer and free speech and free baby.


Contact?

- As I do not read mails, **open an issue on Github or send a pull request**.
- Sorry, my pager [https://hydra.geht.net/pager.php](https://hydra.geht.net/pager.php) is currently down.
  But as soon as it is up again you can use it, of course.


# TODO

- more examples
  - Telnet type service for interactive Python
- way to safely alter Minecraft objects from within Python
  - Java wrappers for Python, such that this all can be done from Python
- start python scripts from server console
- start python scripts from chat


# Unknowns

Perhaps somebody else can enlighten me with Gradle:

- How to edit Gradle such, that build copies `examples/` subdirectories to `run/` output (probably only in IntelliJ Idea?  Currently I add build artifacts for that manually which is not nice.)
- How to edit Gradle such, that build creates the empty directory `run/`.  As if it is missing (`git clean -x -f -d`) build fails in IntelliJ.
- A short, really easy to understand, comprehensive manual of Gradle which a complete coverage of all the gory details I might want to know about (like the previous points).

