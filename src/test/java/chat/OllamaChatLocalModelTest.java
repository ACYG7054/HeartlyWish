package chat;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import static dev.langchain4j.model.chat.request.ResponseFormat.JSON;

class OllamaChatLocalModelTest {
    static String MODEL_NAME = "deepseek-r1:1.5b"; // 尝试其他本地 ollama 模型名称
    static String BASE_URL = "http://localhost:11434"; // 本地 ollama 基础 URL

    public static void main(String[] args) {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();
        String answer = model.chat("列出中国前10大城市");
        System.out.println(answer);

        model = OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .responseFormat(JSON)
                .build();

        String json = model.chat("列出美国前10大城市");
        System.out.println(json);
    }
}