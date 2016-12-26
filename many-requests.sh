#!/bin/bash

while read line; do
  wget "http://localhost:8080?query=${line}" /tmp &
done <business-list.txt

wait
