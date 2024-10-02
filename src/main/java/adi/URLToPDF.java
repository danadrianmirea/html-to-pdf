package adi;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileOutputStream;
import java.io.IOException;

public class URLToPDF {
    public static void main(String[] args) {
        // URL of the webpage to convert
        String url = "https://example.com";
        
        // Output file where the PDF will be saved
        String outputPdf = "webpage_output.pdf";

        try {
            // Fetch the webpage content using Jsoup
            Document doc = Jsoup.connect(url).get();

            // Clean the HTML content to ensure it is well-formed
            String cleanedHtml = doc.outerHtml(); // Ensures well-formed HTML structure
            cleanedHtml = cleanedHtml.replaceFirst("<!doctype[^>]*>", ""); // Removes the DOCTYPE line

            // Convert self-closing tags to XHTML format
            cleanedHtml = cleanedHtml.replaceAll("<meta([^>]*)>", "<meta$1 />");
            cleanedHtml = cleanedHtml.replaceAll("<link([^>]*)>", "<link$1 />");
            cleanedHtml = cleanedHtml.replaceAll("<img([^>]*)>", "<img$1 />");
            cleanedHtml = cleanedHtml.replaceAll("<br>", "<br />");
            cleanedHtml = cleanedHtml.replaceAll("<hr>", "<hr />");

            // Print or log the fetched HTML to inspect it
            System.out.println(cleanedHtml);

            // Convert the cleaned HTML to PDF and save it to file
            convertHtmlToPdf(cleanedHtml, outputPdf);

            System.out.println("PDF created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertHtmlToPdf(String htmlContent, String outputPdf) {
        try (FileOutputStream os = new FileOutputStream(outputPdf)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            
            // Set the HTML content and output stream for the PDF
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);

            // Run the PDF conversion
            builder.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
