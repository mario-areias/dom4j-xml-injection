import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


public class Dom4jTest {

    @Test
    public void testXMLInjectionOnAttributeName() throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        String injectedElement = "<author name=\"hack\" location=\"World\">true</author>";

        Element author = root.addElement("author")
                .addAttribute("name=\"hack\" location=\"World\">true</author><author name", "James")
                .addAttribute("location", "UK")
                .addText("James Strachan");

        Element author2 = root.addElement("author")
                .addAttribute("name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");

        writeFile(document);

        assertThat(document.asXML(), containsString(injectedElement));
    }

    @Test
    public void testCommentOtherElementsOnAttributeName() throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        String injectedElement = "<author name=\"hack\" location=\"World\">true</author>";

        Element author = root.addElement("author")
                .addAttribute("name=\"hack\" location=\"World\">true</author><!-- author name", "James")
                .addAttribute("location", "UK")
                .addText("James Strachan");

        Element author2 = root.addElement("author")
                .addAttribute("--><author name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");

        writeFile(document);

        assertThat(document.asXML(), containsString(injectedElement));
    }

    private void writeFile(Document document) throws IOException {
        try (FileWriter fileWriter = new FileWriter("output.xml")) {
            XMLWriter writer = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
            writer.write( document );
            writer.close();
        }
    }

    @Test
    public void testXMLInjectionOnElementName() throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        String injectedAttribute = "age=\"20\"";

        Element author = root.addElement("author " + injectedAttribute)
                .addAttribute("name", "James")
                .addAttribute("location", "UK");

        Element author2 = root.addElement("author")
                .addAttribute("name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");


        writeFile(document);

        assertThat(document.asXML(), containsString(injectedAttribute));
    }
}