# How to compile on server 101

connect to ssh via using X11 forwarding:

```
ssh -X p<Matrikelnummer>@g0.complang.tuwien.ac.at
```

then switch to directory and make sure we are up-to-date:

```
cd ppH5 && git pull
```

then switch to Aufgabe1-3:

```
cd Aufgabe9
```

compile files into bin folder:

```
javac -d bin *.java
```

run Arena

```
java -cp bin Arena <width> <height> <numberOfAnts>
```



