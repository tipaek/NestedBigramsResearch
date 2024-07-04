import java.io.*;
import java.util.*;

class Solution
{
    static Reader in = new Reader();
    static StringBuilder stringBuilder = new StringBuilder();
    public static void main(String[] args)throws IOException
    {
        int t = in.nextInt();
        for (int i=1;i<=t;i++)
        {
            stringBuilder.append("Case #").append(i).append(": ");
            solve();
            if (i < t)
                stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }
    public static void solve()throws IOException
    {
        int n = in.nextInt();
        int d = in.nextInt();
        long[] A = new long[n];
        for (int i=0;i<n;i++)
            A[i] = in.nextLong();
        Map<Long, Integer> map = new HashMap<>();
        for (int i=0;i<n;i++)
        {
            if (!map.containsKey(A[i]))
                map.put(A[i], 1);
            else
                map.put(A[i], map.get(A[i]) + 1);
        }
        if (d == 1)
        {
            stringBuilder.append("0");
            return;
        }
        else if (d == 2)
        {
            Set<Long> keySet = map.keySet();
            for (long key: keySet)
            {
                if (map.get(key) >= 2)
                {
                    stringBuilder.append("0");
                    return;
                }
            }
            stringBuilder.append("1");
            return;
        }
        Set<Long> keySet = map.keySet();
        for (long key: keySet)
        {
            if (map.get(key) >= 3)
            {
                stringBuilder.append("0");
                return;
            }
        }
        for (long key: keySet)
        {
            if (map.containsKey(2 * key))
            {
                stringBuilder.append("1");
                return;
            }
        }
        stringBuilder.append("2");
        return;
    }
}
class Reader
{
    final private int BUFFER_SIZE = 1 << 16;
    private final DataInputStream din;
    private final byte[] buffer;
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