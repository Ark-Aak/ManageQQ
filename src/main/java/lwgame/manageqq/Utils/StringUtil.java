package lwgame.manageqq.Utils;

import org.bukkit.ChatColor;

import java.util.Random;

public class StringUtil {

    public static String getRandomString(long length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private static int compare(String str, String target) {
        int[][] d;
        int n = str.length(),m = target.length(),i,j;
        char ch1,ch2;
        int temp;
        if (n == 0) {return m;}
        if (m == 0) { return n; }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {d[i][0] = i;}
        for (j = 0; j <= m; j++) {d[0][j] = j;}
        for (i = 1; i <= n; i++) {
            ch1 = str.charAt(i - 1);
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {temp = 0;} else {temp = 1;}
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }
    private static int min(int one, int two, int three) {
        return (one = Math.min(one, two)) < three ? one : three;
    }
    /**
     * 获取两字符串的相似度
     *
     * @param str 字符串1
     * @param target 字符串2
     *
     * @return 相似度
     */
    public static double getSimilarityRatio(String str, String target) {
        return 1 - (double) compare(str, target) / Math.max(str.length(), target.length());
    }

    public static String coloredString(String res){
        return ChatColor.translateAlternateColorCodes('&',res);
    }

    public static String replacePlaceholders(String str,String placeholder,String replacement){
        while(str.contains(placeholder)){
            str = str.replace(placeholder,replacement);
        }
        return str;
    }
}
