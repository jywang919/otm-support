#!/bin/bash

alias docker='sudo docker'
alias lsl='ls -la'
alias ll='ls -l'
alias cd..='cd ..'
alias grps='getent group |grep -i --color '
alias grpsall='getent group |cut -d: -f1|sort'
alias grpsme='id -Gn'

alias chmodrwxU='chmod 700'  #user with RWX -- add -R for recursive
alias chmodrwxG='chmod 770'  #Group with RWX
alias chmodrwxA='chmod 777'  #UGA have RWX

alias chmodGX='chmod 750'  #Group and owner have RX
