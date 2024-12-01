package com.pharmassist.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(String templateName, Map<String, Object> data) throws Exception {
        // Render HTML with Thymeleaf
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);

        // Generate PDF with Flying Saucer
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();
        }
    }
}