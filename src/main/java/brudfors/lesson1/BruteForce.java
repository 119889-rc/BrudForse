package brudfors.lesson1;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {

    public static final int MAX_KEY_SIZE = 5;

    public static void main(String...args) throws InterruptedException {
        //инициализируем словарь символов
        List symbols = generateSymbols();

        //инициализируем цепочку символов
        Link link = initLinks(symbols, MAX_KEY_SIZE);

        //начинаем перебор ключей
        StringBuilder key = new StringBuilder();
        while(!ProtectedBox.checkKey(key.toString())) {
            System.out.println("before:" + key);
            Thread.sleep(200);
            link.next();
            key.setLength(0);
            System.out.println("after:" + key);
            link.getKey(key);
        }

        //цель достигнута
        System.out.println("Valid key="+key);
        System.out.println("Secret phrase="+
                ProtectedBox.getSecretPhrase(key.toString()));
    }

    //инициализация цепочки старой доброй рекурсией
    private static Link initLinks(List symbols, int level) {
        if (level>0) {
            return new Link(symbols, initLinks(symbols, level-1));
        }
        return null;
    }

    private static List generateSymbols() {
        List symbols = new ArrayList();
        for (char c='0'; c<='9'; c++) symbols.add(c);
        for (char c='A'; c<='Z'; c++) symbols.add(c);
        for (char c='a'; c<='z'; c++) symbols.add(c);
        return symbols;
    }
}
class Link {

    //'-1'- пустое значение
    private int currentIndex = -1;
    private List<Character> symbols;
    private Link next;

    public Link(List<Character> symbols, Link link) {
        this.symbols = symbols;
        this.next = link;
    }
    //Переключатель
    public void next() {
        currentIndex++;
        if (currentIndex==symbols.size()) {
            currentIndex=0;
            if (next!=null) {
                next.next();
            }
        }
    }

    //Записываем сгенерированный пароль в generateKey
    public void getKey(StringBuilder generateKey) {
        if (next!=null) {
            next.getKey(generateKey);
        }
        if (currentIndex>-1) {
            generateKey.append(symbols.get(currentIndex));
        }
    }
}
class ProtectedBox {
    public static boolean checkKey(String key) {return false;}

    public static String getSecretPhrase(String key) {return null;}
}