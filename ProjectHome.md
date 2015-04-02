# Simple GWT application framework #
  * Constructs "Application" classes to HashMap<name,Class> and display based on history name key
  * Try to be less boilerplate than MVC implementations
  * Default application mimics GWT "Web Application Starter Project" example
  * Simple "SubSystem" class to carry Handlers to Applications.
  * Code split example

## Install ##
  1. Import Existing Projects into Workspace
  1. Copy latest gwt-servlet.jar to war/WEB-INF/lib/

## Adding "Application" pages ##
  * Create class which extends AppAbstract, add static "act" key name and introduce in AppController inside constructor.
  * As AppAbstract extends Composite, need to use initWidget() to attach Widget you like to use.


