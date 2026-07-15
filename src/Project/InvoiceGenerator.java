/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project;

/**
 *
 * @author acer
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javax.swing.JTable;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class InvoiceGenerator {

    // ===========================
    // Helper Cell
    // ===========================
    public static PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(8);
        return cell;
    }

    // ===========================
    // Header Cell
    // ===========================
    public static PdfPCell getHeaderCell(String text) {

        Font font = new Font(
                Font.FontFamily.HELVETICA,
                11,
                Font.BOLD,
                BaseColor.WHITE);

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8);

        return cell;
    }

    // ===========================
    // Data Cell
    // ===========================
    public static PdfPCell getDataCell(String text) {

        PdfPCell cell = new PdfPCell(new Phrase(text));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8);

        return cell;
    }

    // ===========================
    // Main Invoice Method
    // ===========================
    public static void generateInvoice(

            JTable table,

            String customerName,
            String contact,
            String email,

            String billId,

            String date,
            String time,

            String grandTotal,
            String amountPaid,
            String returnAmount,

            String paymentMethod,

            String pdfPath

    ) {

        try {

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(pdfPath));

            document.open();

            // Fonts

            Font titleFont = new Font(
                    Font.FontFamily.HELVETICA,
                    18,
                    Font.BOLD,
                    BaseColor.RED);

            Font headingFont = new Font(
                    Font.FontFamily.HELVETICA,
                    12,
                    Font.BOLD);

            Font normalFont = new Font(
                    Font.FontFamily.HELVETICA,
                    11);
            // =====================================================
// LOGO + STORE HEADER
// =====================================================

PdfPTable header = new PdfPTable(2);
header.setWidthPercentage(100);
header.setWidths(new float[]{1f, 4f});

// Change the path if your logo is elsewhere
Image logo = Image.getInstance("logo.png");
logo.scaleToFit(70, 70);

PdfPCell logoCell = new PdfPCell(logo);
logoCell.setBorder(Rectangle.NO_BORDER);
logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

Paragraph shopDetails = new Paragraph();
shopDetails.add(new Chunk("BILLING MANAGEMENT SYSTEM\n", titleFont));
shopDetails.add(new Chunk("Fresh Mart Grocery Store\n", headingFont));
shopDetails.add(new Chunk("Chennai, Tamil Nadu\n", normalFont));
shopDetails.add(new Chunk("Phone : +91 9876543210\n", normalFont));
shopDetails.add(new Chunk("Email : freshmart@gmail.com", normalFont));

PdfPCell detailCell = new PdfPCell(shopDetails);
detailCell.setBorder(Rectangle.NO_BORDER);
detailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

header.addCell(logoCell);
header.addCell(detailCell);

document.add(header);

document.add(new Paragraph(" "));
LineSeparator line = new LineSeparator();
line.setLineWidth(1f);
line.setPercentage(100);

document.add(new Chunk(line));
document.add(new Paragraph(" "));
Paragraph invoiceTitle = new Paragraph(
        "TAX INVOICE",
        new Font(
                Font.FontFamily.HELVETICA,
                16,
                Font.BOLD,
                BaseColor.BLUE));

invoiceTitle.setAlignment(Element.ALIGN_CENTER);

document.add(invoiceTitle);

document.add(new Paragraph(" "));

// =====================================================
// BILL INFORMATION
// =====================================================

PdfPTable infoTable = new PdfPTable(2);
infoTable.setWidthPercentage(100);
infoTable.setSpacingBefore(10f);
infoTable.setSpacingAfter(15f);

infoTable.setWidths(new float[]{1.5f, 3f});

// ---------- Left Side ----------

PdfPCell leftCell = new PdfPCell();
leftCell.setPadding(10);
leftCell.setBorder(Rectangle.BOX);

leftCell.addElement(new Paragraph("Bill ID : " + billId, headingFont));
leftCell.addElement(new Paragraph("Date : " + date, normalFont));
leftCell.addElement(new Paragraph("Time : " + time, normalFont));

// ---------- Right Side ----------

PdfPCell rightCell = new PdfPCell();
rightCell.setPadding(10);
rightCell.setBorder(Rectangle.BOX);

rightCell.addElement(new Paragraph("Customer Name : " + customerName, headingFont));
rightCell.addElement(new Paragraph("Contact No : " + contact, normalFont));
rightCell.addElement(new Paragraph("Email : " + email, normalFont));
rightCell.addElement(new Paragraph("Payment Method : " + paymentMethod, normalFont));

infoTable.addCell(leftCell);
infoTable.addCell(rightCell);

document.add(infoTable);

LineSeparator separator = new LineSeparator();
separator.setLineWidth(0.7f);
separator.setPercentage(100);

document.add(new Chunk(separator));
document.add(new Paragraph(" "));

// =====================================================
// PRODUCT TABLE
// =====================================================

PdfPTable productTable = new PdfPTable(5);
productTable.setWidthPercentage(100);
productTable.setSpacingBefore(10f);

// Column Widths
productTable.setWidths(new float[]{1.3f, 3.5f, 1.5f, 1.3f, 1.8f});

// Table Header
productTable.addCell(getHeaderCell("Product ID"));
productTable.addCell(getHeaderCell("Product Name"));
productTable.addCell(getHeaderCell("Rate"));
productTable.addCell(getHeaderCell("Qty"));
productTable.addCell(getHeaderCell("Total"));

// Add Data from JTable
for (int i = 0; i < table.getRowCount(); i++) {

    String productId = table.getValueAt(i, 0).toString();
    String productName = table.getValueAt(i, 1).toString();
    String rate = table.getValueAt(i, 2).toString();
    String quantity = table.getValueAt(i, 3).toString();
    String total = table.getValueAt(i, 4).toString();

    productTable.addCell(getDataCell(productId));
    productTable.addCell(getDataCell(productName));
    productTable.addCell(getDataCell("₹ " + rate));
    productTable.addCell(getDataCell(quantity));
    productTable.addCell(getDataCell("₹ " + total));
}

document.add(productTable);

document.add(new Paragraph(" "));

int totalItems = table.getRowCount();

Paragraph itemCount = new Paragraph(
        "Total Items : " + totalItems,
        headingFont);

itemCount.setAlignment(Element.ALIGN_LEFT);

document.add(itemCount);

document.add(new Paragraph(" "));

// =====================================================
// TOTAL SECTION
// =====================================================

PdfPTable totalTable = new PdfPTable(2);
totalTable.setWidthPercentage(40);
totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
totalTable.setSpacingBefore(15f);

totalTable.setWidths(new float[]{2f, 1.5f});

totalTable.addCell(getCell("Grand Total", headingFont));
totalTable.addCell(getCell("₹ " + grandTotal, normalFont));

totalTable.addCell(getCell("Amount Paid", headingFont));
totalTable.addCell(getCell("₹ " + amountPaid, normalFont));

totalTable.addCell(getCell("Return Amount", headingFont));
totalTable.addCell(getCell("₹ " + returnAmount, normalFont));

document.add(totalTable);

document.add(new Paragraph(" "));

// =====================================================
// THANK YOU MESSAGE
// =====================================================

Paragraph thanks = new Paragraph(
        "Thank You For Shopping With Us!",
        new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));

thanks.setAlignment(Element.ALIGN_CENTER);

document.add(thanks);

Paragraph visitAgain = new Paragraph(
        "Visit Again",
        normalFont);

visitAgain.setAlignment(Element.ALIGN_CENTER);

document.add(visitAgain);

document.add(new Paragraph(" "));

// =====================================================
// FOOTER
// =====================================================

LineSeparator footerLine = new LineSeparator();
footerLine.setPercentage(100);
footerLine.setLineWidth(1f);

document.add(new Chunk(footerLine));

Paragraph footer = new Paragraph(
        "Generated by Billing Management System",
        new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY));

footer.setAlignment(Element.ALIGN_CENTER);

document.add(footer);

// =====================================================
// CLOSE DOCUMENT
// =====================================================

document.close();

JOptionPane.showMessageDialog(null,"Invoice Generated Successfully.");

} catch (DocumentException | IOException e) {
    JOptionPane.showMessageDialog(null, e.getMessage());
    e.printStackTrace();
}

} // End of generateInvoice()

} // End of InvoiceGenerator class
