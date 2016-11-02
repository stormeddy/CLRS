package stringmatching;

public class StringMatch2D {
     
        public static void main(String[] args) {
            char[][] text = {
                               { 'a', 'b', 'a', 'b', 'a' },
                               { 'a', 'b', 'a', 'b', 'a' },
                               { 'a', 'b', 'b', 'a', 'a' },
                               { 'a', 'b', 'a', 'a', 'b' },
                               { 'b', 'b', 'a', 'b', 'a' }
                             };
            char[][] pattern = {
                           { 'a', 'b' },
                           { 'b', 'a' }
                             };
     
            matrixPatternMatch(text, pattern);
        }
     
        private static void matrixPatternMatch(char[][] text, char[][] pattern) {
            // pre-process
            int[] patternStamp = new int[pattern[0].length];
            int[] textStamp = new int[text[0].length];
     
            caculateStamp(pattern, pattern.length, patternStamp);
            caculateStamp(text, pattern.length, textStamp);
     
            int[] next = new int[patternStamp.length];
            caculateNext(patternStamp, next);
     
            for (int i = 0; i < (text.length - pattern.length + 1); i++) {
            	//具体匹配用KMP算法，递推时候类似RK算法
                int col = isMatch(patternStamp, textStamp, next);
                if (col != -1) {
                    System.out.println("found");
                    System.out.println(i+", "+col);
                }
     
               // move down
                if(i < text.length - pattern.length)
                    caculateNextStamp(text, pattern.length, textStamp, i);
            }
     
        }
     
        private static int isMatch(int[] patternStamp, int[] textStamp, int[] next) {
            int i = 0, j = 0;
            while (j < patternStamp.length && i < textStamp.length) {
                if (j == -1 || patternStamp[j] == textStamp[i]) {
                    i++;
                    j++;
                } else {
                    j = next[j];
                }
            }
     
            if (j == patternStamp.length) {
                return i-j;
            } else {
                return -1;
            }
        }
     
        private static void caculateNext(int[] pattern, int[] next) {
            next[0] = -1;
     
            int i = 0, j = -1;
            while(i<pattern.length-1) {
                if(j==-1 || pattern[i] == pattern[j]) {
                    i++;
                    j++;
                    next[i] = j;
                } else {
                    j = next[j];
                }
            }
     
        }
     
        private static void caculateNextStamp(char[][] text, int height,
                int[] textStamp, int row) {
            int d = (int) Math.pow(26, height-1);//最好用模取幂
            for (int i = 0; i < textStamp.length; i++) {
                textStamp[i] = 26 * (textStamp[i] - d * text[row][i]) + text[row + height][i];
            }
        }
     
        private static void caculateStamp(char[][] input, int height, int[] result) {
            for (int i = 0; i < result.length; i++) {
                result[i] = 0;
                for (int j = 0; j < height; j++) {
                    result[i] = 26 * result[i] + input[j][i];
                }
            }
        }
     
    }
//第21-28行，进行匹配前的预处理。
//第21-22行，patternStamp和textStamp分别用来存储模式矩阵以及文本矩阵的前m1行转换成一维数字串。
//第86-93行，定义函数caculateStamp，输入是一个二维字符矩阵，输出是转换成的一维数字串。对输入矩阵的每一列用霍纳法则计算出一个值，作为转换后的一维数字串的一位。
//第24-25行，用函数caculateStamp计算得到转换后的一维数字串。
//第27-28行，计算转换后的一维模式串的next数组，用于KMP算法进行一维串匹配。
//
//第30-40行，进行实际的匹配。
//匹配时，对文本矩阵每m1行进行一次匹配，匹配前都转换成一维的数字串，用KMP算法（第31行）进行一维串匹配，总共要匹配n1-m1+1次。
//第38-39行，待匹配的文本矩阵下移一行，计算转换成的新一维数字串。
//第78-84行，函数caculateNextStamp，用来根据上一次的一维转换数字串计算出新的，类似于RK算法中的公式二。
//
//时间复杂度：
//预处理 – O(n2*m1)
//计算模式矩阵的stamp O(m1*m2) + 计算文本矩阵前m1行的stamp O(n2*m1) + 计算模式矩阵stamp的next数组 O(m2)
//
//匹配 – O((n1-m1+1)*n2)
//总共 n1-m1+1次
//每次用KMP算法匹配 O(n2) + 计算文本矩阵下m行的stamp O(n2)
//
//总的时间复杂度 – O(n1*n2)
