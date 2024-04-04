#!/bin/bash
treeTaggerDir="/home/asbel/projects/si/tt"
export PATH="$treeTaggerDir/bin:$treeTaggerDir/cmd:$PATH"
# untaggedInput="$(cat /dev/stdin)"
untaggedInput="$1"
taggedInput="$(echo $untaggedInput | tree-tagger-english 2> /dev/null | awk '{print $1;print $2;}')"

counter=0
words=()
tags=()
query_string=''

for i in $taggedInput; do
    if [[ $(( "$counter" % 2 )) == 0 ]]; then
        words+=("$i")
    else
        tags+=("$i")
    fi
    counter=$(( "$counter" + 1 ))
done


for i in "${!words[@]}"
do
    word="${words[$i]}"
    tag="${tags[$i]}"
    type=""

    if [[ "$tag" == "NN" || "$tag" == "NV" ]]
    then
        type=""
        test "$tag" == "NN" && type="n"
        test "$tag" == "NV" && type="v"

        query_string+="$(wn $word -hypo$type | grep -oP '(?<=\=\> ).*' | sed 's/\([^,]\) \([^,]\)/\1;\2/g' | sed 's/,//g' | xargs -I{} printf ' {}' | sed 's/\s\+/ OR /g' | sed 's/^\s*OR\s\+//g' | sed 's/\s\+OR\s*$//g' | sed 's/^/(/g' | sed 's/$/)/g' | sed 's/;/ /g'| xargs echo) "

    else
        query_string+="$word "
    fi
done
query_string=$(echo $query_string | sed 's/^\s\+//g' | sed 's/\s\+$//g')
echo "$query_string"

