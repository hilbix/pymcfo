# PyMcFo

Jython module for Minecraft Forge

## Usage

- Locate the `.minecraft` folder.
  - Unix: `$HOME/.minecraft`
  - Windows: `%APPDATA%\.minecraft`
- Download jython from [jython.org](http://www.jython.org/downloads.html)
- Put the `jython*.jar` into directory `libraries`
- Download the `pymcfo*.jar` (see below)
- Place the `pymcfo*.jar` into directory `mods`
- Download (or write) python scripts
- Place python scripts into directory `pymcfo.run`
- Place python libraries into directory `pymcfo.lib`

Then:

- Launch Minecraft with Forge
- Module `pymcfo` reads all files from directorty `pymcfo`
- Each `*.py` script directory `pymcfo` will be started in it's own thread
- ~~Each subdirectory of `pymcfo` will be run as a package in it's own thread~~

There are examples which start a telnet like service for an external shell etc.


## Change code using Intellij Idea

> Sorry, I do not know how to do it in Eclipse

- Clone Repo from https://github.com/hilbix/pymcfo/
- Import your project into IntelliJ Idea
- Choose "build.gradle"
- ...
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

This recipe mainly follows this video:
https://www.youtube.com/watch?v=PfmlNiHonV0

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
