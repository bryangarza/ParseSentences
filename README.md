#Sentence Parser

Take text the raw text of an article and output it to a text file, with a clear
delineation between each sentence.

##Example

Input:
```
As the world watches the strengthening of global jihadist movements –
from ISIS to al Qaeda to dozens of affiliated and like-minded groups – one of
those inside the U.S. government who was most vocal about the growing threats
is leaving his position. General Michael Flynn served as head of the Defense
Intelligence Agency from July 2012 until last week. Throughout his tenure he
challenged the Obama administration’s hopeful and inaccurate narrative about
the war against al Qaeda and jihadists – pushback that doubtless contributed to
his early departure from the agency.
```

Output:
```
Article 1
[1] --> "As the world watches the strengthening of global jihadist movements –
from ISIS to al Qaeda to dozens of affiliated and like-minded groups – one of
those inside the U.S. government who was most vocal about the growing threats
is leaving his position.
[2] --> "General Michael Flynn served as head of the Defense Intelligence
Agency from July 2012 until last week."
...and so on.
```

##Fetcher usage
```java
/**
 * Returns an ArrayList<String> each element containing a paragraph from the given article
 */
Fetcher.pullAndExtract("http://www.weeklystandard.com/blogs/intel-chief-blasts-obama_802242.html");
```
