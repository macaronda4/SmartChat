package mdf.works.smartchat.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    // GSONの準備（JSONを綺麗に改行して出力する設定）
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    // 実際のConfigデータが入る変数
    public static SmartchatConfig INSTANCE = new SmartchatConfig();

    // 保存先のパスを取得（.minecraft/config/smartchat.json になります）
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("smartchat.json");

    // Configを読み込むメソッド
    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                // ファイルがあれば、中身を読み取ってINSTANCEに上書きする
                INSTANCE = GSON.fromJson(reader, SmartchatConfig.class);
            } catch (Exception e) {
                System.err.println("[SmartChat] Configの読み込みに失敗しました！");
                e.printStackTrace();
            }
        } else {
            // ファイルがない場合（初回起動時）は、初期値のままファイルを作成する
            save();
        }
    }

    // Configを保存するメソッド
    public static void save() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            System.err.println("[SmartChat] Configの保存に失敗しました！");
            e.printStackTrace();
        }
    }
}