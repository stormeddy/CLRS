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
            	//����ƥ����KMP�㷨������ʱ������RK�㷨
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
            int d = (int) Math.pow(26, height-1);//�����ģȡ��
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
//��21-28�У�����ƥ��ǰ��Ԥ����
//��21-22�У�patternStamp��textStamp�ֱ������洢ģʽ�����Լ��ı������ǰm1��ת����һά���ִ���
//��86-93�У����庯��caculateStamp��������һ����ά�ַ����������ת���ɵ�һά���ִ�������������ÿһ���û��ɷ�������һ��ֵ����Ϊת�����һά���ִ���һλ��
//��24-25�У��ú���caculateStamp����õ�ת�����һά���ִ���
//��27-28�У�����ת�����һάģʽ����next���飬����KMP�㷨����һά��ƥ�䡣
//
//��30-40�У�����ʵ�ʵ�ƥ�䡣
//ƥ��ʱ�����ı�����ÿm1�н���һ��ƥ�䣬ƥ��ǰ��ת����һά�����ִ�����KMP�㷨����31�У�����һά��ƥ�䣬�ܹ�Ҫƥ��n1-m1+1�Ρ�
//��38-39�У���ƥ����ı���������һ�У�����ת���ɵ���һά���ִ���
//��78-84�У�����caculateNextStamp������������һ�ε�һάת�����ִ�������µģ�������RK�㷨�еĹ�ʽ����
//
//ʱ�临�Ӷȣ�
//Ԥ���� �C O(n2*m1)
//����ģʽ�����stamp O(m1*m2) + �����ı�����ǰm1�е�stamp O(n2*m1) + ����ģʽ����stamp��next���� O(m2)
//
//ƥ�� �C O((n1-m1+1)*n2)
//�ܹ� n1-m1+1��
//ÿ����KMP�㷨ƥ�� O(n2) + �����ı�������m�е�stamp O(n2)
//
//�ܵ�ʱ�临�Ӷ� �C O(n1*n2)
