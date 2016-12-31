# Please specify paths

input=/Users/dmehta/secretwork/projectwork/input
preprocessOutput=/Users/dmehta/secretwork/projectwork/preprocessOutput
predictOutput=/Users/dmehta/secretwork/projectwork/predictOutput
regionPath=/Users/dmehta/secretwork/projectwork/region
modelPath=/Users/dmehta/secretwork/projectwork/model


build:
	cd simple-bird-classifier && mvn clean install
	zip -d simple-bird-classifier/target/meera-kairavi-preprocess.jar META-INF/LICENSE
	cd simpleproject && mvn clean install
	
exec-preprocess:
	/Users/dmehta/Downloads/hadoop-2.7.3/bin/hadoop jar simple-bird-classifier/target/meera-kairavi-preprocess.jar ${input} ${preprocessOutput} ${regionPath}

exec-classifier:	
	spark-submit --master local --class classifier.App simpleproject/target/meera-kairavi-classifier-scala.jar ${preprocessOutput} ${predictOutput} ${modelPath}

exec-project: clean build exec-preprocess exec-classifier

clean:
	cd simple-bird-classifier && mvn clean
	cd simpleproject && mvn clean
	-rm -rf ${preprocessOutput} ${predictOutput} ${modelPath}

