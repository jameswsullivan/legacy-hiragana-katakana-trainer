## Hiragana and Katakana Trainer

#### Screenshot

![](https://github.com/jameswsullivan/legacy-hiragana-katakana-trainer/blob/main/screenshot.PNG)


#### Use
- Download the zipped package `Japanese.Hiragana.and.Katakana.Trainer.7z` from the v1.0.1 release, extract all the files and run the `JapaneseSyllabaryTrainer.jar`.
- The `voice` directory needs to be placed within the same directory as the `jar` file.
- The `settings.ini` file is optional, and will be created by the app if absent.


#### Build

```
# Ensure Java 7 or 8 is installed.
# Using Ubuntu:22.04 as an example.

apt-get install openjdk-8-jdk -y

# Clone the repo:
git clone https://github.com/jameswsullivan/legacy-hiragana-katakana-trainer.git

# Build the jar: (working dir is legacy-hiragana-katakana-trainer)
javac -cp lib/jl1.0.1.jar -d bin ./src/*.java
jar cmf MANIFEST.MF JapaneseSyllabaryTrainer.jar -C bin .
```


#### The Story
Around 2014 ~ 2015, a few of my friends were learning Japanese and were preparing for the JLPT N1, N2 exams. At the time the language learning apps such as Duolingo were not as popular and advanced as they are today and they needed something that would exclusively focus on the
Hiragana and Katakana training and be able to run on desktop computers.
We tried a few interactive learning tools like Rosetta Stone, however, they were either "too powerful" that exceeded the needs or lacked certain features we wanted.
So I decided to make my own.