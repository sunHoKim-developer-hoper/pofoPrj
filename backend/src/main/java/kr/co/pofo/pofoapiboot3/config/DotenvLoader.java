package kr.co.pofo.pofoapiboot3.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {
    
    public static void loadEnv(){

        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_PASS", dotenv.get("DB_PASS"));
        System.setProperty("DB_USER", dotenv.get("DB_URL"));

        /*Dotenv dotenv = Dotenv.configure()
           .directory("/path/to/directory")  // 디렉토리만 지정
           .filename("custom.env")           // 파일명도 변경 가능 (선택)
           .load(); 
        */
    }
}
