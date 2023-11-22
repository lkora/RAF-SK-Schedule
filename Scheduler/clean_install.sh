#!/bin/bash
# Remember the root directory
root_directory=$(pwd)
command_to_run='mvn clean install'

# Building list of modules
declare -a modules=("Lecture" "ScheduleManagerService/ScheduleImporterSpec" "ScheduleManagerService/ScheduleExporterSpec" "ScheduleManagerService" "ScheduleManager" "ScheduleManager/ScheduleManagerCollection" "ScheduleManager/ScheduleManagerWeekly" "Schedule")

# Build all modules first
for module in "${modules[@]}"; do
    echo "Running $command_to_run on module $module"
    # Navigate to the module directory
    cd $root_directory/$module
    # Run the command
    $command_to_run
done