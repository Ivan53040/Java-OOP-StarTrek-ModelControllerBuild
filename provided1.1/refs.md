[1] AI generated method and regex

AI is used to brainstorm and explain the method of matches

return input.matches("\[1-9],\[0-9](\\\\.\[0-9])?");

checking the first index matches "1-9"

the second index is the ","

the third index matches "0-9" (optional: with . and 0-9 after)

and ensure nothing behind it

[2] AI Debugging

As I found that using the moveBetweenQuadrants function, it will create a new guadrant everytime and desync with the galaxy.

When I test the code, I kill 1 klingon in 5,4 and check with long scan, it shows the klingon is removed.

But when I go back to 4,4 and long scan, the klingon in 5,4 reappear, I cannot debug it myself so a seek help from ChatGPT.

ChatGPT gives me the solution by adding two new methods reshuffleCurrentQuadrantPreservingCounts(); and placeEnterpriseOnEmptySector();.

It solves the desync problem correctly.
