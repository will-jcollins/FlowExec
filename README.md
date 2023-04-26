# Flow-Exec
This program provides a graphical programming interface that allows the creation of programs through flow-chart diagrams. Flow-Exec allows flow-charts to be compiled to Python and Java.

## Requirements
Flow-Exec requires Java 18 and expected behaviour is not guaranteed on previous versions. Building Flow-Exec requires Maven 3.

## Instructions for use
### Bulding Flow-Exec
To use this program, it must be packaged into a jar file using Maven. Open a shell on the project's root directory and run the command:

``mvn package``

### Using Flow-Exec
To add an instruction to the flow-chart, drag one from the elements list on the left onto a placeholder on the chart view or an existing instruction. Similarly to add an expression to an expression drag one from the elements list on the left onto an expression placeholder or an existing expression. Existing instructions and expressions can be moved by dragging them.

To compile a flow-chart, select a target language on the toolbar then click compile.

#### Pseudocode mode
Pseudocode mode replaces inputs on the chart view with text to present a more compact chart. To activate it click show / hide pseudocode on the toolbar.

### Language notes
- Integers must correspond to a signed 32 bit integer
- Characters must belong to the UTF-16 character set
- Arrays must have uniform types and not be empty
- Outputs can not receive expressions of no type
