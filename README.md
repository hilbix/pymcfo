> This is in an early stage.  Python scripts already can execute.
>
> - Python scripts run in their own thread and Python interpreter each.
> - They can probably read all Minecraft objects and elements.
> - However I am not sure that they can properly alter things in Minecraft (due to the threading)


# PyMcFo

Minecraft Forge module to run Python scripts


## Usage

> This is under development, so not all features are implemented for now!

- Locate the `.minecraft` folder.  Everything needs to be put below that:
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


## Code with IntelliJ Idea

> Sorry, I do not know how to do it in Eclipse, so only for IntelliJ
>
> Also IntelliJ has no support for `git submodules`, not even a good workaround.
> Hence you need `git` from the commandline.
>
> Sorry, I do not know how to add this to Gradle, as I am apparently incapable to comprehend the Gradle docs.
> Can, perhaps, somebody point me to something, written for an ordinary human being with no prior knowledge of Gradle?
> (To "Everything, you never wanted to know about Gradle, and never wanted to be forced to ask ever, too.") Thanks!

- Import your project into IntelliJ Idea
  - URL https://github.com/hilbix/pymcfo
  - Name: pymcfo
- In the "Event Log" there will be a link called "Import Gradle project".  Click it.
  - Enable "Create directories for empty content roots automatically"
  - If you haven't already done so, under "Global Gradle settings" set "Gradle VM options" `-Xmx4G -Xmn4G` (or higher.
    This assumes your machine has 8 GB of RAM or more.  It might work with machines of less RAM when set lower.)
- Open (the newly appeared) sidebar "Gradle" (usually on the right)
  - expand "pymcfo" "Tasks" `forgegradle`
  - right click `setupDecompWorkspace` select "Create"
    - check "Single instance only" and click "OK"
- Run the newly created "Run Configuration" (Shift+F10)
  - You can ignore the message about "Unindexed remote maven repositories found."
- Wait until it finishes (you see ith in "Run", Alt+4)
  - In the meanwhile you can "Configure" the found "Python Framework" (there is nothing to it)
- Refresh the Gradle projects (Button "Refresh all Gradle projects" in sidebar "Gradle projects")
- Run `genIntellijRuns` (a bit above `setupDecompWorkspace`)
  - if you like you can create this, too
  - This creates the empty "run" directory (in case you wonder because you forgot this step)
- Answer "Yes" to "Would you like to reload Project"
  - Under older IntelliJ you might need a restart instead
- Choose Menu "Run" and "Edit Configurations"
  - Expand "Applications" "Minecraft Client"
  - Under "Configuration" "Use Classpath of Module" select "pymcfo_main"
  - Click "Apply"
  - Do the same for "Minecraft Server"

Up to here this mainly follows this video:
https://www.youtube.com/watch?v=PfmlNiHonV0

Now more specific things (I do not know how to provide it a more easy way, sorry):

- Menu "File", "Settings" (Ctrl+Alt+S)
  - "Editor", "Code Style", "Java"
    - "Manage", "Import"
      - "IntelliJ IDEA Code Style XML", "OK"
      - Select `IdeaCodingStyle.xml`, "OK" (if you are puzzled: It is in the folder of the newly imported project `pymcfo`).
      - From "Project" to "Project" is ok, so just click "OK"

Then:

- Menu "File", "Project Structure" (Ctrl+Alt+Shift+S)
  - "Artifacts", "+", "Other"
    - Name: `Examples`
    - Output directory: `pymcfo/run/pymcfo`
      - Under Windows this is `pymcfo\run\pymcfo` of course
    - Enable "Include in project build"
    - Under "Output Layout" click "+", "Directory Content"
      - Select directory `pymcfo/examples`
- If you want to automatically invoke this "Artifact" on launch, do this:
  - **Beware!** This overwrites data in `run/pymcfo/` without question!
  - Menu "Run" "Edit Configurations"
    - "Minecraft Client", "Configuration" under "Before launch: Build Project, Activate tool Window"
      - change "Build" into "Build Project"
      - This is done by adding action "Build Project"
      - and then removing "Build" (as it is redundant)
    - Do the same for "Minecraft Server"

> **Explained:**  Below `pymcfo/examples` there are example Python scripts.
> The Artifact copies those into the `run` directory, such that they are found on Start of "Minecraft Client".
> You need to "Build" this artifact (Menu "Build", "Build Artifacts..") manually,
> or, when changed in the Run-Configuration, the files are always copied when launched.)

Afterwards there still is something missing.  IntelliJ Idea is incapable to handle git submodules today.
Enter the the project directory (i. E. open the "Terminal" of IntelliJ Idea) and run following `git` command:

    git submodule update --init

