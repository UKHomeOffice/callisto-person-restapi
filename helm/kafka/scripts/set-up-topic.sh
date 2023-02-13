#!/usr/bin/env bash
set -e

root_path=${BASH_SOURCE[0]%/*}
. $root_path/create-topic.sh

bootstrap_server=$1
keystore_password=$2
topic=$3
properties_file=$4

cp scripts/$properties_file tmp/
cd /tmp

#put password in $properties_file
if sed 's/#/'$keystore_password'/g' $properties_file > temp-$properties_file && mv temp-$properties_file $properties_file
then
  echo "Properties file updated"
else
  echo "Properties file failed to update"
  exit 1
fi

ensure_topic_exists $topic
apply_permissions