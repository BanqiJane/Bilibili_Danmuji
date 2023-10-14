#!/bin/bash

# 如果有指定/opt/store的持久卷则提前装配它,以便运行时可被采用
if [ -e /opt/store ]; then
  ln -s /opt/store/profile /workspace/${PROFILE_NAME}
fi
JAVA_OPS=${JAVA_OPS:-"-Xms256m -Xmx1024m"}
java $JAVA_OPS -jar app.jar
