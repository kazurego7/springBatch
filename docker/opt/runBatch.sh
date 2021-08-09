#!/bin/bash

java -Dspring.profiles.active=prod -DrootPath=/opt/logs -jar /opt/batch-processing.jar
