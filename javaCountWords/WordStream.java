import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;

// This is just a "helper" file. Feel free to read it if you are curious,
// but you will not need to change it in any way!

public class WordStream implements Iterable<String> {

    ArrayList<String> words;
    int position;

    public WordStream(String filename) throws FileNotFoundException {
	position = 0;
	words = new ArrayList<String>();
	Scanner scanner  = new Scanner(new FileReader(filename));
	scanner.useDelimiter("([-.,!\";?*():]|\\p{javaWhitespace})+");
	while (scanner.hasNext()) {
	    String word = scanner.next();
	    words.add(word.toLowerCase());
	}
    }

    public String nextWord() {
	if (position < words.size()) {
	    position++;
	    return words.get(position-1);
	}
	else 
	    return null;
    }

    public boolean hasNextWord() {
	return (position < words.size());
    }

    public void reset() {
	position=0;
    }

    public Iterator<String> iterator() {
	return new WSIterator();
    }
    
    private class WSIterator implements Iterator<String> {
	private int mypos;

	public WSIterator() {
	    mypos=0;
	}

	public boolean hasNext() {
	    return (mypos < words.size());
	}

	public String next() {
	    String word = words.get(mypos);
	    mypos++;
	    return word;
	}

	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }
}
