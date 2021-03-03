#!/bin/bash
:'
# Script usage
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# Example
./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
'

#assign cli arguments to variables
PSQL_HOST=$1
PSQL_PORT=$2
PSQL_DB=$3
PGUSER=$4
PGPASS=$5

#create meaningful and reusable aliases
lscpu_out=`lscpu`

#retrieve and assign hardware info to variables:  could use grep -E instead of egrep
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Arch" | awk '{print $2}' | xargs)
cpu_model=$cpu_model=$(echo "$lscpu_out"  | egrep "^Model name" | awk '{ for (x=3; x<=7; x++) {  print $x" " } }' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz" | awk '{print $3}' | xargs)
L2_cache=$(echo "$lscpu_out"  | egrep "^L2" | awk '{print $3}' | grep -o -E '[0-9]+' | xargs)
total_mem=$(grep -E '^MemTotal' /proc/meminfo | awk '{print $2}' | xargs)
timestamp=`date '+%Y-%m-%d %H-%M-%S'`

insert_stmt="INSERT INTO host_info \
  (host_info_id, host_number, host_name, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_capture) \
  VALUES \
    (DEFAULT, \
    1, \
    $hostname, \
    $cpu_number, \
    $cpu_architecture, \
    $cpu_model, \
    $cpu_mhz, \
    $L2_cache, \
    $total_mem, \
    $timestamp);"

export PGPASSWORD=$PGPASS
psql -h $PSQL_HOST -p $PSQL_PORT -U $PGUSER -d $PSQL_DB -c $insert_stmt
