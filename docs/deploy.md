```shell
# config-server:bootJar
java \
-jar \
-Dspring.profiles.active=prod \
-Dspring.output.ansi.enabled=always \
-Dfile.encoding=UTF-8 \
-Dsun.stdout.encoding=UTF-8 \
-Dsun.stderr.encoding=UTF-8 \
-Dlogging.file.name=mylogfile.log \
-Dlogging.file.path=/path/to/log \
config-server-0.0.1-SNAPSHOT.jar
```