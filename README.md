# wallethub
Application that can list IP's from a log file and append than blocked by rules on the args passed from command line.

#MAVEN App Build:
Build the jar file using "mvn clean compile assembly:single".

#Command Line Instructions:
Use a command line instruction like this "java -cp "parser.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100" to get a list of IP Address that will be blocked appended to database.

#Download jar file:
You can also use the already generated file "parse.jar" inside "app-resources/parse" folder, download an run the command line above to test.

#Structure
app-resources folder is the container of ready files and app to run.
aoo-source folder has the application source code.
