package com.fiap.careermap.service;

import com.fiap.careermap.model.Trilha;
import com.fiap.careermap.repository.TrilhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {

    @Autowired
    private TrilhaRepository trilhaRepository;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Async
    public void gerarExplicacaoTrilhaAsync(Trilha trilha) {
        String prompt = criarPrompt(trilha);
        String explicacao = simularChamadaOpenAI(prompt);

        trilha.setExplicacaoIA(explicacao);
        trilhaRepository.save(trilha);
    }

    private String criarPrompt(Trilha trilha) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você é um assistente de planejamento de carreira. Sua tarefa é gerar uma explicação detalhada e motivacional para a seguinte trilha de carreira:\n\n");
        sb.append("Usuário: ").append(trilha.getUsuario().getNome()).append("\n");
        sb.append("Carreira Alvo: ").append(trilha.getCarreiraAlvo().getNome()).append(" - ").append(trilha.getCarreiraAlvo().getDescricao()).append("\n");
        sb.append("Cursos Recomendados:\n");
        trilha.getCursosRecomendados().forEach(curso -> {
            sb.append("- ").append(curso.getNome()).append(" (Duração: ").append(curso.getDuracaoHoras()).append("h). Competências: ");
            curso.getCompetenciasAdquiridas().forEach(comp -> sb.append(comp.getNome()).append(", "));
            sb.delete(sb.length() - 2, sb.length()); // Remove a última vírgula e espaço
            sb.append("\n");
        });
        sb.append("\nCom base nessas informações, escreva uma explicação coesa e inspiradora sobre como essa trilha o ajudará a alcançar a carreira alvo, destacando a importância de cada curso e as competências que serão desenvolvidas. Use um tom profissional e encorajador.");
        return sb.toString();
    }

    private String simularChamadaOpenAI(String prompt) {
        // Simulação de chamada à API do OpenAI usando WebClient
        // Em um ambiente real, você faria uma chamada HTTP POST para a API do OpenAI
        // com o prompt no corpo da requisição.

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "Você é um assistente de planejamento de carreira.");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", prompt);
                }}
        });
        requestBody.put("max_tokens", 500);

        // Esta é uma simulação. A chamada real seria assíncrona e mais complexa.
        // Como não podemos fazer chamadas externas reais, retornaremos um texto fixo.
        return "Esta é uma explicação gerada por IA (simulada) para a sua trilha de carreira. Ela detalha os passos e os benefícios de cada curso para o seu desenvolvimento profissional na carreira alvo.";
    }
}
