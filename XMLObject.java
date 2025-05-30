import java.util.*;
import java.nio.file.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class XMLObject {
    private Path path;
    private String contents; 
    private Element main;

    public XMLObject(String s) {
        this(Paths.get(s));
    }

    public XMLObject(Path p) {
        path = p;
        contents = "";
        main = new Element();

        try {
            contents = Files.readString(p);
        }
        catch(Exception e) {
            System.out.println(e);
        }

        contents = contents.replaceAll("[\\n\\r]", "");

        parse();

        // System.out.println("contents: " + contents);
    }

    private void parse() {
        String firstTag = getNextTag(contents);
        Element firstElement = new Element();

        int index = firstTag.length();
        int depth = 0;
        ArrayList<Element> stack = new ArrayList<>();

        depth += parseTag(firstElement, firstTag);
        stack.add(0, firstElement);

        while(!contents.substring(index).equals("")) {
            // System.out.println("-------------------");

            contents = contents.trim();
            String tag = getNextTag(contents.substring(index));
            Element e = new Element();

            // System.out.println("tag: " + tag);

            int startOfImportant = index;
            int endOfImportant = contents.substring(startOfImportant).indexOf("<") + startOfImportant;
            String important = contents.substring(startOfImportant, endOfImportant);
            // System.out.println("important: " + important);

            if(!important.equals(important.replaceAll("[A-z0-9]", ""))) {
                stack.get(0).appendValue(important.trim().replaceAll("/\\s\\s+/g", " "));
                index += important.length();
            }

            if(parseTag(e, tag) == 1) {
                // System.out.println("parsed a tag to 1");
                stack.get(0).addChild(e);
                stack.add(0, e);
                depth++;
            }
            else {
                // System.out.println("parsed a tag to -1");
                stack.remove(0);
                depth--;
            }
          
            index += (contents.substring(index).length() - contents.substring(index).trim().length());
            index += tag.length();  

            // System.out.println("remaining in contents: " + contents.substring(index));
            // System.out.println("index: " + index + "\ncontents.length: " + contents.length());
        }   

        main = firstElement;

        // System.out.println(e.toString());
    }

    private String getNextTag(String s) {
        int open = s.indexOf("<");
        int close = s.indexOf(">");

        // System.out.println("index of opening bracket: " + open + "\nindex of closing bracket: " + close);

        if((close == -1 || close == 0) || close < open) throw new RuntimeException("Invalid index of closing bracket.");
        
        String tag = s.substring(open, close + 1);

        return tag;
    }

    private int parseTag(Element e, String s) {
        // System.out.println("tag passed to parseTag: " + s);
        // System.out.println("index of first space is: " + s.indexOf(" "));

        s = s.trim();

        if(s.substring(1, 2).equals("/")) return -1;

        if(s.indexOf(" ") != -1) {
            int space = s.indexOf(" ");
            String relevant = s.substring(space + 1);

            e.setName(s.substring(1, space));

            while(!relevant.substring(0, 1).equals(">")) {
                int firstQuote = relevant.indexOf("\"");
                int secondQuote = relevant.substring(firstQuote + 1).indexOf("\"") + firstQuote + 2;

                // System.out.println("firstQuote: " + firstQuote + "\nsecondQuote: " + secondQuote);
                // System.out.println("char at 1st quote: " + relevant.substring(firstQuote, firstQuote+1) + "\nchar at 2nd quote: " + relevant.substring(secondQuote, secondQuote+1));
                // System.out.println("thing between 1st and 2nd quotes: " + relevant.substring(firstQuote, secondQuote) + "-\n");

                String tagName = relevant.substring(0, firstQuote - 1);
                String tagValue = relevant.substring(firstQuote + 1, secondQuote - 1);

                // System.out.println("tagName: " + tagName + "\ntagValue: " + tagValue);
                e.addTag(tagName, tagValue);

                relevant = relevant.substring(secondQuote);
                if(relevant.indexOf(",") == 0) relevant = relevant.substring(1);
                relevant = relevant.trim();
            }
        }
        else {
            e.setName(s.substring(1, s.length() - 1));
        }

        return 1;
    }

    public String toString() {
        return main.toString();
    }

    public Element searchByTag(String tagName, String tagValue) {
        return main.searchByTag(tagName, tagValue);
    }

    public Element searchByValue(String v) {
        return main.searchByValue(v);
    }

    public Element searchByName(String n) {
        return main.searchByName(n);
    }

    public String value(Element e) {
        return e.getValue();
    }
}

class Element {
    private String name;
    private HashMap<String, String> tags;
    private String value;
    private Element[] children;
    private boolean hasSet = false;

    ////////////////////////////////////////////////////////////////
    ///////////////////////// CONSTRUCTORS /////////////////////////
    ////////////////////////////////////////////////////////////////

    public Element() {
        name = "";
        tags = new HashMap<String, String>();
        value = "";
        children = null;
        //Element(null, null, null, null);
    }

    //////////////////////////////////////////////////////////////////
    ///////////////////////// ACTUAL METHODS /////////////////////////
    //////////////////////////////////////////////////////////////////

    public Element searchByTag(String tagName, String tagValue) {
        try {
            if(tags.get(tagName).equals(tagValue)) {
                return this;
            }
        }
        catch(Exception except) {
            if(children == null) return null;
            for(Element e : children) {
                Element el = e.searchByTag(tagName, tagValue);
                if(el != null) return el;
            }

            return null;
        }
        
        return null;
    }

    public Element searchByValue(String v) {
        if(value != null && value.equals(v)) return this;
        else {
            if(children == null) return null;
            for(Element e : children) {
                Element el = e.searchByValue(v);
                if(el != null) return el;
            }

            return null;
        }
    }

    public Element searchByName(String n) {
        if(name != null && name.equals(n)) return this;
        else {
            if(children == null) return null;
            for(Element e : children) {
                Element el = e.searchByName(n);
                if(el != null) return el;
            }
        }
        
        return null;
    }

    public String toString() { 
        String s = "";

        s += "<" + name;
        for(Object t : tags.keySet()) s += " " + t + "=\"" + tags.get(t) + "\""; 
        s += ">";

        if(value != null) s += value;

        if(children != null) for(Element e : children) {
            s += e.toString();
        }

        s += "</" + name + ">";
        
        return s;
    }

    ///////////////////////////////////////////////////////////////
    /////////////////////////// GETTERS ///////////////////////////
    ///////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public Element[] getChildren() {
        return children;
    }

    public HashMap<String, String> tags() {
        return tags;
    }

    public String getValue() {
        return value; 
    }

    public Set<Map.Entry<String, String>> getEntrySet() {
        return tags.entrySet();
    }

    ///////////////////////////////////////////////////////////////
    /////////////////////////// SETTERS ///////////////////////////
    ///////////////////////////////////////////////////////////////

    public void setName(String s) {
        name = s;
    }

    public void setChildren(Element[] e) {
        children = e;
       
        // System.out.println("Children: " + children.toString());
    }

    public void addChild(Element e) {
        if(children == null) {
            Element[] arr = {e};
            setChildren(arr);
            return;
        }

        Element[] arr = new Element[children.length + 1];
        for(int i = 0; i < children.length; i++) {
    	  	arr[i] = children[i];
        }
        arr[children.length] = e;
        children = arr;
      
        // System.out.println("Children: " + children.toString());
    }

    public void addTag(String t, String v) {
        tags.put(t, v);
    }

    public void setValue(String s) {
        value = s; 
    }

    public void appendValue(String s) {
        if(!hasSet) {
            setValue(s);
            hasSet = true;
            return;
        }
  
        value += s;
    }
}