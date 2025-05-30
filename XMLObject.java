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
        String firstAttribute = getNextAttribute(contents);
        Element firstElement = new Element();

        int index = firstAttribute.length();
        int depth = 0;
        ArrayList<Element> stack = new ArrayList<>();

        depth += parseAttribute(firstElement, firstAttribute);
        stack.add(0, firstElement);

        while(!contents.substring(index).equals("")) {
            // System.out.println("-------------------");

            contents = contents.trim();
            String attribute = getNextAttribute(contents.substring(index));
            Element e = new Element();

            // System.out.println("attribute: " + attribute);

            int startOfImportant = index;
            int endOfImportant = contents.substring(startOfImportant).indexOf("<") + startOfImportant;
            String important = contents.substring(startOfImportant, endOfImportant);
            // System.out.println("important: " + important);

            if(!important.equals(important.replaceAll("[A-z0-9]", ""))) {
                stack.get(0).appendTag(important.trim().replaceAll("/\\s\\s+/g", " "));
                index += important.length();
            }

            if(parseAttribute(e, attribute) == 1) {
                // System.out.println("parsed a attribute to 1");
                stack.get(0).addChild(e);
                stack.add(0, e);
                depth++;
            }
            else {
                // System.out.println("parsed a attribute to -1");
                stack.remove(0);
                depth--;
            }
          
            index += (contents.substring(index).length() - contents.substring(index).trim().length());
            index += attribute.length();  

            // System.out.println("remaining in contents: " + contents.substring(index));
            // System.out.println("index: " + index + "\ncontents.length: " + contents.length());
        }   

        main = firstElement;

        // System.out.println(e.toString());
    }

    private String getNextAttribute(String s) {
        int open = s.indexOf("<");
        int close = s.indexOf(">");

        // System.out.println("index of opening bracket: " + open + "\nindex of closing bracket: " + close);

        if((close == -1 || close == 0) || close < open) throw new RuntimeException("Invalid index of closing bracket.");
        
        String attribute = s.substring(open, close + 1);

        return attribute;
    }

    private int parseAttribute(Element e, String s) {
        // System.out.println("attribute passed to parseAttribute: " + s);
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

                String attributeName = relevant.substring(0, firstQuote - 1);
                String attributeTag = relevant.substring(firstQuote + 1, secondQuote - 1);

                // System.out.println("attributeName: " + attributeName + "\nattributeTag: " + attributeTag);
                e.addAttribute(attributeName, attributeTag);

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

    public Element searchByAttribute(String attributeName, String attributeTag) {
        return main.searchByAttribute(attributeName, attributeTag);
    }

    public Element searchByTag(String v) {
        return main.searchByTag(v);
    }

    public Element searchByName(String n) {
        return main.searchByName(n);
    }

    public String tag(Element e) {
        return e.getTag();
    }
}

class Element {
    private String name;
    private HashMap<String, String> attributes;
    private String tag;
    private Element[] children;
    private boolean hasSet = false;

    ////////////////////////////////////////////////////////////////
    ///////////////////////// CONSTRUCTORS /////////////////////////
    ////////////////////////////////////////////////////////////////

    public Element() {
        name = "";
        attributes = new HashMap<String, String>();
        tag = "";
        children = null;
        //Element(null, null, null, null);
    }

    //////////////////////////////////////////////////////////////////
    ///////////////////////// ACTUAL METHODS /////////////////////////
    //////////////////////////////////////////////////////////////////

    public Element searchByAttribute(String attributeName, String attributeTag) {
        try {
            if(attributes.get(attributeName).equals(attributeTag)) {
                return this;
            }
        }
        catch(Exception except) {
            if(children == null) return null;
            for(Element e : children) {
                Element el = e.searchByAttribute(attributeName, attributeTag);
                if(el != null) return el;
            }

            return null;
        }
        
        return null;
    }

    public Element searchByTag(String v) {
        if(tag != null && tag.equals(v)) return this;
        else {
            if(children == null) return null;
            for(Element e : children) {
                Element el = e.searchByTag(v);
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
        for(Object t : attributes.keySet()) s += " " + t + "=\"" + attributes.get(t) + "\""; 
        s += ">";

        if(tag != null) s += tag;

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

    public HashMap<String, String> attributes() {
        return attributes;
    }

    public String getTag() {
        return tag; 
    }

    public Set<Map.Entry<String, String>> getEntrySet() {
        return attributes.entrySet();
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

    public void addAttribute(String t, String v) {
        attributes.put(t, v);
    }

    public void setTag(String s) {
        tag = s; 
    }

    public void appendTag(String s) {
        if(!hasSet) {
            setTag(s);
            hasSet = true;
            return;
        }
  
        tag += s;
    }
}