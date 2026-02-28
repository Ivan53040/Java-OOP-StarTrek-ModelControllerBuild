#!/bin/bash

if [ -f submission.zip ]; then
  old=$(date +%s)
  echo "Backup up old submission as submission-${old}"
  echo
  mv submission.zip submission-"${old}".zip
fi

touch refs.md

zip submission.zip src/sttrj/game/Enterprise.java
zip submission.zip src/sttrj/game/Entity.java
zip submission.zip src/sttrj/game/Galaxy.java
zip submission.zip src/sttrj/game/Game.java
zip submission.zip src/sttrj/game/Klingon.java
zip submission.zip src/sttrj/game/Quadrant.java
zip submission.zip src/sttrj/game/Star.java
zip submission.zip src/sttrj/game/Starbase.java
zip submission.zip src/sttrj/game/Stat.java
zip submission.zip src/sttrj/game/XyPair.java
zip submission.zip src/sttrj/input/validator/CourseValidator.java
zip submission.zip src/sttrj/input/validator/EnergyValidator.java
zip submission.zip src/sttrj/input/validator/WarpCoordValidator.java

zip submission.zip refs.md
