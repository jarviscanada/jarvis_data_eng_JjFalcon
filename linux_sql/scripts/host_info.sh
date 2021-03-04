#!/bin/bash
:'
# Script usage
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# Example
./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
'

#validate all arguments are supplied
if [ "$#" -ne 5 ]; then
  echo "Invalid number of parameters"
  exit 1
fi

#assign cli arguments to variables
psql_host=$1
psql_port=$2
psql_db=$3
pguser=$4
pgpass=$5

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
  (host_info_id, host_name, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_capture) \
  VALUES \
  (DEFAULT, \
  $hostname, \
  $cpu_number, \
  $cpu_architecture, \
  $cpu_model, \
  $cpu_mhz, \
  $L2_cache, \
  $total_mem, \
  $timestamp);"

export PGPASSWORD=$pgpass
psql -h $psql_host -p $psql_port -U $pguser -d $psql_db -c $insert_stmt
exit $?
