This is in an early stage.  Python scripts already can execute.
- Python scripts run in their own thread and Python interpreter each.
- They can probably read all Minecraft objects and elements.
- However I am not sure that they can properly alter things in Minecraft (due to the threading)

# PyMcFo

Minecraft Forge module to run Python scripts

## Usage

> This is under development, so not all features are implemented already!

- Locate the `.minecraft` folder.  Everything needs to be put below that:
  - Unix: `$HOME/.minecraft`
  - Windows: `%APPDATA%\.minecraft`
- Download suitable `pymcfo*.jar` from https://github.com/hilbix/pymcfo/tree/builds (or compile it yourself, it|s in `build/libs/` then)
- Place the `pymcfo*.jar` into dir `mods`
- Run Minecraft once. This created directories:
  - `pymcfo.ini` when Minecraft is started (before entering world)
  - `pymcfo.srv` on the Minecraft server side
  - `pymcfo.usr` on the Minecraft client side
  - `pymcfo.run` for chat (or server console) `/py` command
  - `pymcfo.lib` where Jython and optional additional libs can be placed
- Download Jython from [jython.org](http://www.jython.org/downloads.html)
  - Put `jython*.jar` in `pymcfo.lib`
- Download (or write) python scripts.  Those scripts are text files and end on `.py`
- Place Python scripts into the proper directory (ini/srv/usr/run)
- As always: Be careful, as insecure scripts may harm your server!

Then:

- Launch Minecraft with Forge
- Module `pymcfo` reads all files at the several stages
- Each script runs in it's own thread, so they all run in parallel.
- See `examples/` folder for examples


## Change code using Intellij Idea

> Sorry, I do not know how to do it in Eclipse, so only for IntelliJ

- Clone Repo from https://github.com/hilbix/pymcfo.git
- Import your project into IntelliJ Idea
- Choose "build.gradle"
- ??? (sorry, I have not yet checked what to do here!) ???
- Open sidebar "Gradle"
- expand "pymcfo" "Tasks" "forgegradle"
- rightclick "setupDecompWorkspace" select "Create"
- Run the newly created "Run Configuration"
- Refresh the Gradle projects
- Afterwards run "GenIntellijRuns"
- Restart IntelliJ
- Choose Menu "Run" and "Edit Configurations"
- Select "Applications" "Minecraft Client"
- Under "Configuration" "Use Classpath of Module" select "pymcfo_main"
- Do the same for "Minecraft Server"

Up to here this mainly follows this video:
https://www.youtube.com/watch?v=PfmlNiHonV0

Now more specific things (I do not know how to provide it a more easy way, sorry):

- Menu "File", "Settings" 
- "Editor", "Code Style", "Java"
- "Manage", "Import"
- "IntelliJ IDEA Code Style XML", "OK"
- Select `pymcfo/IdeaCodingStyle.xml`

Then:

- Menu "File", "Settings"
- "Project Settings", "Artifacts"
- "+", "Other"
- Name: `Examples`
- Output directory: `pymcfo/run/pymcfo`
- Enable "Include in project build"
- Under "Output Layout" click "+", "Directory Content"
- Select directory `pymcfo/examples/pymcfo`

If you want to automatically invoke this "Artifact" on launch, do this: 

- **Beware!** This overwrites data in `run/pymcfo/` without question!
- "Run/Debug Configuration", "Minecraft Client"
- "Before launch: Build Project, Activate tool Window"
- change "Build" into "Build Project" (remove and add the action).


> **Explained:**  In `pymcfo/examples/pymcfo` there are example Python scripts.
> The Artifact copies those into the `run` directory, such that they are found on Start of "Minecraft Client".
> You need to "Build" this artifact (Menu "Build", "Build Artifacts..") manually,
> or, when changed in the Run-Configuration, the files are always copied when lauched.)


## FAQ

- Why?

Because I was unable to find this for Forge.

- Why Forge?

Because this does not need a server as Forge may be loaded into clients as well.

- Why Python?

Jython easily integrates into Java.
It is scripting and easy to change interactively.
So no need to restart the server while changing things.

- Why no other Language?

I like Python, everything is readily available and was easy to integrate.

- Jython?

This is a Python interpreter written in Java.

- License?

For all the license files please have a look into directory `license/`.

For everything written by me (directories `src/` and `examples`), following License applies:

This Works is placed under the terms of the Copyright Less License,
see file COPYRIGHT.CLL.  USE AT OWN RISK, ABSOLUTELY NO WARRANTY.


# TODO

- more examples
- Telnet type service for interactive Python
- way to safely alter Minecraft objects from within Python
- Java wrappers for Python, such that this all can be done from Python
- start python scripts from server console
- start python scripts from chat
- Update to newer forge
