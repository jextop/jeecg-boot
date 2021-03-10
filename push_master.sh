#!/bin/bash

echo "commit:" $1

git checkout master
git cherry-pick $1
git status

git push origin master
git push gitee master
git push github master

git checkout rel
git push origin rel
git status
