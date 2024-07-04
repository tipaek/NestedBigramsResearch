import java.io.*;
import java.util.*;

class Solution
{
    public static void main(String[] args)throws IOException
    {
        Reader in = new Reader();
        int t = in.nextInt();
        StringBuilder stringBuilder = new StringBuilder();
        for (int testCase = 1;testCase <= t;testCase++)
        {
            long n = in.nextLong();
            long last = 0;
            long xx = 0;
            for (int i=0;i<500;i++)
            {
                long x = i * (i + 1) / 2 + 1;
                if (x <= n)
                {
                    last = i;
                    xx = x;
                }
                else
                    break;
            }
            n -= xx;
            last ++;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("1 1");
            if (last >= 2)
                stringBuilder1.append("\n");
            for (int i=2;i<=last;i++)
            {
                stringBuilder1.append(i).append(" 2");
                if (i < last)
                    stringBuilder1.append("\n");
            }
            if (n > 0)
                stringBuilder1.append("\n");
            for (int i=0;i<n;i++)
            {
                stringBuilder1.append(last + 1 + i).append(" 1");
                if (i < n - 1)
                    stringBuilder1.append("\n");
            }
            stringBuilder.append("Case #").append(testCase).append(":\n").append(stringBuilder1);
            if (testCase < t)
                stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }
}
class Reader
{
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public Reader()
    {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public Reader(String file_name) throws IOException
    {
        din = new DataInputStream(new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public String readLine() throws IOException
    {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1)
        {
            if (c == '\n')
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    public int nextInt() throws IOException
    {
        int ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do
        {
            ret = ret * 10 + c - '0';
        }  while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    public long nextLong() throws IOException
    {
        long ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        }
        while ((c = read()) >= '0' && c <= '9');
        if (neg)
            return -ret;
        return ret;
    }

    public double nextDouble() throws IOException
    {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();

        do {
            ret = ret * 10 + c - '0';
        }
        while ((c = read()) >= '0' && c <= '9');

        if (c == '.')
        {
            while ((c = read()) >= '0' && c <= '9')
            {
                ret += (c - '0') / (div *= 10);
            }
        }

        if (neg)
            return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException
    {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException
    {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException
    {
        if (din == null)
            return;
        din.close();
    }
}