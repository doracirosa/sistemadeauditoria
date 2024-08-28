package auditorias;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import auditoria.Audit;

public class AuditManager {
    private List<Audit> audits;

    // Construtor que inicializa a lista de auditorias
    public AuditManager() {
        this.audits = new ArrayList<>();
    }

    // Método para adicionar uma auditoria à lista
    public void addAudit(Audit audit) {
        audits.add(audit);
    }

    // Método para exportar os dados para um arquivo CSV
    public void exportToCSV(String fileName) throws IOException {
        // Formato de data para o campo de data
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Usa FileWriter para escrever no arquivo CSV
        try (FileWriter writer = new FileWriter(fileName)) {
            // Escreve a linha de cabeçalho
            writer.append("Date,PO Number,Article Number,Order Quantity,Sample Quantity,Status,Defects\n");

            // Escreve os dados de cada auditoria
            for (Audit audit : audits) {
                writer.append(dateFormat.format(audit.getDate())).append(",");
                writer.append(audit.getPoNumber()).append(",");
                writer.append(audit.getArticleNumber()).append(",");
                writer.append(String.valueOf(audit.getOrderQuantity())).append(",");
                writer.append(String.valueOf(audit.getSampleQuantity())).append(",");
                writer.append(audit.getStatus()).append(","); // Assumindo que getStatus() retorna "Aprovado" ou "Reprovado"
                
                // Converte a lista de defeitos para uma string separada por vírgulas
                List<String> defects = audit.getDefects();
                String defectsString = String.join(";", defects); // Usando ponto e vírgula para evitar conflito com vírgulas no CSV
                writer.append(defectsString).append("\n");
            }
        }
    }
}
