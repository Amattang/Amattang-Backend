#!/bin/bash
echo "> build : Amattang-0.0.1-SNAPSHOT.jar" >> /home/ubuntu/deploy.log


echo "> build 파일 복사" >> /home/ubuntu/deploy.log
cp Amattang-0.0.1-SNAPSHOT.jar /home/ubuntu

echo "> .env 파일 복사" >> /home/ubuntu/deploy.log
cp .env /home/ubuntu

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
nohup java -DSpring.profiles.active=prod -Duser.timezone=Asia/Seoul -jar /home/ubuntu/Amattang-0.0.1-SNAPSHOT.jar >> /home/ubuntu/deploy.log 2>/home/ubuntu/deploy_err.log &
