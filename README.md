# Atsuko
Atsuko is an AI Java Project originally forked from [shinixsensei-dev/yamazaki](https://github.com/shinixsensei-dev/yamazaki).
It's main purpose is, to learn basic english language and to build logic sentences to be able to hold a conversation with a real human being.

# Research
To build up a reliable Database of Words, the AI scans through semi-random [Wikipedia](https://en.wikipedia.org/) articles and sorts every word by their type of speech.
An explanation of these functions can be found here:
- [Scanning through Wikipedia articles](https://github.com/namespace-media/yamazaki#word-collector)
- [Sorting words by type of speech](https://github.com/namespace-media/yamazaki#sorting-words-by-type-of-speech)

## Word Rating System (WRS)
Scores are important for an AI to know the meaning and strength of a word or phrase. To achieve a score like this, we're using [Discord](https://discord.com) to let users rate random words,
chosen from the database, by "Neutral" or "Bad". Out of this information, the AI is building a Word Rating and a confidence score.
The higher the confidence score is, the more likely it is for the AI to be correct about the connotation of the word.

## Word Collector
To collect a good amount of Words in a fairly short amount of time, the AI is using a specified Wikipedia article and detects every Word by splitting the visible text by spaces and by replacing
all special characters.
If an article ends, it is using a random, already collected noun from it's database to search for new articles.
This Process is repeated infinitly and already builds up a big database in a few hours. 

## Sorting words by type of speech
Everytime, a new word is found, it is searched up on a [dictionary](http://wordnetweb.princeton.edu/), which shows the available types.
Then, every word is sorted by "noun", "verb", "adjective" or "adverb". Names and other words will be categorized with "undefined".

Additional to this Process, the AI is also collecting similar words and phrases and saves them as tags.
These tags can be used to understand sentences by comparing each word's tags with another to determine the list the top mutual tags.