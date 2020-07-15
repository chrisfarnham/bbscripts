#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

SCRIPTS=$(ls -1 $DIR/bin)
for s in $SCRIPTS; do
    set -x
    rm -f $HOME/bin/$s
    ln -s $DIR/bin/$s $HOME/bin/$s
done