# Java / JavaDoc Formatter (prototype)

This plugin utilizes the JavaParser since it has the feature of "Lexical-Preseving Printing".

## Usage
To start formatting you need to select Code -> Reformat with JavaFormatter:

![img_3.png](img_3.png)

Since this is a Prototype Formatting Rules have to be applied in code. Add them to the "FormattingRules" class and call them in "applyFormattingRules". If there are no rules the code remains untouched.

Here is an example of the "addCommentsToPublicMethods" Rule.

before:

![img_5.png](img_5.png)

after:

![img_4.png](img_4.png)

You may use other API Classes to perform other IntelliJ plugin actions (e.g. automatically on save).

## Extensibility

The formatting aka. Lexical-Preseving Printing ist done in the class JavaFormatter.java. Feel free to adjust.

## GUI
A menu can be found under Tools -> Java Formatter Settings (see JavaFormatterSettingsAction class).

![img_1.png](img_1.png)

The menus under Settings -> Tools -> JavaFormatter does not work for some reason.

## ToDo
A menu and config file import in Clang-Format or Jalopy style may be added via IntelliJs API (see the Clang-Format, GoogleFormat, or even Jalopy Plugin for an example).

### Jalopy Comparison
In Jalopy formatting was registered on save:

![img_2.png](img_2.png)