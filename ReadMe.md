# JavaFormatter (PROTOTYPE ONLY)

This plugin utilizes the JavaParser since it has the feature of "Lexical-Preseving Printing".

## Usage
To start formatting your code you need to press Ctrl+Alt+L (Windows/Linux) or Cmd+Option+L (Mac), otherwise nothing happens. 
![img.png](img.png)

For an understanding how it works, see `public class CustomFormattingModelBuilder implements FormattingModelBuilder`.

You may use other API Classes to perform other actions (e.g. automatically).

## Extensibility

The formatting aka. Lexical-Preseving Printing ist done in the class JavaFormatter.java. Feel free to adjust.

## GUI
A menu can be found under Tools -> Java Formatter Settings (see JavaFormatterSettingsAction class).
![img_1.png](img_1.png)

## ToDo
A Menu and Config File Import in Clang-Format or Jalopy style may be added via IntelliJs API (see the Clang-Format, GoogleFormat, or even Jalopy Plugin for an example).