> Under Windows you need to install [`git` for Windows](https://git-scm.com/download/win) or similar.

Please note that, even that this already was long, there still are some missing steps.

> I really have no idea why this is so complicated.
> Why isn't a simple checkout enough?


## Code with Linux

In future I want that this is the full process to get started under Linux:

    git clone --recursive https://github.com/hilbix/pymcfo.git
    cd pymcfo
    make

And I think this already is much too complex.

> What I would like to see is
>
> - Navigate to https://github.com/hilbix/pymcfo to see this README.
> - In this README there is a simple link.
> - You can click on it to start the building process.
>   - No complex questions asked except, perhaps, a local target folder.
>   - No additional installations or updates of your environment needed.
>   - No additional configuration needed, as everything is readily configured.
>   - No additional explanations needed, as it just works.
> - This link must support, at least, Linux, Windows and MacOS.


## FAQ

- Update Forge?

> I did not find any better solution than that yet:
>
> - Go to [https://files.minecraftforge.net/](https://files.minecraftforge.net/)
> - Find the Forge version you want to use
> - Hover over to "MCP" and click the `(i)` button.
> - Open the ZIP with 7-zip or similar.
> - Find the file `build.gradle` in the ZIP
> - Show it as text (in 7-zip just press `F4`)
> - Scroll down to the `minecraft {` block.
> - Open your local project file `build.gradle`.
> - Update the `minecraft {` block of your local file accordingly
> - Be sure to replace `version` and `mapping`, as they belong together!
>
> Afterwards run Gradle task `SetupDecompWorkspace` and follow the yellow brick road.


- Why?

> Because I was unable to find Python for Forge.


- Why Forge?

> Because Forge runs on Servers as well as on standalone Clients.


- Why Python?

> Jython easily integrates into Java.
> It is scripting and easy to change interactively.
> So no need to restart Minecraft while changing scripts.


- Why no other Language?

> I like Python, everything is readily available and was easy to integrate.


- Jython?

> This is a Python interpreter written in Java.


- PyMcFo?

> Sorry, I did not find a catchy name.
>
> If you have a better idea,
> and allow me to use this freely (see License),
> I am all ears.


- Logo?

> Sorry, I am not into graphics.
>
> If you have a nice logo (please SVG),
> and allow me to use this freely (see License),
> I am all ears.


- `git` submodules?

> Some people think, `git submodule` is evil.  Many alternatives were developed, like `gitslave`, `git subtree`, `git subrepo` and `git monorepo`.
> However, git submodules offer one unique feature:  You can **support rewriting of history without breaking it**!
>
> Thanks to this unique feature, you can work against the problem of ever growing `git` repo data.
>
> For example, in future I can remove old builds or old variants of the big `libs/jython-*.jar` without trouble.3
> And I can split the `git` repo into a small main repo an a big overwhelming archive repo.
> You then can `git fetch` the archive into your local repo to bring back archived SHAs temporarily.
> The main repo still    stays fully functional and operable, without any holes nor any broken parts.
>
> Without `git submodule` you need to rewrite history by rebasing to be able to remove files from commits.


- License?

> For all the license files please have a look into directory `license/`.
>
> For everything written by me (directories `src/` and `examples`), following License applies:
>
>     This Works is placed under the terms of the Copyright Less License,
>     see file COPYRIGHT.CLL.  USE AT OWN RISK, ABSOLUTELY NO WARRANTY.`
>
> You can add your license,     but it must be a free license, with free as in
free beer and free speech and, if possible, free baby.


- Contact?

> As I do not read mails, **open an issue on Github or send a pull request**.
>
> Sorry, my pager [https://hydra.geht.net/pager.php](https://hydra.geht.net/pager.php) is currently down.
> But if it is back up you can use it, of course.


# TODO

> - more examples
> - Telnet type service for interactive Python
> - way to safely alter Minecraft objects from within Python
> - Java wrappers for Python, such that this all can be done from Python
> - start python scripts from server console
> - start python scripts from chat


# Unknowns

> Perhaps somebody else can enlighten me.
>
> - How to edit Gradle such, that build copies `example` subdirectories to `run` output (probably only in IntelliJ Idea?  Currently I add build artifacts for that manually which is not nice.)
> - How to edit Gradle such, that build creates the empty directory `run`.  As if it is missing (`git clean -x -f -d`) build fails.
> - A short, really easy to understand, comprehensive manual of Gradle which a complete coverage of all the gory details I might want to know about (like the previous points).
