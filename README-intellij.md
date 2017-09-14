# Use IntelliJ Idea

> Sorry, I do not know how to do it in Eclipse, so only for IntelliJ
>
> Also IntelliJ seems to have no good support for `git submodules`.
> Hence you probably need `git` from the commandline.
>
> Also I do not know how to add this to Gradle, as I am apparently incapable to comprehend the Gradle docs.
> Can, perhaps, somebody point me to something, written for an ordinary human being with no prior knowledge of Gradle?
> (To "Everything, you never wanted to know about Gradle, and never wanted to be forced to ask ever, too.") Thanks!
>
> Also the following probably is very out of sync, incomplete, wrong and nobody understands it, including me.
> This is because I think this process is far from being something, you can use.
> It should be that you just load IntelliJ, checkout the Repo and .. go!
> But sorry, I was not able to find a way how to archive that.

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
    - I even have the slightest idea what this wants to tell me and how to get rid such messages.
- Wait until it finishes (you see ith in "Run", Alt+4)
  - In the meanwhile you can "Configure" the found "Python Framework" (there is nothing to it)
- Refresh the Gradle projects (Button "Refresh all Gradle projects" in sidebar "Gradle projects")
- Run `genIntellijRuns` (a bit above `setupDecompWorkspace`)
  - if you like you can create this, too
  - This creates the empty "run" directory (in case you wonder because you forgot this step)
- Answer "Yes" to "Would you like to reload Project"
  - Restart IntelliJ if this question is not shown
- Choose Menu "Run" and "Edit Configurations"
  - Expand "Applications" "Minecraft Client"
  - Under "Configuration" "Use Classpath of Module" select "pymcfo_main"
  - Click "Apply"
  - Do the same for "Minecraft Server"

Up to here this mainly followed this video:
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

Afterwards there still is something missing.  IntelliJ Idea is incapable to handle `git submodule`s today.
Enter the the project directory (i. E. open the "Terminal" of IntelliJ Idea) and run following `git` command:

    git submodule update --init

> Under Windows you need to install [`git` for Windows](https://git-scm.com/download/win) or similar.
> Or use the new Linux ABI for this, which I highly recommend.

Please note that, even that this already was long, there still are some missing steps.

> I really have no idea why this is so complicated.
> Why isn't a simple checkout enough?
