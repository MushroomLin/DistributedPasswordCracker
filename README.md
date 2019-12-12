# DistributedPasswordCracker
## DistributedPasswordCracker
* PasswordCracker dir contains file to be deployed on master node.
* CrackerWorker dir contains file to be deployed on worker node.
* Deployment details can be found in our final report.
* GeniRspec.xml is Repec file to initialize Gini nodes.
* Demo.mov is the demo video of our distributed password cracker.
* 655 Final Report is the final report
## Deployment instructions:
1. Use GeniRspec.xml to initialize the master node and three worker node
2. On master node, copy the jar file PasswordCracker/target/password-cracker-0.1.1.jar to master. Running master with "java -jar password-cracker-0.1.1.jar"
3. On worker node, copy all file under CrackerWorker dir to it. Change the HOST String to master IP in Worker.java file if using different nodes. Compile the Java code with "javac -d ./ *.java".
4. Open a browser, enter the IP address of the master node, in our case, it is
“128.112.170.32:8080”
5. Enter the hashed string you wish to crack in the web interface and click the submit button. We used “95ebc3c7b3b9f1d2c40fec14415d3cb8”, the hash version of zzzzz to test our application. The browser will get stucked since worker has not start yet.
6. On worker node, type “java passwordcracker.Worker” to start a worker. You may start multiple workers to run concurrently.
7. Wait for worker running. When password found, the browser will return the result.
 