#!/bin/bash

#check if docker is running, else start docker
sudo systemctl status docker || systemctl start docker

case $1 in
	create)
	  #check if docker container is already created
	  if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 2 ]; then
	  	echo "container already created"
  		echo "exiting"
  		exit 1;
	  fi

	  PGUSER=$2
	  PGPASSWORD=$3

	  #check if log-in info are supplied
	  if [ "$PGUSER" = "" ] || [ "$PGPASSWORD" = "" ]; then
		echo "missing user log-in info"
  		echo "exiting"
  		exit 1;
	  fi

	  #create a new volume
	  docker volume create pgdata

	  #create a new psql container
	  docker run --name jrvs-psql -e POSTGRES_PASSWORD="$PGPASSWORD" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 "$PGUSER"

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