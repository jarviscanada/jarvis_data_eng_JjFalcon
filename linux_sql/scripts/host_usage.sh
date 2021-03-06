#!/bin/bash
:'
# Script usage
./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

# Example
./scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
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
mem_info=`cat /proc/meminfo`
idle_time=`iostat -c`
disk_bio=`vmstat --unit M`
disk_avail=`df -BM /`

#retrieve both disk input and output
input=$(echo "$disk_bio" | sed '3q;d' | awk '{print $9}' | xargs)
output=$(echo "$disk_bio" | sed '3q;d' | awk '{print $10}' | xargs)

#retrieve and assign hardware usage to variables:  could use grep -E instead of egrep
mem_free=$(echo "$mem_info"  | grep -E '^MemFree'| awk '{print $2}' | xargs)
cpu_idle=$(echo "$idle_time" | sed '4q;d' | awk '{print $6}' | xargs)
cpu_kernel=$(echo "$mem_info"  | grep -E '^Kernel'| awk '{print $2}' | xargs)
disk_io=$((input+output))
disk_available=$(echo "$disk_avail"  | egrep '/dev/sda2' | awk '{print $2}' | grep -o -E '[0-9]+' | xargs)

host_name=$(hostname -f)

insert_stmt="INSERT INTO host_usage \
  (host_usage_id, host_info_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) \
  SELECT \
    DEFAULT, \
    info.host_info_id, \
    $mem_free, \
    $cpu_idle, \
    $cpu_kernel, \
    $disk_io, \
    $disk_available \
  FROM  host_info info \
  WHERE info.host_name = $host_name; "

export PGPASSWORD=$pgpass
psql -h $psql_host -p $psql_port -U $pguser -d $psql_db -c $insert_stmt
exit $?
