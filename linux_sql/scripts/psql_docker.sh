#!/bin/bash

#check if docker is running, else start docker
sudo systemctl status docker || systemctl start docker

cmd=$1

case $cmd in
	create)
	  #check if docker container is already created
	  if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 2 ]; then
	  	echo "container already created"
  		echo "exiting"
  		exit 1;
	  fi

	  pguser=$2
	  pgpassword=$3

	  #check if log-in info are supplied
	  if [ "$pguser" = "" ] || [ "$pgpassword" = "" ]; then
		echo "missing user log-in info"
  		echo "exiting"
  		exit 1;
	  fi


	  #create a new psql container which will also automatically create the volume
	  docker run --name jrvs-psql -e POSTGRES_PASSWORD="$pgpassword" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 "$pguser"

	  #check if last command was a success
	  if [ $? -eq 1 ]; then
		echo "error creating container"
  		echo "exiting"
  		exit 1;
	  fi
	;;

	start)
	  docker container start jrvs-psql
	  exit $?
	;;

	stop)
	  docker container stop jrvs-psql
	  exit $?
	;;

	#else any other argument is invalid
	*)
	  echo -e "invalid argument\n"
	  exit 1
  	;;
esac
exit 0
