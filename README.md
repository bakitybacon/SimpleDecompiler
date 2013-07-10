SimpleDecompiler
================

My little Java Decompiler. Work in Progress, needs to actually get code from opcodes.
Not going to write much explanation, I have a lot of stuff running around in the Javadoc if you care. Currently, this can get all the fields, the access keywords (including static, volatile, etc.), package name, super classes, interfaces, method names and types, and Constructors. The last task to finish is the parsing of the opcodes.

Update: The parsing of opcodes is going fairly well.  I have written code for roughly 75% of the opcodes.  I have most of the basic syntax covered, but I really am having trouble with the grouping, (e.g. parentheses) and if/else, do/while, for, and most other key words in Java for loops and conditionals.  The switch is going to be a nightmare, as it has two opcodes that are ridiculously complex and are of variable length.
