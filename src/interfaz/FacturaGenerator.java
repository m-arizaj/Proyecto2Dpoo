package interfaz;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class FacturaGenerator {

    public static void main(String[] args) throws CsvException {
        try {
            generarFacturaPDF("Nombre Cliente", "Placa del Carro", 10000);
            System.out.println("PDF creado");
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void generarFacturaPDF(String cliente, String placaCarro, double monto)
            throws IOException, DocumentException, CsvException {
        Document document = new Document();
        placaCarro = "ABC123";
        String dest = placaCarro + ".pdf";

        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        // Agregar título
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("SISTEMA DE ALQUILER DE VEHÍCULOS", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Agregar información del cliente
        Font infoFont = new Font(Font.FontFamily.HELVETICA, 12);
        document.add(new Paragraph("Cliente: " + cliente, infoFont));

        // Agregar información del carro desde el archivo CSV
        String carrosCSVPath = "./datos/carros.csv"; // Ajusta la ruta según tu caso
        List<String[]> carrosData = readCSV(carrosCSVPath);
        String[] headers = carrosData.get(0); // Encabezados del CSV

        String[] carroInfo = buscarCarroPorPlaca(carrosData, placaCarro);
        if (carroInfo != null) {
            document.add(new Paragraph("Información del Carro:", infoFont));
            for (int i = 0; i < headers.length; i++) {
                document.add(new Paragraph(headers[i] + ": " + carroInfo[i], infoFont));
            }
        } else {
            document.add(new Paragraph("Carro no encontrado en el archivo CSV.", infoFont));
        }

        // Agregar monto
        document.add(new Paragraph("Monto: $" + monto, infoFont));

        // Agregar firma del administrador
        String adminSignaturePath = "admin_signature.png"; // Ajusta la ruta según tu caso
        Image adminSignature = Image.getInstance(adminSignaturePath);
        adminSignature.scaleAbsolute(200, 100); // Ajusta el tamaño de la firma
        document.add(adminSignature);

        // Agregar "Atentamente, Administrador encargado del alquiler"
        document.add(new Paragraph("\nAtentamente,\nAdministrador encargado del alquiler", infoFont));

        // Puedes agregar más contenido según tus necesidades

        document.close();
        abrirPDF(dest);
    }

    private static List<String[]> readCSV(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }

    private static String[] buscarCarroPorPlaca(List<String[]> carrosData, String placa) {
        for (int i = 1; i < carrosData.size(); i++) { // Comenzamos desde 1 para omitir el encabezado
            String[] carro = carrosData.get(i);
            if (carro[0].equals(placa)) {
                return carro;
            }
        }
        return null; // Si no se encuentra la placa
    }

    public static void abrirPDF(String pdfPath) {
        try {
            // Abre el PDF con el programa predeterminado del sistema
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfPath);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                Runtime.getRuntime().exec("xdg-open " + pdfPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




//package interfaz;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfWriter;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//
//
//public class FacturaGenerator 
//{
//public static void main(String[] args) throws DocumentException {
//        
//        try {
//            Document document = new Document();
//            String dest = "hello_world.pdf";
//            PdfWriter.getInstance(document, new FileOutputStream(dest));
//            document.open();
//            
//            Phrase header = new Phrase("Hello world");
//            document.add(header);
//            
//            document.close();
//            
//            System.out.println("PDF creado, revisar ubicación del proyecto, ahí podrá ver la factura.");
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FacturaGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}
