#!/bin/sh

set -e
git checkout develop

set +e
git merge --no-edit main
mergeCode=$?

set -e
if [ $mergeCode -eq 1 ]; then
  git checkout --theirs README.md
  git add README.md
  git commit --no-edit
fi

git push origin develop