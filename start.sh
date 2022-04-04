#!/bin/bash
echo "> build : Amattang-0.0.1-SNAPSHOT.jar" >> /home/ubuntu/deploy.log
echo "> copy environment : source .env 실행" >> /home/ubuntu/deploy.log
source /home/ubuntu/deploy/.env

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/deploy.log
CURRENT_PID=$(pgrep -f Amattang-0.0.1-SNAPSHOT.jar)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> DEPLOY_JAR 배포"    >> /home/ubuntu/deploy.log
nohup java -DSpring.profiles.active=prod -Duser.timezone=Asia/Seoul -jar /home/ubuntu/deploy/Amattang-0.0.1-SNAPSHOT.jar >> /home/ubuntu/deploy.log 2>/home/ubuntu/deploy_err.log &
