# Logic gate simulation by Romain DUHR based on Jeremie DEQUIDT and Bernard CARRE java course Polytech Lille

## Game Description
This archive includes a Java project for simulating logic gates. In the "circuits" directory, you will find all the source codes for the simulation. This allows the creation of circuits, logic gates, and probes. Additionally, you can save and restore a circuit (in a *.bin file) using the test classes. Finally, a graphical component has been added to the project, based on Bernard CARRE's code.

## Compilation linux

To compile successfully, you will need to install the JDK (Java Development Kit) tool.
```
sudo apt-get upgrade
sudo apt install openjdk-11-jdk
```
To compile the archive:
```
javac circuits/*.java
javac test/*.java
javac ihm/*.java
```
Do not pay attention to the errors. they are due to serialization, and for a project of this scale, it is not a problem as it is intended to protect the objects.

To run the test on circuits and saving:
```
java test.TestCircuits 
```

For the test of restoring a saved circuit:
```
java test.TestSerial circuit.bin
```

You can replace "circuit.bin" with the binary file you want to load.

To launch a graphical window based on a circuit:
```
java ihm.Testeur circuit
```
You can also replace "circuit" with the bin file to be loaded. Please note that this also considers the .png file.

