#!/bin/bash

#Checks if given command is available within a system
#$1 - command name (required)
#$2 - additional message (optional)
check_if_installed() {
  if [ $# -ne 1 ] && [ $# -ne 2 ]; then
    printf "Invalid number of parameters for check_if_installed"
    exit 1
  fi

  if ! command -v "$1" &>/dev/null; then
    if [ $# -eq 1 ]; then
      printf "This script requires %s installed." "$1"
    else
      printf "This script requires %s installed.\n%s " "$1" "$2"
    fi
    exit 2
  fi
}

#Checks if the the amount of arguments provided is equal to the expected ones
#$1 - expected count of arguments
#$2 - received count of arguments
#$3 - message with sample usage of command
check_for_args() {
  if [ $# -ne 3 ]; then
    printf "Invalid number of parameters for check_for_args"
    exit 1
  fi
  if [ "$1" -ne "$2" ]; then
    printf "Invalid command arguments.\nSample usage: %s" "$3"
    exit
  fi
}

check_arg_value(){
    case $1 in
        library.js)
        ;;
        sorting.js)
        ;;
        *)
        echo -e "Unsuported test name.\n\nOnly the following test names are allowed:\nlibrary.js\nsorting.js"
    esac
}

#Script Content
check_if_installed k6 "See instalation guide: https://k6.io/docs/get-started/installation"
check_for_args 2 $# "./runTest <TEST_NAME> <BASE_URL>"
check_arg_value $1

export REMOTE_URL=$2
k6 run ../k6-tests/tests/src/$1
