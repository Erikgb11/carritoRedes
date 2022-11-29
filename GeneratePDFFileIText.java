/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.practica1aplicaciones;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.io.*;
import java.util.*;

/**
 *
 * @author erikg
 */
public class GeneratePDFFileIText {

    // Fonts definitions (Definición de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private static final String iTextExampleImage = "/home/xules/codigoxules/iText-Example-image.png";

    /**
     *      * We create a PDF document with iText using different elements to
     * learn      * to use this library.      * Creamos un documento PDF con
     * iText usando diferentes elementos para aprender      * a usar esta
     * librería.      * @param pdfNewFile  <code>String</code>      * pdf File we
     * are going to write.      * Fichero pdf en el que vamos a escribir. 
     
     */
    public void createPDF(File pdfNewFile, ArrayList<Producto> chi,ArrayList<Integer> c) {
        // We create the document and set the file name.        
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF " + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
// We add metadata to PDF
// Añadimos los metadatos del PDF
            document.addTitle("Table export to PDF (Exportamos la tabla a PDF)");
            document.addSubject("Using iText (usando iText)");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Código Xules");
            document.addCreator("Código Xules");

// First page
// Primera página 
            Chunk chunk = new Chunk("Recibo de compra", chapterFont);
            chunk.setBackground(BaseColor.GRAY);
// Let's create de first Chapter (Creemos el primer capítulo)
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph("Gracias por su compra vuelva pronto", paragraphFont));
            chapter.add(new Paragraph("Su compra es de: ", paragraphFont));
            
// How to use PdfPTable
// Utilización de PdfPTable
// We use various elements to add title and subtitle
// Usamos varios elementos para añadir título y subtítulo
            Anchor anchor = new Anchor("", categoryFont);
            Chapter chapTitle = new Chapter(new Paragraph(anchor), 1);
            Paragraph paragraph = new Paragraph("", subcategoryFont);
            Section paragraphMore = chapTitle.addSection(paragraph);
            Integer numColumns = 4;
            Integer numRows=chi.size()+3;//editarlo
// We create the table (Creamos la tabla).
            PdfPTable table = new PdfPTable(numColumns);
// Now we fill the PDF table 
// Ahora llenamos la tabla del PDF
            PdfPCell columnHeader;
// Fill table rows (rellenamos las filas de la tabla).   
            columnHeader = new PdfPCell(new Phrase("Lista"));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);
            columnHeader = new PdfPCell(new Phrase("Nombre"));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);
            columnHeader = new PdfPCell(new Phrase("Cantidad"));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);
            columnHeader = new PdfPCell(new Phrase("Costos Unitarios"));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);
            table.setHeaderRows(1);
            float ch=0;
// Fill table rows (rellenamos las filas de la tabla).                
            for (int row = 0; row < chi.size(); row++) {
                table.addCell(row+"");
                table.addCell(chi.get(row).getNombre());
                table.addCell(c.get(row)+"");
                table.addCell(chi.get(row).getPrecio()+"");
                ch=c.get(row).floatValue()*chi.get(row).getPrecio()+ch;
            }
            table.addCell("");
            table.addCell("");
            table.addCell("Subtotal");
            table.addCell(ch+"");
            table.addCell("");
            table.addCell("");
            table.addCell("IVA 16%");
            float iva=(ch*16)/100;
            table.addCell(iva+"");
            table.addCell("");
            table.addCell("");
            table.addCell("Total");
            float total=iva+ch;
            table.addCell(total+"");
// We add the table (Añadimos la tabla)
            document.add(chapter);
            paragraphMore.add(table);
// We add the paragraph with the table (Añadimos el elemento con la tabla).
            document.add(chapTitle);
            document.close();
            System.out.println("Tu recibo se ha generado con exito!!!");
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar tu recibo: " + documentException);
        }
    }
}
