# tamk-ohsyte-2025
TAMK / Tietotekniikka / Ohjelmoinnin syventävät tekniikat, kevät 2025

## Java versions

* Marco Behler, [Java Versions and Features](https://www.marcobehler.com/guides/a-guide-to-java-versions-and-features)

## Random number generation

* [Generating Random Numbers Between 1 and 100 in Java](https://dev.to/meenakshi052003/generating-random-numbers-between-1-and-100-in-java-i3b)

## Time and date handling

Use the `java.time` package and its subpackages.

## Setting an environment variable

In Unix / Linux, set the value in your shell's configuration file.
Add the line

    export BIRTHDATE=1989-11-06

to your `.bashrc` or equivalent, and then source it:

    source .bashrc
    
Or, set it just for the next run:

    BIRTHDATE=1989-11-06 java EnvTest.java BIRTHDATE

In Windows, use the `set` command in Command Prompt:

    set BIRTHDATE=1989-11-06
    
To set the environment variable permanently, use the `setx` command:

    setx BIRTHDATE 1989-11-06

You may still need to restart Command Prompt before the setting
takes effect (?).

Check the setting with the `echo` command:

    echo %BIRTHDATE%

in Windows, or 

    echo $BIRTHDATE

in Linux and macOS.

## JShell

* [JShell Tutorial](https://cr.openjdk.org/~rfield/tutorial/JShellTutorial.html)

## Visual Studio Code

If you don't want/need to see the names of
method arguments etc. in the editor, then do this:

With a Java source code file open, select "Java"
in the bottom right of the status bar, then
"Configure 'Java' language settings", search for
"editor.inlayHints.enabled", set to "off".

This should result in the following in your `settings.json` file:

    {
        "[java]": {
            "editor.inlayHints.enabled": "off",
        }
    }
