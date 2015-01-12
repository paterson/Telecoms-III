LIBS = ./libs
CLASSES = $(wildcard *.java)
JC = javac
JFLAGS = -cp $(LIBS)/tcdlib.jar -d ./
RUNFLAGS = -cp $(LIBS)/tcdlib.jar:./

.SUFFIXES: .java .class
.PHONY: all classes clean run

build:
	$(JC) $(JFLAGS) *.java

clean:
	rm -rf ./cs

run:
	java $(RUNFLAGS) cs.tcd.ie.Server &
	java $(RUNFLAGS) cs.tcd.ie.Client 50000 &
	java $(RUNFLAGS) cs.tcd.ie.Client 50002 &