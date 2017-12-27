

#JAVA_PATH=C:/Program Files/Java/jdk1.7.0_51/bin
JAVA_PATH=$(JAVA_HOME)/bin
JFX_LIB=C:/Program Files/Java/jre7/lib/jfxrt.jar

CLASSPATH=$(JFX_LIB);bin;


build: bin/ReadCsv.class bin/FXController.class

bin/%.class: src/%.java
	"$(JAVA_PATH)/javac"  -cp "$(CLASSPATH)" -d "bin/" -encoding UTF-8 src/*.java


start: bin/FXController.class
	"$(JAVA_PATH)/java" -cp "$(CLASSPATH)" FXController