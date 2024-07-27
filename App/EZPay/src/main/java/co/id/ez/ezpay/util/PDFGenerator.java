/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author RCS
 */
public class PDFGenerator {

    public void make() throws FileNotFoundException, DocumentException, BadElementException, IOException, URISyntaxException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
        Paragraph par = new Paragraph(new Chunk("Loremna ipsum nomerin kasdgngnnh ahsdgndasdbhasd najsdhas djhasdnjs"
                + "asdkajhsdj jashdkjahsd ajsjdha hsdjhaksjd jasshdkhasd kjahsdhasd"
                + "jjahsd jhajsd ajsdhkas hdaskd kajshdkasdh akjsdhajshduafabs fBDFJ gaj askja hs dkashd JASDKJASDABH DASYD"
                + "asdajsdasd", font));
        
        par.setAlignment(3);
        document.add(chunk);
        document.add(par);
        document.add(new Chunk());
        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addRows(table);
        addCustomRows(table);
        document.add(table);
        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("column header 2", "column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }

    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
//    Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
//    Image img = Image.getInstance(path.toAbsolutePath().toString());
//    img.scalePercent(10);

//    PdfPCell imageCell = new PdfPCell(img);
//    table.addCell(imageCell);
        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    public static void main(String[] args) {
        try {
            new PDFGenerator().make();
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
