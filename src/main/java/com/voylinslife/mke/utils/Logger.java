package com.voylinslife.mke.utils;


public class Logger {
  public static void printGame(String log) { System.out.println("\033[1;36m[Game]: \033[22m" + log + "\033[0m"); }
  public static void printEngine(String log) { System.out.println("\033[1;35m[Engine]: \033[22m" + log + "\033[0m"); }
  public static String printError(String log) {
    System.out.println("\033[1;33m[Error]: \033[22m" + log + "\033[0m");
    return log;
  }
}
