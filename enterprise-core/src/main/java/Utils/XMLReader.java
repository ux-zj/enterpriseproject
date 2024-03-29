package Utils;

import com.fasterxml.jackson.xml.XmlMapper;
import com.thoughtworks.xstream.XStream;

import java.io.IOException;

// TODO: Replace Jackson with XSTREAM, it seems to deal with arrays alot better than jackson

public class XMLReader implements Reader {
    private static volatile XMLReader instance = null;

    private XMLReader() {
    }

    public static XMLReader getInstance() {
        if (instance == null) {
            synchronized (XMLReader.class) {
                if (instance == null) {
                    instance = new XMLReader();
                }
            }
        }
        return instance;
    }

    // serialize object to XML
    public <T> String serialiseObject(T input) {
        XStream xstream = new XStream();
        return xstream.toXML(input);
    }

    public <T> T deserialiseObject(String XMLString, Class<T> tClass) {
        try {
            return new XmlMapper().readValue(XMLString, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> boolean isValidInput(String xmlString, Class<T> tClass) {
        try {
            if (deserialiseObject(xmlString, tClass) != null)
                return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public String getAcceptedMimeType() {
        return "application/xml";
    }
}
