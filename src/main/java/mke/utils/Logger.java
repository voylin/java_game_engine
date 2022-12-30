package mke.utils;


import static javax.script.ScriptEngine.ENGINE_VERSION;
import static mke.Core.*;

public class Logger {
  public static void printGame(String log) { System.out.println("\033[1;36m[Game]: \033[22m" + log + "\033[0m"); }
  public static void printEngine(String log) { System.out.println("\033[1;35m[Engine]: \033[22m" + log + "\033[0m"); }
  public static String printError(String log) {
    System.out.println("\033[1;33m[Error]: \033[22m" + log + "\033[0m");
    return log;
  }


  public static void printDetails() {
    System.out.println("\033[1;36m>--== Starting engine ==--<\033[0m");
    System.out.println("\033[1;36m Engine details:" + "\033[0m");
    System.out.println("\033[1;36m  - Name: " + ENGINE_NAME + "\033[0m");
    System.out.println("\033[1;36m  - Version: " + ENGINE_VERSION + "\033[0m");
    System.out.println("\033[1;36m Library details:" + "\033[0m");
    System.out.println("\033[1;36m  - LWJGL version: " + org.lwjgl.Version.getVersion() + "\033[0m");
    System.out.println("\033[1;36m  - OpenGL version: " + OPENGL_VERSION.toStringSmall() + "\033[0m");
    System.out.println("\033[1;36m Game details:" + "\033[0m");
    System.out.println("\033[1;36m  - Name: " + GAME_NAME + "\033[0m");
    System.out.println("\033[1;36m  - Version: " + GAME_VERSION + "\033[0m");
    System.out.println("\033[1;36m>--==--==--==--==--==--==--<\033[0m");
  }
}
