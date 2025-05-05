package ru.java.maryan.api.transactionnotificationservice.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionService;

import java.io.ByteArrayOutputStream;

@UtilityClass
public class PdfGenerator {
    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 16, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.HELVETICA, 10);
    private static final Font BOLD_FONT = new Font(Font.HELVETICA, 10, Font.BOLD);

    public byte[] generateReceipt(Transaction transaction, User fromUser, User toUser) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph title = new Paragraph("Payment Receipt", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            document.add(new Paragraph("Transaction Details:", HEADER_FONT));
            addLine(document, "ID:", transaction.getId().toString());
            addLine(document, "Date:", transaction.getCreatedAt().toString());
            addLine(document, "Amount:", transaction.getAmount().toString());
            addLine(document, "Status:", transaction.getStatus().toString());

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Sender:", HEADER_FONT));
            addLine(document, "Name:", fromUser.getNameUser());
            addLine(document, "Surname:", fromUser.getSurnameUser());
            addLine(document, "Account:", transaction.getFromAccountId().toString());

            document.add(new Paragraph("Recipient:", HEADER_FONT));
            addLine(document, "Name:", toUser.getNameUser());
            addLine(document, "Surname:", toUser.getSurnameUser());
            addLine(document, "Account:", transaction.getToAccountId().toString());

            if (transaction.getComment() != null && !transaction.getComment().isEmpty()) {
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Comment:", HEADER_FONT));
                document.add(new Paragraph(transaction.getComment(), NORMAL_FONT));
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addLine(Document doc, String label, String value) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(label + " ", BOLD_FONT));
        p.add(new Chunk(value, NORMAL_FONT));
        doc.add(p);
    }

    private String formatAmount(Long amount) {
        return String.format("%.2f", amount / 100.0);
    }
}
